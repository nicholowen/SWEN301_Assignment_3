package nz.ac.wgtn.swen301.a3.client;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Client {

  public static HttpResponse doGetRequest(String suffix) throws IOException {
    HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(HttpRequest::getContent);
    GenericUrl url = new GenericUrl("http://localhost:8080/resthome4logs"+suffix);
    HttpRequest request = requestFactory.buildGetRequest(url);

    return request.execute();
  }

  public static void main(String args[]) throws IOException {

    if(args.length != 2){
      System.out.println("Incorrect Parameters - Please enter a file type and filename");
    }

    String format = "excel";
    String fileString = "stats.xls";

    HttpResponse response;
    FileOutputStream outputStream = new FileOutputStream(fileString);

    if(format.equals("excel") && fileString.endsWith(".xls")){
      response = doGetRequest("/statsxls");
      ByteArrayInputStream inputStream = new ByteArrayInputStream(response.getContent().readAllBytes());
      Workbook wb = WorkbookFactory.create(inputStream);
      wb.write(outputStream);
      return;
    }else if(format.equals("csv") && fileString.endsWith(".csv")){
      response = doGetRequest("/statscsv");
      response.getContent().transferTo(outputStream);
      return;
    }else{
      throw new Error("Format or filename is invalid. Please try again.");
    }
  }
}
