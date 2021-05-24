package nz.ac.wgtn.swen301.a3.server;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class LogsServlet extends HttpServlet {

  Persistency p = new Persistency();

  //constructor with no parameters as per assignment
  public LogsServlet(){}


  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    int limit = (req.getParameter("limit") != null) ? Integer.parseInt(req.getParameter("limit")) : -1;
    String level = req.getParameter("level").toUpperCase();
    int count = 0;

    PrintWriter pw = resp.getWriter();
    pw.print("[");
    for(LogEvent d : p.getDB()){
      if(limit != -1 && count < limit) {
        if(d.getLevel().equals(level)) {
          pw.println(d.toString());
        }
      }
      count++;
    }
    pw.print("]");
  }

  // this posts the logs I generate
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    StringBuilder sb = new StringBuilder();
    BufferedReader br = req.getReader();

    String line;
    while((line = br.readLine()) != null){
      sb.append(line);
      System.out.println(line);
    }
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    LogEvent lg = gson.fromJson(sb.toString(), LogEvent.class);
    p.postLog(lg);

  }

  // this deletes logs... one at a time? Not sure how this needs to work.
  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    p.clearDB();
  }

}
