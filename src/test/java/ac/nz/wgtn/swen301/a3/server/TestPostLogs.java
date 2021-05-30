package ac.nz.wgtn.swen301.a3.server;

import nz.ac.wgtn.swen301.a3.server.LogEvent;
import nz.ac.wgtn.swen301.a3.server.LogsServlet;
import nz.ac.wgtn.swen301.a3.server.Persistency;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.ServletException;
import java.io.BufferedReader;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TestPostLogs {

  Persistency persistency = new Persistency();

  @Test
  public void test1() throws ServletException, IOException {

    MockHttpServletRequest request = new MockHttpServletRequest();

    LogEvent log = new LogEvent("d290f1ee-6c54-4b01-90e6-d701748f085", "application started", "04-05-2021 10:12:00", "main", "com.example.Foo", "DEBUG", "string");

    String test = "{\n" +
          "  \"d290f1ee-6c54-4b01-90e6-d701748f0851\",\n" +
          "  \"application started\",\n" +
          "  \"04-05-2021 10:12:00\",\n" +
          "  \"main\",\n" +
          "  \"com.example.Foo\",\n" +
          "  \"DEBUG\",\n" +
          "  \"string\"\n" +
          "}";

    request.setParameter(test);
    MockHttpServletResponse response = new MockHttpServletResponse();

    LogsServlet service = new LogsServlet();
    service.doPost(request, response);

    assertEquals(409, response.getStatus());

  }

  //04-05-2021 10:12:00


}
