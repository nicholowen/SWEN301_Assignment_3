package nz.ac.wgtn.swen301.a3.server;

import java.util.*;

/**
 * Simulates a Database using a static list
 */
public class Persistency {

  private static final String[] all_levels = {"ALL", "TRACE", "DEBUG","INFO", "WARN", "ERROR", "FATAL", "OFF"};

  static PriorityQueue<LogEvent> DB = new PriorityQueue<>();

  public void postLog(LogEvent lg){
    DB.offer(lg);
  }

  public ArrayList<LogEvent> getDB(){
    return new ArrayList<>(DB);
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
        int q = levels.get(lg.getLevel()) + 1;
        levels.put(lg.getLevel(), q);
    }
    return logger;
  }

  public ArrayList<String> getAll_levels(){
    return new ArrayList<>(Arrays.asList(all_levels));
  }

}
