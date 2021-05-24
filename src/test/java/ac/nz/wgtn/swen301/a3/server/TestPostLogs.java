package ac.nz.wgtn.swen301.a3.server;

import nz.ac.wgtn.swen301.a3.server.LogsServlet;
import nz.ac.wgtn.swen301.a3.server.Persistency;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.Test;

public class TestPostLogs {

  Persistency persistency = new Persistency();

  @Test
  public void test1(){
    Logger logger = Logger.getLogger(LogsServlet.class);
    LoggingEvent lg = new LoggingEvent("Test", logger, Level.WARN, "this is a test", null);
//    persistency.postLog(lg);
  }

}
