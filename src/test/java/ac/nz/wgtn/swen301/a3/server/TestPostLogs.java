package ac.nz.wgtn.swen301.a3.server;

import nz.ac.wgtn.swen301.a3.server.LogEvent;
import nz.ac.wgtn.swen301.a3.server.LogsServlet;
import nz.ac.wgtn.swen301.a3.server.Persistency;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

public class TestPostLogs {

  Persistency persistency = new Persistency();

  MockHttpSession mock = new MockHttpSession();
  MockHttpServletRequest request;
  MockHttpServletResponse response;

  @Test
  public void test1(){

    request.setServerName("localhost");
    request.setRequestURI("/log");

    request.setParameter("limit", "1");
    request.setParameter("level", "debug");

    LogEvent lg = new LogEvent("d290f1ee-6c54-4b01-90e6-d701748f0851",
          "application started",
          "04-05-2021 10:12:00",
          "main",
          "com.example.Foo",
          "DEBUG",
          "string");



  }

}
