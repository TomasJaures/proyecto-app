package com.group.rua.session.integration.calendar;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.rua.entities.Block;
import com.group.rua.repositories.BlockRepo;
import com.group.rua.session.calendar.AddBlockDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class CalendarBlockIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BlockRepo blockRepo;

    @Autowired
    private ObjectMapper objectMapper;

    // Caso de Uso Configuración de Clase
    @Test
    void addBlock_EndpointShouldSaveInDatabase() throws Exception {
        // Arrange
        Integer calendarId = 1;
        AddBlockDTO dto = new AddBlockDTO(
                99,
                Block.WeekDay.MON,
                LocalTime.of(14, 30),
                LocalTime.of(16, 0));

        // Act
        mockMvc.perform(post("/api/calendars/" + calendarId + "/blocks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        // Assert
        List<Block> savedBlocks = blockRepo.findAll();
        assertFalse(savedBlocks.isEmpty(), "Debería haber al menos un bloque guardado en H2");

        Block lastBlock = savedBlocks.get(savedBlocks.size() - 1);
        assertEquals(99, lastBlock.moduleId);
        assertEquals(Block.WeekDay.MON, lastBlock.weekDay);
    }
}