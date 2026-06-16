package com.group.rua.Session.Attendance;

import com.group.rua.RuaConfig;
import com.group.rua.Entities_Classes.Attendance;
import com.group.rua.Entities_Classes.Classes;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = RuaConfig.FRONTEND_URL)
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @GetMapping("/class/{blockId}/getInfo")
    public ResponseEntity<ClassInfoDTO> getLastClassAsignedToBlock(@PathVariable Integer blockId) {
        ClassInfoDTO classInfo = attendanceService.getClassInfoWithDetails(blockId);
        return ResponseEntity.ok(classInfo);
    }
    

    @GetMapping("/class/{classId}/present")
    public ResponseEntity<List<Attendance>> getPresentStudents(@PathVariable Integer classId) {
        List<Attendance> presentStudents = attendanceService.getPresentStudents(classId);
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
}