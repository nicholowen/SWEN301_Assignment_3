package ac.nz.wgtn.swen301.a3.server;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import nz.ac.wgtn.swen301.a3.server.LogEvent;
import nz.ac.wgtn.swen301.a3.server.LogsServlet;
import nz.ac.wgtn.swen301.a3.server.Persistency;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static java.time.format.DateTimeFormatter.ofPattern;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestGetLogs {

  MockHttpServletRequest request;
  MockHttpServletResponse response;
  LogsServlet service;

  @Before
  public void init() {
    service = new LogsServlet();
    response = new MockHttpServletResponse();
    request = new MockHttpServletRequest();
  }

  @Test
  public void testInvalidRequestResponseCode1() throws IOException, ServletException {

    service.doGet(request,response);

    assertEquals(400,response.getStatus());
  }

  @Test
  public void testInvalidRequestResponseCode2() throws IOException, ServletException {

    //level is incorrect

    request.setParameter("limit", "1");
    request.setParameter("level", "1");
    service.doGet(request,response);

    assertEquals(400,response.getStatus());
  }

  @Test
  public void testInvalidRequestResponseCode3() throws IOException, ServletException {

    //level is incorrect

    request.setParameter("limit", "-1");
    request.setParameter("level", "WARN");
    service.doGet(request,response);

    assertEquals(400,response.getStatus());
  }

  @Test
  public void testInvalidRequestResponseCode4() throws IOException, ServletException {

    //level is incorrect
    request.setParameter("INVALID", "10");
    request.setParameter("level", "DEBUG");
    service.doGet(request,response);

    assertEquals(400,response.getStatus());
  }

  @Test
  public void testValidRequestResponseCode1() throws IOException, ServletException {

    //level is incorrect
    request.setParameter("limit", "10");
    request.setParameter("level", "DEBUG");
    service.doGet(request,response);

    assertEquals(200,response.getStatus());
  }

  @Test
  public void testValidContentType() throws IOException, ServletException {

    request.setParameter("limit", "1");
    request.setParameter("level", "1");
    service.doGet(request,response);

    assertTrue(response.getContentType().startsWith("application/json"));
  }

//  @Test
//  public void testLevelParameter() throws IOException, ServletException {
//    Persistency p = new Persistency();
//    Gson gson = new Gson();
//    ArrayList<String> levels = p.getAll_levels();
//    for(int i = 0; i < 5; i++){
//      try {
//        Thread.sleep(1000);
//      } catch (InterruptedException e) {
//        e.printStackTrace();
//      }
//      JsonObject json = buildJSON("test"+i, levels.get(i));
//      request.setContent(json.toString().getBytes(StandardCharsets.UTF_8));
//      service.doPost(request, response);
//    }
//    String line = response.getContentAsString();
//    System.out.println(line);
//
//    for(int i = 0; i < 5; i++) {
////      LogEvent log = gson.fromJson(pw.toString(), LogEvent.class);
////      System.out.println(log.getTimestamp());
//
//    }
//
//  }

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
