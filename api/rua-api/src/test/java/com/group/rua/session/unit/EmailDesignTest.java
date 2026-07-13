package com.group.rua.session.unit;

import com.group.rua.general.EmailDesign;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EmailDesignTest {
    @Test
    void testInitialization() {
        EmailDesign design = new EmailDesign();
        assertNotNull(design);
    }
}