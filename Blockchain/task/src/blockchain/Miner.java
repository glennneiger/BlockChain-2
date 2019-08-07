package blockchain;

public class Miner implements Runnable {

  private String id;
  private BlockChain blockChain;

  public Miner(String id, BlockChain blockChain) {
    this.id = id;
    this.blockChain = blockChain;
  }

  @Override
  public void run() {
    var newBlock = new Block(this.blockChain.size(), this.id, this.blockChain.getLastHash(),
        this.blockChain.getN());
    while (!newBlock.validHash()) {
      newBlock.calcHash(this.blockChain.getMessage());
    }
    this.blockChain.offer(newBlock);
  }
}
