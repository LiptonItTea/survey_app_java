package org.liptonit;


import org.liptonit.cli.Menu;
import org.liptonit.entity.Survey;
import org.liptonit.entity.User;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Vars.userRepository.createEntity(new User(0, "amogus", "amogus@mail.ru", LocalDate.now(), "amoguspassword"));
        Vars.surveyRepository.createEntity(new Survey(0, "test", "very cool test", 0));
//            Vars.questionRepository.createEntity(new Question(0, "2+2*2 ?", false, 0));
//                Vars.answerRepository.createEntity(new Answer(0, "1", 0));
//                Vars.answerRepository.createEntity(new Answer(0, "8", 0));
//                Vars.answerRepository.createEntity(new Answer(0, "6", 0));
//        Vars.questionRepository.createEntity(new Question(0, "integral(1/(1+x^7) ?", false, 0));
//            Vars.answerRepository.createEntity(new Answer(0, "idk", 1));
//            Vars.answerRepository.createEntity(new Answer(0, "i'm a catboy", 1));
//            Vars.answerRepository.createEntity(new Answer(0, "take a chill pill brother", 1));

        Menu.run();
    }
}