package com.group.rua.session.unit.attendance;

import com.group.rua.entities.Block.BlockState;
import com.group.rua.entities.Block.WeekDay;
import com.group.rua.session.attendance.ClassInfoDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClassInfoDTOTest {

    @Test
    void testConstructorAndGetters() {
        // Arrange
        LocalDate date = LocalDate.of(2026, 7, 10);
        LocalTime start = LocalTime.of(8, 30);
        LocalTime end = LocalTime.of(9, 40);

        // Act
        ClassInfoDTO dto = new ClassInfoDTO(
                1, BlockState.NORMAL, 100, true, 1,
                "Matemáticas", "MAT101", WeekDay.MON,
                start, end, date
        );

        // Assert
        assertEquals(1, dto.getBlockId());
        assertEquals(BlockState.NORMAL, dto.getBlockState());
        assertEquals(100, dto.getClassId());
        assertTrue(dto.getIsAnulled());
        assertEquals(1, dto.getNum());
        assertEquals("Matemáticas", dto.getSubjectName());
        assertEquals("MAT101", dto.getCode());
        assertEquals(WeekDay.MON, dto.getWeekDay());
        assertEquals(start, dto.getStartHour());
        assertEquals(end, dto.getEndHour());
        assertEquals(date, dto.getClassDate());
    }

    @Test
    void testSetters() {
        // Arrange
        ClassInfoDTO dto = new ClassInfoDTO(null, null, null, null, null, null, null, null, null, null, null);

        LocalDate newDate = LocalDate.of(2026, 8, 15);
        LocalTime newStart = LocalTime.of(10, 0);
        LocalTime newEnd = LocalTime.of(11, 30);

        // Act
        dto.setClassDate(newDate);
        dto.setStartHour(newStart);
        dto.setEndHour(newEnd);
        dto.setWeekDay(WeekDay.FRI);
        dto.setTimeState("FUTURE");

        // Assert
        assertEquals(newDate, dto.getClassDate());
        assertEquals(newStart, dto.getStartHour());
        assertEquals(newEnd, dto.getEndHour());
        assertEquals(WeekDay.FRI, dto.getWeekDay());
        assertEquals("FUTURE", dto.getTimeState());
    }
}