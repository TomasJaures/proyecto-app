package com.group.rua.session.calendar;

import com.group.rua.entities.Block.WeekDay;
import java.time.LocalTime;

public record AddBlockDTO (Integer moduleId, WeekDay weekDay, LocalTime startHour, LocalTime endHour){
}