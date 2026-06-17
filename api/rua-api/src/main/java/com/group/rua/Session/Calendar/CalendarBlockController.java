package com.group.rua.Session.Calendar;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.group.rua.RuaConfig;

import java.util.List;

@RestController
@RequestMapping("/api/calendars")
@CrossOrigin(origins = RuaConfig.FRONTEND_URL) // ajusta el origen según tu config de seguridad
public class CalendarBlockController {

    private final CalendarBlockService calendarBlockService;

    public CalendarBlockController(CalendarBlockService calendarBlockService) {
        this.calendarBlockService = calendarBlockService;
    }

    /**
     * GET /api/calendars/{calendarId}/blocks
     *
     * Devuelve la lista de bloques del calendario indicado,
     * con toda la información necesaria para renderizar la UI.
     *
     * Ejemplo de respuesta:
     * [
     *   {
     *     "calendarId": 1,
     *     "blockId": 1,
     *     "weekDay": "MON",
     *     "startHour": "08:00:00",
     *     "endHour": "09:30:00",
     *     "blockState": "NO_PROJECTIONS",
     *     "moduleId": 1,
     *     "moduleNum": 1,
     *     "subjectId": 1,
     *     "subjectName": "Bases de Datos",
     *     "code": "ICC705"
     *   }
     * ]
     */
    @GetMapping("/{calendarId}/blocks") //@RequestMapping("/api/calendars")
    public ResponseEntity<List<CalendarBlockDTO>> getBlocks(@PathVariable Integer calendarId) {
        System.out.println("test");
        List<CalendarBlockDTO> blocks = calendarBlockService.getBlocksByCalendar(calendarId);
        System.out.println("test");
        return ResponseEntity.ok(blocks);
    }
}
