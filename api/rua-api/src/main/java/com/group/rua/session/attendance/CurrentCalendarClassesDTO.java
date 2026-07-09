package com.group.rua.session.attendance;

import java.util.List;

public class CurrentCalendarClassesDTO {

    private int currentWeek;
    private List<ClassInfoDTO> classInfoDTOS;

    public CurrentCalendarClassesDTO() {}

    public CurrentCalendarClassesDTO(int currentWeek, List<ClassInfoDTO> classInfoDTOS) {
        this.currentWeek = currentWeek;
        this.classInfoDTOS = classInfoDTOS;
    }

    public int getCurrentWeek() {
        return currentWeek;
    }

    public void setCurrentWeek(int currentWeek) {
        this.currentWeek = currentWeek;
    }

    public List<ClassInfoDTO> getClassInfoDTOS() {
        return classInfoDTOS;
    }

    public void setClassInfoDTOS(List<ClassInfoDTO> classInfoDTOS) {
        this.classInfoDTOS = classInfoDTOS;
    }
}