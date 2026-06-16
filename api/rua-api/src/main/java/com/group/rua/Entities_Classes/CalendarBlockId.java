package com.group.rua.Entities_Classes;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * Clave primaria compuesta para la tabla calendar_blocks (N:M entre calendar y blocks).
 * Requiere implementar equals() y hashCode() por convención de JPA con @EmbeddedId.
 */
@Embeddable
public class CalendarBlockId implements Serializable {

    @Column(name = "calendar_id")
    public Integer calendarId;

    @Column(name = "block_id")
    public Integer blockId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CalendarBlockId)) return false;
        CalendarBlockId that = (CalendarBlockId) o;
        return Objects.equals(calendarId, that.calendarId) &&
               Objects.equals(blockId, that.blockId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(calendarId, blockId);
    }

}
