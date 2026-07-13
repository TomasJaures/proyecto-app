package com.group.rua.session.unit;

import com.group.rua.RuaConfig;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RuaConfigTest {
    @Test
    void testConfig() {
        // Esto cubre la carga de la clase y la constante
        assertNotNull(RuaConfig.FRONTEND_URL);
    }
}