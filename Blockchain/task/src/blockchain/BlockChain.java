package blockchain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class BlockChain implements Serializable {

  private ArrayList<Block> blocks = new ArrayList<>();
  private Integer numZeros = 0;

  public boolean validate() {
    for (int i = 0; i < this.blocks.size(); ++i) {
      if (i > 0 && !this.blocks.get(i).getPrevHash().equals(this.blocks.get(i - 1).getHash())) {
        return false;
      }
    }
    return true;
  }

  public synchronized boolean offer(Block newBlock) {
    if (this.blocks.size() == 0) {
      this.blocks.add(newBlock);
      return true;
    } else if (newBlock.getPrevHash()
        .equals(this.blocks.get(this.blocks.size() - 1).getHash()) && newBlock.getHash()
        .startsWith("0".repeat(this.numZeros))) {
      this.blocks.add(newBlock);
      return true;
    }
    return false;
  }

  public synchronized void incrementN() {
    this.numZeros++;
  }

  public synchronized void decrementN() {
    this.numZeros--;
  }

  public synchronized int getN() {
    return this.numZeros;
  }

  public synchronized int size() {
    return this.blocks.size();
  }

  public synchronized String getLastHash() {
    return this.blocks.size() == 0 ? "0" : this.blocks.get(this.blocks.size() - 1).getHash();
  }

  @Override
  public String toString() {
    return this.blocks.subList(0, 5).stream().map(Block::toString)
        .collect(Collectors.joining("\n"));
  }
}
