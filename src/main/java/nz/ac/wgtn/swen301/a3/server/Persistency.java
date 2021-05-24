package nz.ac.wgtn.swen301.a3.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.apache.log4j.spi.LoggingEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Simulates a Database using a static list
 */
public class Persistency {

  static List<LogEvent> DB = new ArrayList<>();

  public void postLog(LogEvent lg){

    DB.add(lg);

  }

  public List<LogEvent> getDB(){
    return DB;
  }

  public void clearDB(){
    DB.clear();
  }

}
