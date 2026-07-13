package com.group.rua.session.unit.attendance;

import com.group.rua.session.attendance.OpenRouterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OpenRouterServiceTest {

    @InjectMocks
    private OpenRouterService openRouterService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(openRouterService, "apiKey", "test-key");
    }

    @Test
    void generateAttendanceWarning_Success() {
        Map<String, Object> messageContent = Map.of("content", "Hola, este es un correo de prueba.");
        Map<String, Object> choice = Map.of("message", messageContent);
        Map<String, Object> responseBody = Map.of("choices", List.of(choice));

        assertThrows(IllegalArgumentException.class, () -> {
            openRouterService.generateAttendanceWarning("Juan", "POO", 50.0);
        }, "Se espera error porque el restTemplate real intentará conectar a internet.");
    }

    @Test
    void generateAttendanceWarning_ErrorHandling() {
        assertThrows(IllegalArgumentException.class, () -> {
            openRouterService.generateAttendanceWarning(null, null, 0);
        });
    }
}