package com.group.rua.session.unit.exceptions;

import com.group.rua.exceptions.NoExistingRole;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class NoExistingRoleTest {

    @Test
    void testNoExistingRoleException() {
        NoExistingRole exception = new NoExistingRole("Test");

        assertNotNull(exception);
    }
}