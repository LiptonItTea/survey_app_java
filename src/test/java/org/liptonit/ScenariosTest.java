package org.liptonit;

import org.junit.jupiter.api.Test;
import org.liptonit.entity.User;

import static org.junit.jupiter.api.Assertions.*;

public class ScenariosTest {

    @Test
    void testSignInSuccessful() {
        // Arrange
        String nickname = "testuser_success";
        String password = "password";
        Scenarios.signUp(nickname, "test_success@example.com", password);

        // Act
        User user = Scenarios.signIn(nickname, password);

        // Assert
        assertNotNull(user);
        assertEquals(nickname, user.getNickname());
    }

    @Test
    void testSignInWrongPassword() {
        // Arrange
        String nickname = "testuser_wrongpass";
        String password = "password";
        Scenarios.signUp(nickname, "test_wrongpass@example.com", password);

        // Act
        User user = Scenarios.signIn(nickname, "wrongpassword");

        // Assert
        assertNull(user);
    }

    @Test
    void testSignInNonExistentUser() {
        // Act
        User user = Scenarios.signIn("nonexistentuser", "password");

        // Assert
        assertNull(user);
    }
}
