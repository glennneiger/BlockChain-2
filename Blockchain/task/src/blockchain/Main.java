package blockchain;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

  private final static int POOL_SIZE = 4;
  private final static int NUM_TASKS = 20;

  public static void main(String[] args) throws InterruptedException {
    BlockChain bc = new BlockChain();
    User[] users = {
        new User(bc, "me: hey"),
        new User(bc, "bob: my"),
        new User(bc, "alice: 1st"),
        new User(bc, "X: block"),
        new User(bc, "Y: chain"),
        new User(bc, "Z: is"),
        new User(bc, "every1: working!")
    };

    var executor = Executors.newFixedThreadPool(POOL_SIZE);
    for (int i = 0; i < NUM_TASKS; ++i) {
      executor.submit(new Miner(String.valueOf(i), bc));
      executor.submit(users[i % users.length]);
    }

    executor.shutdown();
    executor.awaitTermination(120, TimeUnit.SECONDS);
    System.out.println(bc);
  }
}
