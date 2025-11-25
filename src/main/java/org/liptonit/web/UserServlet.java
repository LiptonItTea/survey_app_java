package org.liptonit.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.liptonit.SurveyAppService;
import org.liptonit.Vars;
import org.liptonit.entity.User;

import java.io.IOException;
import java.util.List;

@WebServlet
public class UserServlet extends HttpServlet {

    private final ObjectMapper mapper;

    public UserServlet() {
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
        this.mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        List<User> users = Vars.userRepository.readEntities(u -> true);

        mapper.writeValue(resp.getOutputStream(), users);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = mapper.readValue(req.getInputStream(), User.class);

        User u = SurveyAppService.signUp(user.getNickname(), user.getEmail(), user.getHashedPassword());

        mapper.writeValue(resp.getOutputStream(), u);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo(); // api/users/{userId} => pathInfo={userId}
        String id = pathInfo.substring(1);
        Long userId = Long.parseLong(id);

        User u = Vars.userRepository.deleteEntityById(userId);

        mapper.writeValue(resp.getOutputStream(), u);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo(); // api/users/{userId} => pathInfo={userId}
        String id = pathInfo.substring(1);
        Long userId = Long.parseLong(id);

        User user = mapper.readValue(req.getInputStream(), User.class);

        Vars.userRepository.updateEntityById(userId, u -> {
            u.setNickname(user.getNickname());
            u.setEmail(user.getEmail());
            if (user.getHashedPassword() != null)
                u.setHashedPassword(user.getHashedPassword());
        });

        mapper.writeValue(resp.getOutputStream(), Vars.userRepository.readEntityById(userId));
    }
}
