package com.group.rua.session.calendar;

public class ScheduleChangeDTO {
    private String action;
    private String day; // El front enviará "MON", "TUE", etc.
    private String startHour; // Formato "HH:mm" (ej. "08:30")
    private String endHour;
    private Integer moduleId;
    private Integer blockId;

    // Constructores
    public ScheduleChangeDTO() {
        // Constructor vacío requerido para la deserialización de JSON por Spring Boot
    }

    // Getters y Setters
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public Integer getBlockId() {
        return blockId;
    }

    public void setBlockId(Integer blockId) {
        this.blockId = blockId;
    }
}