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

  public LinkedHashMap<String, int[]> getLogLevels(){

    /**
     * for each log, add to a map of string and a list. The list contains a number of all levels
     */

    LinkedHashMap<String, int[]> test1 = new LinkedHashMap<>();

    for(int i = 0; i < DB.size(); i++){
      test1.put(getDB().get(i).getLogger(), new int[8]);
    }


    for(LogEvent lg : DB){
      for(int i = 0; i < 8; i++){
        if(lg.getLevel().equals(all_levels[i])) {
          test1.get(lg.getLogger())[i]++;
        }
      }
    }

    for(String k : test1.keySet()){
      System.out.println(k + " ");
      for(int i : test1.get(k)){
        System.out.print(i + " ");
      }
    }






//    HashMap<String, LinkedHashMap<String, Integer>> logger = new HashMap<>();
//    LinkedHashMap<String, Integer> lvls = new LinkedHashMap<>();
//    for(String s : all_levels){
//      lvls.put(s, 0);
//    }
//    Persistency p = new Persistency();
//    ArrayList<LogEvent> DB = p.getDB();
//
//    for(LogEvent lg : DB){
//      logger.put(lg.getLogger(), lvls);
//    }
//
//    for(LogEvent lg : DB){
//      LinkedHashMap<String, Integer> levels = logger.get(lg.getLogger());
//
//        int q = levels.get(lg.getLevel()) + 1;
//        levels.put(lg.getLevel(), q);
//    }
    return test1;
  }

  public ArrayList<String> getAll_levels(){
    return new ArrayList<>(Arrays.asList(all_levels));
  }

}
