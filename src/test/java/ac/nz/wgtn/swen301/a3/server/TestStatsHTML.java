package ac.nz.wgtn.swen301.a3.server;

import com.google.gson.JsonObject;
import nz.ac.wgtn.swen301.a3.server.LogsServlet;
import nz.ac.wgtn.swen301.a3.server.Persistency;
import nz.ac.wgtn.swen301.a3.server.StatsCSVServlet;
import nz.ac.wgtn.swen301.a3.server.StatsServlet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.jsoup.parser.Parser;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static java.time.format.DateTimeFormatter.ofPattern;

public class TestStatsHTML {

    MockHttpServletRequest request;
    MockHttpServletResponse response;
    LogsServlet service;
    StatsServlet statsService;

    Persistency p = new Persistency();
    ArrayList<String> levels = p.getAll_levels();

    @Before
    public void init() throws ServletException, IOException {
        service = new LogsServlet();
        statsService = new StatsServlet();
        response = new MockHttpServletResponse();
        request = new MockHttpServletRequest();
        generateLogs();
    }

    @Test
    public void test1() throws UnsupportedEncodingException {
        StatsCSVServlet service = new StatsCSVServlet();
        service.doGet(request, response);
        Document doc = Jsoup.parse(response.getContentAsString());
        String body = doc.body().wholeText();
        System.out.println(body);

        assert body.startsWith("Logger\tALL\tTRACE\tDEBUG\tINFO\tWARN\tERROR\tFATAL\tOFF\n");
        String[] lines = body.split("\n");

        int logs = 0;
        for (int i = 1; i < lines.length; i++) {      //for every line in the content
            String[] line = lines[i].split("\t");
            for (int j = 1; j < line.length; j++) {   //for every token in the line
                assert Integer.parseInt(line[j]) >= 0;  //check that each count is not negative
                logs += Integer.parseInt(line[j]);
            }
        }
        System.out.println(logs);
        System.out.println(p.getDB().size());
        assert p.getDB().size() == logs;
    }



    public void generateLogs() throws ServletException, IOException {
        p.clearDB();
        for(int i = 0; i < 5; i++){
            JsonObject json = buildJSON("test" + i, levels.get(i), "example.logger" + i);
            request.setContent(json.toString().getBytes(StandardCharsets.UTF_8));
            service.doPost(request, response);
        }
    }

    public JsonObject buildJSON(String message, String level, String logger) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", String.valueOf(UUID.randomUUID()));
        jsonObject.addProperty("message", message);
        jsonObject.addProperty("timestamp", ofPattern("dd-MM-yyyy HH:mm:ss").format(LocalDateTime.now()));
        jsonObject.addProperty("thread", "main");
        jsonObject.addProperty("logger", logger);
        jsonObject.addProperty("level", level);
        jsonObject.addProperty("errorDetails", "string");
        return jsonObject;
    }
}