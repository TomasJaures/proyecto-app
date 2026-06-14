package com.group.rua.Entities_Classes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "attendance")
@IdClass(AttendanceId.class) // aqui vinculamos la llave compuesta
public class Attendance {

    @Id
    @Column(name = "user_id")
    public Integer userId;

    @Id
    @Column(name = "class_id")
    public Integer classId;

    @Column(name = "status")
    public String status; // "PRESENT", "ABSENT", "MANUAL-ATTENDANCE"
}