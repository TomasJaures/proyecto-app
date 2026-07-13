package com.group.rua.session.unit.program;

import com.group.rua.session.program.ProgramChangeDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProgramChangeDTOTest {

    @Test
    void testProgramChangeDTO() {
        ProgramChangeDTO dto = new ProgramChangeDTO();
        dto.field = "Subject";
        dto.action = "Add";
        dto.newSubjectName = "Matematicas";
        dto.subjectId = 1;
        dto.code = "MAT101";
        dto.num = 1;
        dto.moduleId = 10;

        assertEquals("Subject", dto.field);
        assertEquals("Add", dto.action);
        assertEquals(1, dto.subjectId);
    }
}