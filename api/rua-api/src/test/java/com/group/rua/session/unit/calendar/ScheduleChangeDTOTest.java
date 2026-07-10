package com.group.rua.session.unit.calendar;

import com.group.rua.session.calendar.ScheduleChangeDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScheduleChangeDTOTest {

    @Test
    void testGettersAndSetters() {
        // Arrange
        ScheduleChangeDTO dto = new ScheduleChangeDTO();

        // Act
        dto.setAction("Move");
        dto.setDay("MON");
        dto.setStartHour("08:30");
        dto.setEndHour("09:40");
        dto.setModuleId(5);
        dto.setBlockId(12);

        // Assert
        assertEquals("Move", dto.getAction());
        assertEquals("MON", dto.getDay());
        assertEquals("08:30", dto.getStartHour());
        assertEquals("09:40", dto.getEndHour());
        assertEquals(5, dto.getModuleId());
        assertEquals(12, dto.getBlockId());
    }
}