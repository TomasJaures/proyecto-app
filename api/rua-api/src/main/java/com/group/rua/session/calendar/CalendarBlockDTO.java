package com.group.rua.session.calendar;

import com.group.rua.entities.Block.BlockState;
import com.group.rua.entities.Block.WeekDay;

import java.time.LocalTime;

/**
 * DTO que agrupa toda la información necesaria para renderizar
 * un bloque en el calendario de la interfaz.
 */
public class CalendarBlockDTO {

    private Integer calendarId;
    private Integer blockId;
    private Integer moduleId;
    private Integer moduleNum;
    private Integer subjectId;
    private String subjectName;
    private String code;
    private WeekDay weekDay;
    private LocalTime startHour;
    private LocalTime endHour;
    private BlockState blockState;

    // ----------------------------------------------------------------
    // Constructor usado por la JPQL query (orden debe coincidir)
    // ----------------------------------------------------------------
    @SuppressWarnings("java:S107")
    public CalendarBlockDTO(
            Integer calendarId,
            Integer blockId,
            WeekDay weekDay,
            LocalTime startHour,
            LocalTime endHour,
            BlockState blockState,
            Integer moduleId,
            Integer moduleNum,
            Integer subjectId,
            String subjectName,
            String code
    ) {
        this.calendarId  = calendarId;
        this.blockId     = blockId;
        this.weekDay     = weekDay;
        this.startHour   = startHour;
        this.endHour     = endHour;
        this.blockState  = blockState;
        this.moduleId    = moduleId;
        this.moduleNum   = moduleNum;
        this.subjectId   = subjectId;
        this.subjectName = subjectName;
        this.code        = code;
    }

    // ----------------------------------------------------------------
    // Getters
    // ----------------------------------------------------------------
    public Integer getCalendarId()  { return calendarId; }
    public Integer getBlockId()     { return blockId; }
    public Integer getModuleId()    { return moduleId; }
    public Integer getModuleNum()   { return moduleNum; }
    public Integer getSubjectId()   { return subjectId; }
    public String  getSubjectName() { return subjectName; }
    public String  getCode()        { return code; }
    public WeekDay getWeekDay()     { return weekDay; }
    public LocalTime getStartHour() { return startHour; }
    public LocalTime getEndHour()   { return endHour; }
    public BlockState getBlockState(){ return blockState; }
}
