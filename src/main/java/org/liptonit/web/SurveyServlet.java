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
import org.liptonit.entity.Survey;
import org.liptonit.entity.User;

import java.io.IOException;
import java.util.List;

@WebServlet
public class SurveyServlet extends HttpServlet {
    private final ObjectMapper mapper;

    public SurveyServlet() {
        this.mapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        List<Survey> surveys = Vars.surveyRepository.readEntities(s -> true);

        mapper.writeValue(resp.getOutputStream(), surveys);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        Survey survey = mapper.readValue(req.getInputStream(), Survey.class);

        Survey s = Vars.surveyRepository.createEntity(survey);

        mapper.writeValue(resp.getOutputStream(), s);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String pathInfo = req.getPathInfo(); // api/users/{surveyId} => pathInfo={surveyId}
        String id = pathInfo.substring(1);
        Long surveyId = Long.parseLong(id);

        Survey s = Vars.surveyRepository.deleteEntityById(surveyId);

        mapper.writeValue(resp.getOutputStream(), s);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String pathInfo = req.getPathInfo(); // api/users/{surveyId} => pathInfo={surveyId}
        String id = pathInfo.substring(1);
        Long surveyId = Long.parseLong(id);

        Survey survey = mapper.readValue(req.getInputStream(), Survey.class);

        Vars.surveyRepository.updateEntityById(surveyId, s -> {
            s.setName(survey.getName());
            s.setDescription(survey.getDescription());
            s.setIdUserCreator(survey.getIdUserCreator());
        });

        mapper.writeValue(resp.getOutputStream(), Vars.surveyRepository.readEntityById(surveyId));
    }
}
