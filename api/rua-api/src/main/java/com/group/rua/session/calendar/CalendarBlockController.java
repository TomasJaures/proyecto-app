package com.group.rua.session.calendar;


import com.group.rua.RuaConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/{calendarId}/blocks")
    public ResponseEntity<List<CalendarBlockDTO>> getBlocks(@PathVariable Integer calendarId) {
        List<CalendarBlockDTO> blocks = calendarBlockService.getBlocksByCalendar(calendarId);
        return ResponseEntity.ok(blocks);
    }

    // Endpoint para Remover
    @PatchMapping("/blocks/{blockId}/remove")
    public ResponseEntity<String> removeBlock(@PathVariable Integer blockId) {
        calendarBlockService.removeBlock(blockId);
        return ResponseEntity.ok("Clase removida exitosamente");
    }

    // Endpoint para Mover
    @PatchMapping("/blocks/{blockId}/move")
    public ResponseEntity<String> moveBlock(@PathVariable Integer blockId, @RequestBody MoveBlockDTO dto) {
        calendarBlockService.moveBlock(blockId, dto.weekDay(), dto.startHour(), dto.endHour());
        return ResponseEntity.ok("Clase movida exitosamente");
    }

    // Endpoint para Añadir
    @PostMapping("/{calendarId}/blocks")
    public ResponseEntity<String> addBlock(@PathVariable Integer calendarId, @RequestBody AddBlockDTO dto) {
        calendarBlockService.addBlockToCalendar(calendarId, dto);
        return ResponseEntity.ok("Clase añadida exitosamente al calendario");
    }

    // Endpoint para Clonar
    @PostMapping("/{calendarId}/blocks/{blockId}/clone")
    public ResponseEntity<String> cloneBlock(
            @PathVariable Integer calendarId,
            @PathVariable Integer blockId,
            @RequestBody CloneBlockDTO dto) {

        calendarBlockService.cloneBlock(calendarId, blockId, dto);
        return ResponseEntity.ok("Clase clonada exitosamente");
    }

    // Endpoint para Editar
    @PutMapping("/blocks/{blockId}")
    public ResponseEntity<String> editBlock(@PathVariable Integer blockId, @RequestBody EditBlockDTO dto) {
        calendarBlockService.editBlock(blockId, dto);
        return ResponseEntity.ok("Clase editada exitosamente");
    }
}