package com.group.rua.session.unit.attendance;

import com.group.rua.entities.ProgramSubjectId;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProgramSubjectIdTest {

    @Test
    void testEqualsAndHashCode() {
        ProgramSubjectId id1 = new ProgramSubjectId();
        id1.programId = 5;
        id1.subjectId = 50;

        ProgramSubjectId id2 = new ProgramSubjectId();
        id2.programId = 5;
        id2.subjectId = 50;

        ProgramSubjectId diffProgram = new ProgramSubjectId();
        diffProgram.programId = 9;
        diffProgram.subjectId = 50;

        ProgramSubjectId diffSubject = new ProgramSubjectId();
        diffSubject.programId = 5;
        diffSubject.subjectId = 99;

        assertEquals(id1, id1);

        assertNotEquals(null, id1);
        assertNotEquals("Un String Cualquiera", id1);

        assertEquals(id1, id2);
        assertEquals(id1.hashCode(), id2.hashCode());

        assertNotEquals(id1, diffProgram);
        assertNotEquals(id1, diffSubject);
    }
}