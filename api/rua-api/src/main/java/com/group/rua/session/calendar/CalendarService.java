package com.group.rua.session.calendar;

import com.group.rua.repositories.BlockRepo;
import com.group.rua.repositories.ClassesRepo;
import com.group.rua.session.attendance.ClassInfoDTO;
import com.group.rua.session.attendance.CurrentCalendarClassesDTO;
import org.springframework.stereotype.Service;
import com.group.rua.entities.Block;
import com.group.rua.entities.Classes;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

@Service
public class CalendarService {

    private final ClassesRepo classesRepo;
    private final BlockRepo blockRepo;

    public CalendarService(ClassesRepo classesRepo, BlockRepo blockRepo) {
        this.classesRepo = classesRepo;
        this.blockRepo = blockRepo;
    }

    public CurrentCalendarClassesDTO getStudentCalendar(Integer calendarId) {
        LocalDateTime now = LocalDateTime.now();
        int currentWeekNumber = now.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());

        return getStudentCalendarByWeek(calendarId, currentWeekNumber);
    }

    public CurrentCalendarClassesDTO getStudentCalendarByWeek(Integer calendarId, Integer weekId) {

        LocalDateTime now = LocalDateTime.now();
        int currentYear = now.getYear();

        LocalDate startOfWeek = LocalDate.now()
                .withYear(currentYear)
                .with(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear(), weekId)
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        LocalDate endOfWeek = startOfWeek.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        List<ClassInfoDTO> studentClasses = classesRepo.findAllClassesByCalendarIdAndWeek(calendarId, startOfWeek, endOfWeek);

        for (ClassInfoDTO classInfo : studentClasses) {
            String state = calculateTimeState(classInfo.getClassDate(), classInfo.getStartHour(), classInfo.getEndHour(), now);
            classInfo.setTimeState(state);
        }

        return new CurrentCalendarClassesDTO(weekId, studentClasses);
    }

    @Transactional
    public CurrentCalendarClassesDTO generateClassesForCurrentWeek(Integer calendarId) {

        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        List<Block> blocks = blockRepo.findBlocksByCalendarId(calendarId);
        List<Classes> classesToSave = new ArrayList<>();

        for (Block block : blocks) {

            int dayOffset = getDayNumber(block.weekDay) - 1;
            LocalDate targetClassDate = startOfWeek.plusDays(dayOffset);

            if (!classesRepo.existsByBlockIdAndClassDate(block.blockId, targetClassDate)) {
                Classes newClass = new Classes();
                newClass.blockId = block.blockId;
                newClass.classDate = targetClassDate;
                newClass.isAnulled = false;

                classesToSave.add(newClass);
            }
        }

        if (!classesToSave.isEmpty()) {
            classesRepo.saveAll(classesToSave);
        }

        return getStudentCalendar(calendarId);
    }

    private String calculateTimeState(LocalDate classDate, LocalTime startHour, LocalTime endHour, LocalDateTime now) {
        LocalDate currentDate = now.toLocalDate();
        LocalTime currentTime = now.toLocalTime();

        if (classDate.isBefore(currentDate)) {
            return "PAST";
        }

        if (classDate.isAfter(currentDate)) {
            return "FUTURE";
        }

        if (currentTime.isBefore(startHour)) {
            return "FUTURE";
        } else if (currentTime.isAfter(endHour)) {
            return "PAST";
        } else {
            return "PRESENT";
        }
    }

    private int getDayNumber(Block.WeekDay day) {
        return switch (day) {
            case MON -> 1;
            case TUE -> 2;
            case WED -> 3;
            case THU -> 4;
            case FRI -> 5;
            case SAT -> 6;
            case SUN -> 7;
        };
    }
}