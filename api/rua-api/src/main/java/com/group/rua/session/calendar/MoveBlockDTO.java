package com.group.rua.session.calendar;

import com.group.rua.entities.Block.WeekDay;
import java.time.LocalTime;

public record MoveBlockDTO (WeekDay weekDay, LocalTime startHour, LocalTime endHour){
}