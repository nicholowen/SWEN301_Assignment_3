package ac.nz.wgtn.swen301.a3.server;

import com.google.gson.JsonObject;
import nz.ac.wgtn.swen301.a3.server.LogsServlet;
import nz.ac.wgtn.swen301.a3.server.Persistency;
import nz.ac.wgtn.swen301.a3.server.StatsCSVServlet;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


import static java.time.format.DateTimeFormatter.ofPattern;

public class TestStatsCSV {
    MockHttpServletRequest request;
    MockHttpServletResponse response;
    LogsServlet service;
    Persistency p = new Persistency();
    ArrayList<String> levels = p.getAll_levels();

    @Before
    public void init() {
        service = new LogsServlet();
        response = new MockHttpServletResponse();
        request = new MockHttpServletRequest();
    }

    @Test
    public void parseCSVTest_1() throws ServletException, IOException {
        StatsCSVServlet csvServlet = new StatsCSVServlet();

        for(int i = 0; i < 5; i++){
            JsonObject json = buildJSON("test" + i, levels.get(i));
            request.setContent(json.toString().getBytes(StandardCharsets.UTF_8));
            service.doPost(request, response);
        }

        csvServlet.processCSV(response);

        String header = "attachment; filename = statscsv.csv";
        assertEquals(header, response.getHeaderValue("content-disposition"));
    }



    public JsonObject buildJSON(String message, String level) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", String.valueOf(UUID.randomUUID()));
        jsonObject.addProperty("message", message);
        jsonObject.addProperty("timestamp", ofPattern("dd-MM-yyyy HH:mm:ss").format(LocalDateTime.now()));
        jsonObject.addProperty("thread", "main");
        jsonObject.addProperty("logger", "example.logger");
        jsonObject.addProperty("level", level);
        jsonObject.addProperty("errorDetails", "string");
        return jsonObject;
    }
}
