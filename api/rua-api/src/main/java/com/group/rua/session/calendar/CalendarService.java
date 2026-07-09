package com.group.rua.session.calendar;

import com.group.rua.repositories.ClassesRepo;
import com.group.rua.session.attendance.ClassInfoDTO;
import com.group.rua.session.attendance.CurrentCalendarClassesDTO;
import org.springframework.stereotype.Service;

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

    public CalendarService(ClassesRepo classesRepo) {
        this.classesRepo = classesRepo;
    }

    public CurrentCalendarClassesDTO getStudentCalendar(Integer calendarId) {

        LocalDateTime now = LocalDateTime.now();
        LocalDate today = now.toLocalDate();

        // en qué semana del año estamos
        int currentWeekNumber = now.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());

        // fechas límite de esta semana (Lunes a Domingo)
        LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        // traer solo las clases de este calendario y de esta semana específica
        List<ClassInfoDTO> studentClasses = classesRepo.findAllClassesByCalendarIdAndWeek(calendarId, startOfWeek, endOfWeek);

        for (ClassInfoDTO classInfo : studentClasses) {
            String state = calculateTimeState(classInfo.getClassDate(), classInfo.getStartHour(), classInfo.getEndHour(), now);
            classInfo.setTimeState(state);
        }

        return new CurrentCalendarClassesDTO(currentWeekNumber, studentClasses);
    }

    private String calculateTimeState(LocalDate classDate, LocalTime startHour, LocalTime endHour, LocalDateTime now) {
        LocalDate currentDate = now.toLocalDate();
        LocalTime currentTime = now.toLocalTime();

        // si la fecha de la clase ya pasó (Ayer o antes)
        if (classDate.isBefore(currentDate)) {
            return "PAST";
        }

        // si la fecha de la clase es a futuro (Mañana o después)
        if (classDate.isAfter(currentDate)) {
            return "FUTURE";
        }

        // la clase es HOY. Comparamos las horas exactas.
        if (currentTime.isBefore(startHour)) {
            return "FUTURE"; // Aún no empieza
        } else if (currentTime.isAfter(endHour)) {
            return "PAST"; // Ya terminó
        } else {
            return "PRESENT"; // Está ocurriendo en este momento
        }
    }
}