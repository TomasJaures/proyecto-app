package com.group.rua.session.attendance;

import com.group.rua.RuaConfig;
import com.group.rua.entities.QrToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/qr")
@CrossOrigin(origins = RuaConfig.FRONTEND_URL)
public class QrTokenController {

    private final QrTokenService qrTokenService;

    public QrTokenController(QrTokenService qrTokenService) {
        this.qrTokenService = qrTokenService;
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generateQr(@RequestParam Integer classId) {
        try {
            QrToken generatedToken = qrTokenService.generateQrForClass(classId);
            return ResponseEntity.ok(generatedToken.content);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/decode")
    public ResponseEntity<Object> decodeQr(@RequestParam String content, @RequestParam String email) {
        try {
            // Ahora recibimos un Mapa que contiene { operated: boolean, attendance: Attendance }
            Map<String, Object> response = qrTokenService.decodeQrAndRegisterAttendance(content, email);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}