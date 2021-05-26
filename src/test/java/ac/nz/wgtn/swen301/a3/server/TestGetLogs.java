package ac.nz.wgtn.swen301.a3.server;


import nz.ac.wgtn.swen301.a3.server.LogsServlet;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestGetLogs {


  @Test
  public void testInvalidRequestResponseCode1() throws IOException, ServletException {

    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();
    LogsServlet service = new LogsServlet();
    service.doGet(request,response);

    assertEquals(400,response.getStatus());
  }

  @Test
  public void testInvalidRequestResponseCode2() throws IOException, ServletException {

    //level is incorrect
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setParameter("limit", "1");
    request.setParameter("level", "1");
    MockHttpServletResponse response = new MockHttpServletResponse();
    LogsServlet service = new LogsServlet();
    service.doGet(request,response);

    assertEquals(400,response.getStatus());
  }

  @Test
  public void testInvalidRequestResponseCode3() throws IOException, ServletException {

    //level is incorrect
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setParameter("limit", "-1");
    request.setParameter("level", "WARN");
    MockHttpServletResponse response = new MockHttpServletResponse();
    LogsServlet service = new LogsServlet();
    service.doGet(request,response);

    assertEquals(400,response.getStatus());
  }

  @Test
  public void testInvalidRequestResponseCode4() throws IOException, ServletException {

    //level is incorrect
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setParameter("INVALID", "10");
    request.setParameter("level", "DEBUG");
    MockHttpServletResponse response = new MockHttpServletResponse();
    LogsServlet service = new LogsServlet();
    service.doGet(request,response);

    assertEquals(400,response.getStatus());
  }

  @Test
  public void testValidRequestResponseCode1() throws IOException, ServletException {

    //level is incorrect
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setParameter("limit", "10");
    request.setParameter("level", "DEBUG");
    MockHttpServletResponse response = new MockHttpServletResponse();
    LogsServlet service = new LogsServlet();
    service.doGet(request,response);

    assertEquals(200,response.getStatus());
  }



  @Test
  public void testValidContentType() throws IOException, ServletException {

    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setParameter("limit", "1");
    request.setParameter("level", "1");
    MockHttpServletResponse response = new MockHttpServletResponse();

    LogsServlet service = new LogsServlet();
    service.doGet(request,response);

    assertTrue(response.getContentType().startsWith("application/json"));
  }

}
