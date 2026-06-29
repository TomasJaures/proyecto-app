package com.group.rua.session.unit.attendance;
import com.group.rua.entities.AttendanceId;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AttendanceIdTest {

    @Test
    void testConstructorsAndEqualsAndHashCode() {
        AttendanceId id1 = new AttendanceId(10, 50);
        AttendanceId id2 = new AttendanceId(10, 50);
        AttendanceId id3 = new AttendanceId(99, 100);
        AttendanceId emptyId = new AttendanceId();

        assertEquals(id1, id2);
        assertNotEquals(id1, id3);
        assertEquals(id1.hashCode(), id2.hashCode());

        assertNotEquals(null, id1);

        assertFalse(id1.equals(new Object()));
        assertNotNull(emptyId);
    }
}