package com.group.rua.Session.Attendance;

import com.group.rua.RuaConfig;
import com.group.rua.Entities_Classes.Attendance;
import com.group.rua.Entities_Classes.Classes;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = RuaConfig.FRONTEND_URL)
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final AttendanceAlertService attendanceAlertService;

    public AttendanceController(AttendanceService attendanceService, AttendanceAlertService attendanceAlertService) {
        this.attendanceService = attendanceService;
        this.attendanceAlertService = attendanceAlertService;
    }

    @GetMapping("/class/{blockId}/getInfo")
    public ResponseEntity<ClassInfoDTO> getLastClassAsignedToBlock(@PathVariable Integer blockId) {
        ClassInfoDTO classInfo = attendanceService.getClassInfoWithDetails(blockId);
        return ResponseEntity.ok(classInfo);
    }
    

    //FIXME: ARREGLAR NOMBRE
    @GetMapping("/class/{classId}/present")
    public ResponseEntity<List<Attendance>> getPresentStudents(@PathVariable Integer classId) {
        System.out.println("------- sin students");
        List<Attendance> presentStudents = attendanceService.getPresentStudents(classId);
        System.out.println("sin students" + presentStudents);
        return ResponseEntity.ok(presentStudents);
    }

    @GetMapping("/class/{classId}/present2")
    public ResponseEntity<List<PresentStudentDTO>> getPresentStudentsWithDetails(@PathVariable Integer classId) {
        List<PresentStudentDTO> presentStudents = attendanceService.getPresentStudentsWithDetails(classId);
        return ResponseEntity.ok(presentStudents);
    }

    @PostMapping("/manual")
    public ResponseEntity<Attendance> registerManualAttendance(
            @RequestParam Integer userId,
            @RequestParam Integer classId,
            @RequestParam String status) {

        Attendance registeredAttendance = attendanceService.registerManualAttendance(userId, classId, status);
        return ResponseEntity.ok(registeredAttendance);
    }

    @PostMapping("/class/{classId}/alert-low-attendance")
    public ResponseEntity<String> sendLowAttendanceAlert(
            @PathVariable Integer classId,
            @RequestParam Integer userId,
            @RequestParam Double percentage) {

        try {
            // Requerirá instanciar AttendanceAlertService en el constructor del controlador
            attendanceAlertService.generateAndSendAlert(userId, classId, percentage);
            return ResponseEntity.ok("Alerta generada con IA y enviada correctamente al alumno.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}