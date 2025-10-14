package org.liptonit;

import org.junit.jupiter.api.Test;
import org.liptonit.entity.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScenariosTest {

    @Test
    void example() {
        List<User> all = Vars.userRepository.readEntities(u -> true);

        List<User> truth = new ArrayList<>();
        truth.add(new User(0, "amogus", "amogus@mail.ru", LocalDate.now(), "amoguspassword"));
    }
}
