package nz.ac.wgtn.swen301.a3.server;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class StatsServlet extends HttpServlet {

  public StatsServlet(){}

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    processTable(req, resp);
  }

  private void processTable(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    response.setContentType("text/html");
    Persistency p = new Persistency();
    PrintWriter out = response.getWriter();

    out.println("<html>");
    out.println("<head>");
    out.println("<title>Log Information</title>");
    out.println("</head>");
    out.println("<body>");

    out.println("<h1>Echoing Log Information</h1>");
    out.println("<table border><th>Logger</th><th>ALL</th><th>TRACE</th><th>DEBUG</th><th>INFO</th><th>WARN</th><th>ERROR</th><th>FATAL</th><th>OFF</th>");
    // list headers send by the client
    HashMap<String, LinkedHashMap<String, Integer>> lg = p.getLogLevels();
    for(String logger_name : lg.keySet()) {
      HashMap<String, Integer> logger = lg.get(logger_name);
      out.print("<tr><td>");
      out.print(logger_name);
      out.print("<td>");
      if(logger.containsKey("ALL")) out.print(logger.get("ALL"));
      else out.print(0);
      out.print("</td>");
      out.print("<td>");
      if(logger.containsKey("TRACE")) out.print(logger.get("TRACE"));
      else out.print(0);
      out.print("</td>");
      out.print("<td>");
      if(logger.containsKey("DEBUG")) out.print(logger.get("DEBUG"));
      else out.print(0);
      out.print("</td>");
      out.print("<td>");
      if(logger.containsKey("INFO")) out.print(logger.get("INFO"));
      else out.print(0);
      out.print("</td>");
      out.print("<td>");
      if(logger.containsKey("WARN")) out.print(logger.get("WARN"));
      else out.print(0);
      out.print("</td>");
      out.print("<td>");
      if(logger.containsKey("ERROR")) out.print(logger.get("ERROR"));
      else out.print(0);
      out.print("</td>");
      out.print("<td>");
      if(logger.containsKey("FATAL")) out.print(logger.get("FATAL"));
      else out.print(0);
      out.print("</td>");
      out.print("<td>");
      if(logger.containsKey("OFF")) out.print(logger.get("OFF"));
      else out.print(0);
      out.print("</td>");
    }

    out.println("</td></tr>");
    out.println("</table>");
    out.println("</body>");
    out.println("</html>");

    out.close();
  }
}

