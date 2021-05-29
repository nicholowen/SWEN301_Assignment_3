package nz.ac.wgtn.swen301.a3.server;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class StatsCSVServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
   processCSV(resp);
  }

  public void processCSV(HttpServletResponse response) throws IOException {
    response.setContentType("text/csv");
    response.addHeader("content-disposition", "attachment; filename=\"" + "statscsv" + ".csv\"");
    PrintWriter writer = response.getWriter();

    Persistency p = new Persistency();

    writer.write("Logger\tALL\tTRACE\tDEBUG\tINFO\tWARN\tERROR\tFATAL\tOFF\r\n");

    HashMap<String, LinkedHashMap<String, Integer>> table = p.getLogLevels();
    for(String logger : table.keySet()){
      writer.write(logger);
      writer.write("\t");
      for(Integer count : table.get(logger).values()){
        writer.write(count.toString());
        writer.write("\t");
      }
      writer.write("\r\n");
    }

    writer.flush();
    writer.close();
  }
}
