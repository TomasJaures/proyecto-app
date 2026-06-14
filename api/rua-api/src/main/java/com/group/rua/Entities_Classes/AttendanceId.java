package com.group.rua.Entities_Classes;

import java.io.Serializable;
import java.util.Objects;

/**
 * Clase utilizada por JPA para definir la clave compuesta (userId, classId) de Attendance.
 */

public class AttendanceId implements Serializable {
    public Integer userId;
    public Integer classId;

    public AttendanceId() {}

    public AttendanceId(Integer userId, Integer classId) {
        this.userId = userId;
        this.classId = classId;
    }

    // JPA requiere equals y hashCode para llaves compuestas
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttendanceId that = (AttendanceId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(classId, that.classId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, classId);
    }
}
