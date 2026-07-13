package com.group.rua.session.unit.attendance;

import com.group.rua.session.attendance.WeekClassAttendanceInfoDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WeekClassAttendanceInfoDTOTest {

    @Test
    void testWeekClassAttendanceInfoDTO() {
        WeekClassAttendanceInfoDTO dto = new WeekClassAttendanceInfoDTO(100, "PRESENT", true);

        assertEquals(100, dto.getClassId());
        assertEquals("PRESENT", dto.getTimeState());
        assertTrue(dto.getHasAssisted());

        dto.setClassId(200);
        dto.setTimeState("PAST");
        dto.setHasAssisted(false);

        assertEquals(200, dto.getClassId());
        assertEquals("PAST", dto.getTimeState());
        assertFalse(dto.getHasAssisted());
    }
}