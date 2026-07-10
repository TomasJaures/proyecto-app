package com.group.rua.session.unit.calendar;

import com.group.rua.session.attendance.ClassInfoDTO;
import com.group.rua.session.attendance.CurrentCalendarClassesDTO;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CurrentCalendarClassesDTOTest {

    @Test
    void testConstructorsAndGettersSetters() {
        // Arrange
        List<ClassInfoDTO> classList = new ArrayList<>();

        // Act
        CurrentCalendarClassesDTO dto1 = new CurrentCalendarClassesDTO();
        dto1.setCurrentWeek(28);
        dto1.setClassInfoDTOS(classList);

        // Assert
        assertEquals(28, dto1.getCurrentWeek());
        assertEquals(classList, dto1.getClassInfoDTOS());

        // Act
        CurrentCalendarClassesDTO dto2 = new CurrentCalendarClassesDTO(30, classList);

        // Assert
        assertEquals(30, dto2.getCurrentWeek());
        assertEquals(classList, dto2.getClassInfoDTOS());
    }
}