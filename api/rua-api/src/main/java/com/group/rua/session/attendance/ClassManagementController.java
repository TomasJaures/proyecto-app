package com.group.rua.session.attendance;

import com.group.rua.RuaConfig;
import com.group.rua.entities.Block;
import com.group.rua.entities.Classes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/classes")
@CrossOrigin(origins = RuaConfig.FRONTEND_URL)
public class ClassManagementController {

    private final ClassManagementService classManagementService;

    public ClassManagementController(ClassManagementService classManagementService) {
        this.classManagementService = classManagementService;
    }

    @PostMapping("/{classId}/annul")
    public ResponseEntity<Classes> annulClass(@PathVariable Integer classId) {
        Classes annulledClass = classManagementService.anullClass(classId);
        return ResponseEntity.ok(annulledClass);
    }

    @PostMapping("/block/{blockId}/state")
    public ResponseEntity<Block> configureClassBlockState(
            @PathVariable Integer blockId,
            @RequestParam String state) {

        Block updatedBlock = classManagementService.configureClassBlockState(blockId, state);
        return ResponseEntity.ok(updatedBlock);
    }
}