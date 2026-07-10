package com.group.rua.session.unit.calendar;

import com.group.rua.entities.Block.BlockState;
import com.group.rua.entities.Block.WeekDay;
import com.group.rua.session.calendar.CalendarBlockDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalendarBlockDTOTest {

    @Test
    void testConstructorAndGetters() {
        // Arrange
        LocalTime start = LocalTime.of(14, 30);
        LocalTime end = LocalTime.of(16, 0);

        // Act
        CalendarBlockDTO dto = new CalendarBlockDTO(
                10, 20, WeekDay.WED, start, end,
                BlockState.NO_PROJECTIONS, 5, 2,
                30, "Programación", "PROG200"
        );

        // Assert
        assertEquals(10, dto.getCalendarId());
        assertEquals(20, dto.getBlockId());
        assertEquals(WeekDay.WED, dto.getWeekDay());
        assertEquals(start, dto.getStartHour());
        assertEquals(end, dto.getEndHour());
        assertEquals(BlockState.NO_PROJECTIONS, dto.getBlockState());
        assertEquals(5, dto.getModuleId());
        assertEquals(2, dto.getModuleNum());
        assertEquals(30, dto.getSubjectId());
        assertEquals("Programación", dto.getSubjectName());
        assertEquals("PROG200", dto.getCode());
    }
}