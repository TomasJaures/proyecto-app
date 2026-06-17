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

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
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
}