package nz.ac.wgtn.swen301.a3.server;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class LogsServlet extends HttpServlet {

  Persistency p = new Persistency();
  Gson gson = new GsonBuilder().setPrettyPrinting().create();

  ArrayList<String> levels = new ArrayList<>(Arrays.asList("ALL","DEBUG","INFO","WARN","ERROR","FATAL","TRACE","OFF"));

  //constructor with no parameters as per assignment
  public LogsServlet(){}


  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    resp.setContentType("application/json");

    String _limit = req.getParameter("limit");
    String _level = req.getParameter("level");

    //sets the status code to '400' when parameters are incorrect
    if(!checkParamaters(req)) resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    else {


      //converts parameter values to appropriate types for process
      int limit = Integer.parseInt(_limit);
      String level = _level.toUpperCase();

      int count = 0;
      PrintWriter pw = resp.getWriter();

      pw.print("[\n");
        for (LogEvent d : p.getDB()) {
          if (count < limit && d.getLevel().equals(level)) {
              pw.println(gson.toJson(d));
            }
          count++;
      }
      pw.print("]");
    }
  }


  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    // converts log into a 'LogEvent' object which is then stored.
    StringBuilder sb = new StringBuilder();
    BufferedReader br = req.getReader();
    String line;
    while((line = br.readLine()) != null){
      sb.append(line);
      System.out.println(line);
    }
    LogEvent log = gson.fromJson(sb.toString(), LogEvent.class);
    if(checkID(log)){
      p.postLog(log);
    }
  }

  // this deletes logs... one at a time? Not sure how this needs to work.
  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    p.clearDB();
  }


  //checks ID....? maybe expand to more
  private boolean checkID(LogEvent lg){
    ArrayList<LogEvent> logs = p.getDB();
    try {

      for (LogEvent log : logs) {
        assertNotEquals(lg.getId(), log.getId());
      }
    }catch(AssertionError e){
      return false;
    }
    return true;
  }

  //checks the parameters - throws assertion error parameters or parameter names are invalid
  private boolean checkParamaters(HttpServletRequest req){

    try {
      String limit = req.getParameter("limit");
      String level = req.getParameter("level");

      // if the parameters are null (meaning the parameter values are incorrectly
      // inputted or incorrect parameter names)
      assertNotNull(limit);
      assertNotNull(level);

      // non-integer string
      assertTrue(Integer.parseInt(limit) > 0);
      // level is not correct
      assertTrue(levels.contains(level.toUpperCase()));

    }catch (AssertionError e){
      return false;
    }

    return true;
  }

}
