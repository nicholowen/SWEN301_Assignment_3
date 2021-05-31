package nz.ac.wgtn.swen301.a3.client;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonObjectParser;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.apache.http.impl.io.HttpResponseWriter;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class Client {

  static OkHttpClient client = new OkHttpClient();


  public static HttpResponse doGetRequest(String suffix) throws IOException {
    HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    HttpRequestFactory requestFactory
          = HTTP_TRANSPORT.createRequestFactory(
          HttpRequest::getContent);
    GenericUrl url = new GenericUrl("http://localhost:8080/resthome4logs"+suffix);
    HttpRequest request = requestFactory.buildGetRequest(url);
    HttpResponse response = request.execute();

    return response;
  }

  public static void main(String args[]) throws IOException {

    String format = "csv";
    String fileString = "test.csv";

    HttpResponse response;
    FileOutputStream outputStream = new FileOutputStream(fileString);

    if(format.equals("excel")){
      response = doGetRequest("/statsxls");
      ByteArrayInputStream inputStream = new ByteArrayInputStream(response.getContent().readAllBytes());
//      HttpHeaders headers = response.getHeaders();
//      Workbook ww = new XSSFWorkbook();
      Workbook wb = WorkbookFactory.create(inputStream);
      wb.write(outputStream);
      return;
    }else if(format.equals("csv")){
      response = doGetRequest("/statscsv");
//      FileOutputStream out = new FileOutputStream(response.getContent().toString());
      ByteArrayInputStream inputStream = new ByteArrayInputStream(response.getContent().readAllBytes());
//      InputStream input = new InputStreamReader();
      HttpHeaders headers = response.getHeaders();
//      Workbook ww = WorkbookFactory.create(headers);
      Workbook ww = new XSSFWorkbook(inputStream);
//      Workbook wb = WorkbookFactory.create(inputStream);
      ww.write(outputStream);
      return;
    }else{
      throw new Error("Format or filename is invalid. Please try again.");
    }
  }
}
