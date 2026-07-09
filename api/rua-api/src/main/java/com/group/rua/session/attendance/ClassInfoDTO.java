package com.group.rua.session.attendance;

// Importa el Enum de tu entidad Block para que coincida el tipo de dato
import com.group.rua.entities.Block.BlockState;
import com.group.rua.entities.Block.WeekDay;
import java.time.LocalTime;

public class ClassInfoDTO {
    private Integer blockId;
    private BlockState blockState; // <-- ENUM
    private Integer classId;
    private Boolean isAnulled;
    private Integer num;
    private String subjectName;
    private String code;
    private String timeState;
    private WeekDay weekDay;
    private LocalTime startHour;
    private LocalTime endHour;

    public LocalTime getEndHour() {
        return endHour;
    }

    public void setEndHour(LocalTime endHour) {
        this.endHour = endHour;
    }

    public LocalTime getStartHour() {
        return startHour;
    }

    public void setStartHour(LocalTime startHour) {
        this.startHour = startHour;
    }

    public WeekDay getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(WeekDay weekDay) {
        this.weekDay = weekDay;
    }

    // EL CONSTRUCTOR EXIGIDO POR SPRING:
    public ClassInfoDTO(Integer blockId, BlockState blockState, Integer classId, Boolean isAnulled, Integer num, String subjectName, String code, WeekDay weekDay, LocalTime startHour, LocalTime endHour) {
        this.blockId = blockId;
        this.blockState = blockState;
        this.classId = classId;
        this.isAnulled = isAnulled;
        this.num = num;
        this.subjectName = subjectName;
        this.code = code;
        this.weekDay = weekDay;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    // Getters y Setters...

    public Integer getBlockId() {
        return blockId;
    }

    public BlockState getBlockState() {
        return blockState;
    }

    public Integer getClassId() {
        return classId;
    }

    public String getCode() {
        return code;
    }

    public Boolean getIsAnulled() {
        return isAnulled;
    }

    public Integer getNum() {
        return num;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getTimeState() {
        return timeState;
    }

    public void setTimeState(String timeState) {
        this.timeState = timeState;
    }
}