package com.group.rua.session.calendar;

import com.group.rua.entities.Block.WeekDay;
import com.group.rua.repositories.ClassesRepo;
import com.group.rua.session.attendance.ClassInfoDTO;
import com.group.rua.session.attendance.CurrentCalendarClassesDTO;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

        // Obtener la semana del año actual
        LocalDateTime now = LocalDateTime.now();
        int currentWeekNumber = now.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());

        List<ClassInfoDTO> studentClasses = classesRepo.findAllClassesByCalendarId(calendarId);

        // Evaluar dinámicamente el estado temporal de cada clase
        for (ClassInfoDTO classInfo : studentClasses) {
            String state = calculateTimeState(classInfo.getWeekDay(), classInfo.getStartHour(), classInfo.getEndHour(), now);
            classInfo.setTimeState(state); // Inyectamos el estado en el DTO
        }

        return new CurrentCalendarClassesDTO(currentWeekNumber, studentClasses);
    }

    private String calculateTimeState(WeekDay blockDay, LocalTime startHour, LocalTime endHour, LocalDateTime now) {
        DayOfWeek currentDay = now.getDayOfWeek();
        LocalTime currentTime = now.toLocalTime();

        // Convertir Enum al formato numérico de Java (Lunes=1, Martes=2...) --> el mapeo esta al final de esta clase
        int blockDayNumber = getDayNumber(blockDay);
        int currentDayNumber = currentDay.getValue();

        // Si el día del bloque ya pasó en la semana actual
        if (blockDayNumber < currentDayNumber) {
            return "PAST";
        }
        // Si el bloque es para un día futuro en la semana actual
        if (blockDayNumber > currentDayNumber) {
            return "FUTURE";
        }

        // Comparar las horas.
        if (currentTime.isBefore(startHour)) {
            return "FUTURE"; // Aún no empieza
        } else if (currentTime.isAfter(endHour)) {
            return "PAST"; // Ya terminó
        } else {
            return "PRESENT"; // Está ocurriendo en este momento
        }
    }

    // para mapear el Enum personalizado al estándar numérico
    private int getDayNumber(WeekDay day) {
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