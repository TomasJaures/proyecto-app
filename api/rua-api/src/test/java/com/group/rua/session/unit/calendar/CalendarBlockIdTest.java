package com.group.rua.session.unit.calendar;

import com.group.rua.entities.CalendarBlockId;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CalendarBlockIdTest {

    @Test
    void testEqualsAndHashCode() {
        CalendarBlockId id1 = new CalendarBlockId();
        id1.calendarId = 1;
        id1.blockId = 10;
        CalendarBlockId id2 = new CalendarBlockId();
        id2.calendarId = 1;
        id2.blockId = 10;

        assertEquals(id1, id1);

        assertNotEquals(null, id1);
        assertNotEquals(new Object(), id1);

        assertEquals(id1, id2);
        assertEquals(id1.hashCode(), id2.hashCode());
    }
}