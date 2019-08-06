package blockchain;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

  private final static int POOL_SIZE = 4;
  private final static int NUM_TASKS = 20;

  public static void main(String[] args) throws InterruptedException {
    BlockChain bc = new BlockChain();
    var executor = Executors.newFixedThreadPool(POOL_SIZE);
    for (int i = 0; i < NUM_TASKS; ++i) {
      executor.submit(new Miner(String.valueOf(i), bc));
    }
    executor.shutdown();
    executor.awaitTermination(60, TimeUnit.SECONDS);
    System.out.println(bc);
  }
}
