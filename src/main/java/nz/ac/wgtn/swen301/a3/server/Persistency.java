package nz.ac.wgtn.swen301.a3.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.apache.log4j.spi.LoggingEvent;

import javax.servlet.http.HttpServlet;
import java.util.*;

/**
 * Simulates a Database using a static list
 */
public class Persistency {

  private static final String[] all_levels = {"ALL", "TRACE", "DEBUG","INFO", "WARN", "ERROR", "FATAL", "OFF"};

  static ArrayList<LogEvent> DB = new ArrayList<>();

  public void postLog(LogEvent lg){
    DB.add(lg);
  }

  public ArrayList<LogEvent> getDB(){
    return DB;
  }

  public void clearDB(){
    DB.clear();
  }

  public HashMap<String, LinkedHashMap<String, Integer>> getLogLevels(){
    HashMap<String, LinkedHashMap<String, Integer>> logger = new HashMap<>();
    LinkedHashMap<String, Integer> lvls = new LinkedHashMap<>();
    for(String s : all_levels){
      lvls.put(s, 0);
    }
    Persistency p = new Persistency();
    ArrayList<LogEvent> DB = p.getDB();

    for(LogEvent lg : DB){
      logger.put(lg.getLogger(), lvls);
    }

    for(LogEvent lg : DB){
      LinkedHashMap<String, Integer> levels = logger.get(lg.getLogger());
//      int q = 0;
//      if(!levels.containsKey(lg.getLevel())){
//        levels.put(lg.getLevel(), 1);
//      }else{
        int q = levels.get(lg.getLevel()) + 1;
        levels.put(lg.getLevel(), q);
//      }
    }
    return logger;
  }

}
