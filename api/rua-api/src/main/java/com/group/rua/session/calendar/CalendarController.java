package com.group.rua.session.calendar;

import com.group.rua.RuaConfig;
import com.group.rua.session.attendance.CurrentCalendarClassesDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}