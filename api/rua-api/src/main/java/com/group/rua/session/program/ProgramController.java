package com.group.rua.session.program;

import com.group.rua.RuaConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/program")
@CrossOrigin(origins = RuaConfig.FRONTEND_URL)
public class ProgramController {

    private final ProgramService programService;

    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }

    @GetMapping("/{programId}/subjectsInfo")
    public ResponseEntity<List<ProgramInfoDTO>> getProgramInfo(@PathVariable Integer programId) {
        try {
            List<ProgramInfoDTO> infoList = programService.getProgramInfo(programId);
            return ResponseEntity.ok(infoList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{programId}/changes")
    public ResponseEntity<Void> applyProgramChanges(
            @PathVariable Integer programId,
            @RequestBody List<ProgramChangeDTO> changes) {
        try {
            programService.processProgramChanges(programId, changes);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}