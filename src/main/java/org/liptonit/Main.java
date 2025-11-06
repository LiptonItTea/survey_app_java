package org.liptonit;


import org.liptonit.cli.Menu;
import org.liptonit.entity.Answer;
import org.liptonit.entity.Question;
import org.liptonit.entity.Survey;
import org.liptonit.entity.User;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        SurveyAppService.signUp("amogus", "amogus@mail.ru", "amoguspassword");
        SurveyAppService.createSurvey(
                "amogus", "amoguspassword",
                "test", "very cool test",
                new String[]{"2+2*2 ?", "integral(1/(1+x^7) ?"},
                new Boolean[]{false, true},
                new String[][]{{"1", "8", "6"}, {"idk", "i'm a catboy", "take a chill pill brother"}}
        );

        Menu.run();

        SurveyAppService.deleteUser("amogus", "amoguspassword");
    }   
}