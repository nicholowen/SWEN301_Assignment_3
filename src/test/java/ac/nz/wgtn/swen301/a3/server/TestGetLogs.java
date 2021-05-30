package ac.nz.wgtn.swen301.a3.server;


import com.google.gson.JsonObject;
import nz.ac.wgtn.swen301.a3.server.LogsServlet;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;
import java.time.LocalDateTime;
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
