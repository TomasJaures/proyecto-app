package com.group.rua.session.unit.attendance;

import com.group.rua.entities.ProgramSubject;
import com.group.rua.entities.ProgramSubjectId;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ProgramSubjectTest {

    @Test
    void testProgramSubjectEntity() {
        // Arrange
        ProgramSubject entity = new ProgramSubject();
        ProgramSubjectId id = new ProgramSubjectId();
        id.programId = 1;
        id.subjectId = 2;

        // Act
        entity.id = id;

        // Assert
        assertNotNull(entity.id);
        assertEquals(1, entity.id.programId);
        assertEquals(2, entity.id.subjectId);
    }
}