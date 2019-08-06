package blockchain;

public class Miner implements Runnable {

  private String id;
  private BlockChain blockChain;
  private static Long LB = 1L; // one sec.
  private static Long UB = LB * 10L; // ten secs.

  public Miner(String id, BlockChain blockChain) {
    this.id = id;
    this.blockChain = blockChain;
  }

  @Override
  public void run() {
    var newBlock = new Block(this.blockChain.size(), this.id, this.blockChain.getLastHash(),
        this.blockChain.getN());
    var computeTime = newBlock.getComputeTime();
    var offered = this.blockChain.offer(newBlock);
    if (offered) {
      if (computeTime <= LB) {
        this.blockChain.incrementN();
        newBlock.setNumZeroChanges("N was increased to " + this.blockChain.getN());
      } else if (computeTime <= UB) {
        newBlock.setNumZeroChanges("N stays the same");
      } else {
        this.blockChain.decrementN();
        newBlock.setNumZeroChanges("N was decreased by 1");
      }
    }
  }
}
