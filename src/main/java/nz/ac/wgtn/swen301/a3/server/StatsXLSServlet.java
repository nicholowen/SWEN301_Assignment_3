package nz.ac.wgtn.swen301.a3.server;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class StatsXLSServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    procressXLS(resp);
  }

  public void procressXLS(HttpServletResponse resp) throws IOException {
    resp.setContentType("application/vnd.ms-excel");
    OutputStream out = resp.getOutputStream();
    Persistency p = new Persistency();
    HashMap<String, LinkedHashMap<String, Integer>> table = p.getLogLevels();

    XSSFWorkbook workbook = new XSSFWorkbook();
    XSSFSheet sheet = workbook.createSheet("XLS Log Stats");

    int rowCount = 1;

    for (String logger : table.keySet()) {
      Row row = sheet.createRow(++rowCount);

      int columnCount = 1;

      for (Object field : table.get(logger).values()) {
        Cell cell = row.createCell(++columnCount);
        if (field instanceof String) {
          cell.setCellValue((String) field);
        } else if (field instanceof Integer) {
          cell.setCellValue((Integer) field);
        }
      }
    }
    workbook.write(out);

  }

}
