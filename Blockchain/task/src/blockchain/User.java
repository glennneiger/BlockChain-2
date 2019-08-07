package blockchain;

public class User implements Runnable {

  private BlockChain blockChain;
  private String msg;

  public User(BlockChain blockChain, String msg) {
    this.blockChain = blockChain;
    this.msg = msg;
  }

  @Override
  public void run() {
    this.blockChain.addMessage(this.msg);
  }
}
