package com.group.rua.Session.Calendar;

import com.group.rua.Entities_Classes.Block.WeekDay;
import java.time.LocalTime;

public class AddBlockDTO {
    public Integer moduleId;
    public WeekDay weekDay;
    public LocalTime startHour;
    public LocalTime endHour;
}