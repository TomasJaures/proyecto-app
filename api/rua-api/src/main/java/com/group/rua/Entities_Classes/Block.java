package com.group.rua.Entities_Classes;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "blocks")
public class Block {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "block_id")
    public Integer blockId;

    // FK N:1 hacia modules — mapeada como Integer (convención del proyecto)
    @Column(name = "module_id")
    public Integer moduleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "week_day")
    public WeekDay weekDay;

    @Column(name = "start_hour")
    public LocalTime startHour;

    @Column(name = "end_hour")
    public LocalTime endHour;

    @Enumerated(EnumType.STRING)
    @Column(name = "block_state")
    public BlockState blockState = BlockState.NORMAL;

    // ENUMs del dominio, anidados para mantener cohesión con la entidad
    public enum WeekDay {
        MON, TUE, WED, THU, FRI, SAT, SUN
    }

    public enum BlockState {
        NORMAL, NO_PROJECTIONS, COMPLETE_ANULED, REMOVED
    }

}
