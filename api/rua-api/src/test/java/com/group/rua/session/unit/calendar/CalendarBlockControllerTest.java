package com.group.rua.session.unit.calendar;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.rua.entities.Block.WeekDay;
import com.group.rua.session.calendar.AddBlockDTO;
import com.group.rua.session.calendar.CalendarBlockController;
import com.group.rua.session.calendar.CalendarBlockService;
import com.group.rua.session.calendar.MoveBlockDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CalendarBlockController.class)
@AutoConfigureMockMvc(addFilters = false) // Desactiva seguridad/JWT temporalmente para testear solo la API pura
public class CalendarBlockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CalendarBlockService calendarBlockService;

    @Autowired
    private ObjectMapper objectMapper;

    // Verifica que el endpoint GET devuelva un 200 OK
    @Test
    void getBlocks_ReturnsOk() throws Exception {
        when(calendarBlockService.getBlocksByCalendar(1)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/calendars/1/blocks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // Verifica que el endpoint PATCH para remover devuelva 200 OK
    @Test
    void removeBlock_ReturnsOk() throws Exception {
        mockMvc.perform(patch("/api/calendars/blocks/1/remove")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(calendarBlockService).removeBlock(1);
    }

    // Verifica que el endpoint POST procese el JSON correctamente al añadir
    @Test
    void addBlock_ReturnsOk() throws Exception {
        AddBlockDTO dto = new AddBlockDTO(
                5,
                WeekDay.MON,
                LocalTime.of(8, 0),
                LocalTime.of(9, 30)
        );

        mockMvc.perform(post("/api/calendars/1/blocks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(calendarBlockService).addBlockToCalendar(eq(1), any(AddBlockDTO.class));
    }

    // Verifica que el endpoint PATCH para mover procese el JSON correctamente
    @Test
    void moveBlock_ReturnsOk() throws Exception {
        MoveBlockDTO dto = new MoveBlockDTO(
                WeekDay.TUE,
                LocalTime.of(10, 0),
                LocalTime.of(11, 30)
        );

        mockMvc.perform(patch("/api/calendars/blocks/1/move")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(calendarBlockService).moveBlock(eq(1), eq(dto.weekDay()), eq(dto.startHour()), eq(dto.endHour()));
    }
}