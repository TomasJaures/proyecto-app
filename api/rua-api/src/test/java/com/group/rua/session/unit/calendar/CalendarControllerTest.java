package com.group.rua.session.unit.calendar;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.rua.session.attendance.CurrentCalendarClassesDTO;
import com.group.rua.session.calendar.CalendarController;
import com.group.rua.session.calendar.CalendarService;
import com.group.rua.session.calendar.ScheduleChangeDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CalendarController.class)
@AutoConfigureMockMvc(addFilters = false)
class CalendarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CalendarService calendarService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getStudentCalendar_ReturnsOk() throws Exception {
        when(calendarService.getStudentCalendar(1)).thenReturn(new CurrentCalendarClassesDTO());
        mockMvc.perform(get("/api/calendar/1/classes")).andExpect(status().isOk());
    }

    @Test
    void getStudentCalendar_ThrowsException_ReturnsBadRequest() throws Exception {
        when(calendarService.getStudentCalendar(1)).thenThrow(new RuntimeException("Error"));
        mockMvc.perform(get("/api/calendar/1/classes")).andExpect(status().isBadRequest());
    }

    @Test
    void getStudentCalendarByWeek_ReturnsOk() throws Exception {
        when(calendarService.getStudentCalendarByWeek(1, 28))
                .thenReturn(new CurrentCalendarClassesDTO());
        mockMvc.perform(get("/api/calendar/1/classesByWeek")
                .param("weekId", "28"))
                .andExpect(status().isOk());
    }

    @Test
    void getStudentCalendarByWeek_ThrowsException_ReturnsBadRequest() throws Exception {
        when(calendarService.getStudentCalendarByWeek(1, 28)).thenThrow(new RuntimeException("Error"));
        mockMvc.perform(get("/api/calendar/1/classesByWeek")
                .param("weekId", "28"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void generateCurrentWeekClasses_ReturnsOk() throws Exception {
        when(calendarService.generateClassesForCurrentWeek(1)).thenReturn(new CurrentCalendarClassesDTO());
        mockMvc.perform(post("/api/calendar/1/classes/generate")).andExpect(status().isOk());
    }

    @Test
    void generateCurrentWeekClasses_ThrowsException_ReturnsBadRequest() throws Exception {
        when(calendarService.generateClassesForCurrentWeek(1)).thenThrow(new RuntimeException("Error"));
        mockMvc.perform(post("/api/calendar/1/classes/generate")).andExpect(status().isBadRequest());
    }

    @Test
    void applyScheduleChanges_ReturnsOk() throws Exception {
        List<ScheduleChangeDTO> changes = new ArrayList<>();
        mockMvc.perform(post("/api/calendar/1/changes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(changes)))
                .andExpect(status().isOk());
        verify(calendarService, times(1)).processScheduleChanges(eq(1), any());
    }

    @Test
    void applyScheduleChanges_ThrowsException_ReturnsBadRequest() throws Exception {
        doThrow(new RuntimeException("Invalid Data")).when(calendarService).processScheduleChanges(eq(1), any());

        mockMvc.perform(post("/api/calendar/1/changes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[]"))
                .andExpect(status().isBadRequest());
    }
}