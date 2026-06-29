package com.group.rua.entities;

import jakarta.persistence.*;

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