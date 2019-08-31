package apis;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**.
 * a thread pool for gui
 *
 * @author Guo Ziyang
 */
public class GuiThreadPool {

  private static final int CORE_SIZE = 8;
  private static final int MAX_SIZE = 12;
  private static final long KEEP_ALIVE_TIME = 30;
  private static final int QUEUE_SIZE = 50000;
  private static ThreadPoolExecutor threadPool
          = new ThreadPoolExecutor(CORE_SIZE, MAX_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS,
          new ArrayBlockingQueue<>(QUEUE_SIZE), new ThreadPoolExecutor.AbortPolicy());

  public static ThreadPoolExecutor getThreadPool() {
    return threadPool;
  }
  
}
