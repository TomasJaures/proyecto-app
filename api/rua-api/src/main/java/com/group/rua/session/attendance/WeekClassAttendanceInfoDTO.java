package com.group.rua.session.attendance;

public class WeekClassAttendanceInfoDTO {
    private Integer classId;
    private String timeState; // PAST, PRESENT, FUTURE
    private Boolean hasAssisted; // para saber si el alumno asistió

    public WeekClassAttendanceInfoDTO(Integer classId, String timeState, Boolean hasAssisted) {
        this.classId = classId;
        this.timeState = timeState;
        this.hasAssisted = hasAssisted;
    }

    public Integer getClassId() {
        return classId;
    }

    public String getTimeState() {
        return timeState;
    }

    public Boolean getHasAssisted() {
        return hasAssisted;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public void setTimeState(String timeState) {
        this.timeState = timeState;
    }

    public void setHasAssisted(Boolean hasAssisted) {
        this.hasAssisted = hasAssisted;
    }
}
