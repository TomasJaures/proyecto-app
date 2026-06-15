package com.group.rua.Session.Unit.Attendance;
import com.group.rua.Entities_Classes.AttendanceId;
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

        assertFalse(id1.equals(null));
        assertFalse(id1.equals(new Object()));
        assertNotNull(emptyId);
    }
}