package nz.ac.wgtn.swen301.a3.client;

import okhttp3.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Client {

  static OkHttpClient client = new OkHttpClient();


  public static Response doGetRequest(String suffix) throws IOException {
    HttpUrl.Builder urlBuilder = HttpUrl.parse("http://localhost:8080/resthome4logs" + suffix).newBuilder();

    String url = urlBuilder.build().toString();

    Request request = new Request.Builder()
          .url(url)
          .build();
    Call call = client.newCall(request);
    Response response = call.execute();
    return response;

  }

  public static void main(String args[]) throws IOException {

//    if(args.length!=2){
//      throw new Error("No parameters detected. Please insert a format and filename");
//    }


//    String format = args[0];
//    String fileString = args[1];

    String format = "excel";
    String fileString = "stats.xls";
    Response response;
    FileOutputStream outputStream = new FileOutputStream(fileString);

    if(format.equals("excel")){
      response = doGetRequest("/statsxls");
      ByteArrayInputStream inputStream = new ByteArrayInputStream(response.body().bytes());
      Workbook wb = WorkbookFactory.create(inputStream);
      wb.write(outputStream);
      return;
    }else if(format.equals("csv")){
      response = doGetRequest("/statscsv");
      ByteArrayInputStream inputStream = new ByteArrayInputStream(response.body().bytes());
      Workbook wb = WorkbookFactory.create(inputStream);
      wb.write(outputStream);
      return;
    }else{
      throw new Error("Format or filename is invalid. Please try again.");
    }
  }
}
