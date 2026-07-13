package com.group.rua.session.unit.program;

import com.group.rua.session.program.ProgramInfoDTO;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProgramInfoDTOTest {

    @Test
    void testProgramInfoDTO() {
        ProgramInfoDTO dto = new ProgramInfoDTO(1, "Fisica", "FIS101", new ArrayList<>());

        assertEquals(1, dto.subjectId);
        assertEquals("Fisica", dto.subjectName);
        assertEquals("FIS101", dto.code);
        assertNotNull(dto.modules);
    }
}