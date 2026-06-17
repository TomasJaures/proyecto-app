package com.group.rua.Session.Calendar;



import java.time.LocalTime;

import com.group.rua.Entities_Classes.Block.BlockState;
import com.group.rua.Entities_Classes.Block.WeekDay;

/**
 * DTO que agrupa toda la información necesaria para renderizar
 * un bloque en el calendario de la interfaz.
 */
public class CalendarBlockDTO {

    // --- Identificadores ---
    private Integer calendarId;
    private Integer blockId;
    private Integer moduleId;
    private Integer moduleNum;   // Número de módulo (1-9)
    private Integer subjectId;

    // --- Datos de asignatura ---
    private String subjectName;
    private String code;

    // --- Datos de bloque (definen posición/color en la UI) ---
    private WeekDay weekDay;
    private LocalTime startHour;
    private LocalTime endHour;
    private BlockState blockState;

    // ----------------------------------------------------------------
    // Constructor usado por la JPQL query (orden debe coincidir)
    // ----------------------------------------------------------------
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
