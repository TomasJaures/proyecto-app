package com.group.rua.Session.Attendance;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OpenRouterService {

    @Value("${openrouter.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final String API_URL = "https://openrouter.ai/api/v1/chat/completions";

    public String generateAttendanceWarning(String studentName, String subjectName, double percentage) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);
        headers.set("HTTP-Referer", "http://localhost:1428");

        String prompt = String.format(
                "Actúa como un coordinador académico formal, empático y proactivo. Redacta un correo electrónico breve dirigido al estudiante %s. El objetivo es informarle que su registro actual de asistencia en la clase de %s se encuentra en un %s%%, lo cual requiere su atención temprana.\n" +
                        "Instrucciones estrictas:\n" +
                        "- Utiliza un tono de apoyo y acompañamiento.\n" +
                        "- NO asumas que el estudiante ha reprobado ni utilices un lenguaje punitivo o alarmista.\n" +
                        "- Sugiérele amablemente que, en caso de tener dudas o justificativos pendientes, se acerque a conversar directamente con su docente o bien con el/la director/a de su respectiva carrera para revisar su situación formalmente.\n" +
                        "- El correo debe ser conciso y debe firmarse exclusivamente como 'Sistema de Registro Universitario de Asistencia (RUA)'. Redacta únicamente el cuerpo del mensaje.",
                studentName, subjectName, percentage
        );

        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", prompt);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "meta-llama/llama-3-8b-instruct:free");
        requestBody.put("messages", List.of(message));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(API_URL, request, Map.class);
            Map<String, Object> responseBody = response.getBody();
            List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
            Map<String, Object> firstChoice = choices.get(0);
            Map<String, Object> messageContent = (Map<String, Object>) firstChoice.get("message");

            return (String) messageContent.get("content");
        } catch (Exception e) {
            throw new RuntimeException("Error al comunicarse con OpenRouter: " + e.getMessage());
        }
    }
}