package com.group.rua.session.unit.calendar;

import com.group.rua.entities.CalendarBlockId;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CalendarBlockIdTest {

    @Test
    void testEqualsAndHashCode() {
        // Preparar las instancias
        CalendarBlockId id1 = new CalendarBlockId();
        id1.calendarId = 1;
        id1.blockId = 10;

        CalendarBlockId id2 = new CalendarBlockId();
        id2.calendarId = 1;
        id2.blockId = 10;

        CalendarBlockId diffCalendar = new CalendarBlockId();
        diffCalendar.calendarId = 2;
        diffCalendar.blockId = 10;

        CalendarBlockId diffBlock = new CalendarBlockId();
        diffBlock.calendarId = 1;
        diffBlock.blockId = 20;

        // Camino: this == o (Comparar consigo mismo)
        assertTrue(id1.equals(id1));

        // Camino: o == null o distinta clase
        assertFalse(id1.equals(null));
        assertFalse(id1.equals(new Object()));

        // Camino: Valores idénticos
        assertTrue(id1.equals(id2));
        assertEquals(id1.hashCode(), id2.hashCode());

        // Camino: Valores distintos
        assertFalse(id1.equals(diffCalendar));
        assertFalse(id1.equals(diffBlock));
    }
}