package com.group.rua.session.calendar;

import com.group.rua.RuaConfig;
import com.group.rua.session.attendance.CurrentCalendarClassesDTO;
import com.group.rua.session.attendance.WeekClassAttendanceInfoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/calendar")
@CrossOrigin(origins = RuaConfig.FRONTEND_URL)
public class CalendarController {

    private final CalendarService calendarService;

    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @GetMapping("/{calendarId}/classes")
    public ResponseEntity<CurrentCalendarClassesDTO> getStudentCalendar(@PathVariable Integer calendarId) {
        try {
            CurrentCalendarClassesDTO calendarData = calendarService.getStudentCalendar(calendarId);
            return ResponseEntity.ok(calendarData);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{calendarId}/classes/week/{weekId}")
    public ResponseEntity<CurrentCalendarClassesDTO> getStudentCalendarByWeek(
            @PathVariable Integer calendarId,
            @PathVariable Integer weekId) {
        try {
            CurrentCalendarClassesDTO calendarData = calendarService.getStudentCalendarByWeek(calendarId, weekId);
            return ResponseEntity.ok(calendarData);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{calendarId}/classes/generate")
    public ResponseEntity<CurrentCalendarClassesDTO> generateCurrentWeekClasses(@PathVariable Integer calendarId) {
        try {
            CurrentCalendarClassesDTO calendarData = calendarService.generateClassesForCurrentWeek(calendarId);
            return ResponseEntity.ok(calendarData);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{calendarId}/changes")
    public ResponseEntity<Void> applyScheduleChanges(
            @PathVariable Integer calendarId,
            @RequestBody List<ScheduleChangeDTO> changes) {
        try {
            calendarService.processScheduleChanges(calendarId, changes);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // por si el front envía un día mal escrito, una hora inválida o falla la base de datos
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{calendarId}/classesAttendance")
    public ResponseEntity<List<WeekClassAttendanceInfoDTO>> getAttendanceByWeek(
            @PathVariable Integer calendarId,
            @RequestParam Integer weekId) {

        try {
            List<WeekClassAttendanceInfoDTO> attendanceInfo = calendarService.getWeeklyAttendanceStatus(calendarId, weekId);
            return ResponseEntity.ok(attendanceInfo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}