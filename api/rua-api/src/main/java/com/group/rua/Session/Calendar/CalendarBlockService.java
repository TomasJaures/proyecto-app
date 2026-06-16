package com.group.rua.Session.Calendar;



import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalendarBlockService {

    private final CalendarBlockRepository calendarBlockRepository;

    public CalendarBlockService(CalendarBlockRepository calendarBlockRepository) {
        this.calendarBlockRepository = calendarBlockRepository;
    }

    /**
     * Obtiene todos los bloques de un calendario dado su ID.
     *
     * Si el calendario no existe o no tiene bloques asignados,
     * devuelve una lista vacía (el frontend maneja el caso vacío).
     *
     * TODO: Bloques con estado REMOVED se excluyen: ya no son visibles
     * en la interfaz y no tiene sentido enviarlos al frontend.
     */
    public List<CalendarBlockDTO> getBlocksByCalendar(Integer calendarId) {
        return calendarBlockRepository.findBlocksByCalendarId(calendarId);
    }
}
