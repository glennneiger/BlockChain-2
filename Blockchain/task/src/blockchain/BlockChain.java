package blockchain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class BlockChain implements Serializable {

  private static Long LB = 1L; // one sec.
  private static Long UB = LB * 10L; // ten secs.

  private ArrayList<Block> blocks = new ArrayList<>();
  private Integer numZeros = 0;
  private ArrayList<String> messages = new ArrayList<>();

  public boolean validate() {
    for (int i = 0; i < this.blocks.size(); ++i) {
      if (i > 0 && !this.blocks.get(i).getPrevHash().equals(this.blocks.get(i - 1).getHash())) {
        return false;
      }
    }
    return true;
  }

  public synchronized void offer(Block newBlock) {
    if (this.blocks.size() == 0 || newBlock.getPrevHash()
        .equals(this.blocks.get(this.blocks.size() - 1).getHash()) && newBlock.getHash()
        .startsWith("0".repeat(this.numZeros))) {
      this.blocks.add(newBlock);
      this.messages = new ArrayList<>(
          this.messages.subList(newBlock.getMessageSize(), this.messages.size()));
      var computeTime = newBlock.getComputeTime();
      if (computeTime <= LB) {
        this.numZeros++;
        newBlock.setNumZeroChanges("N was increased to " + this.numZeros.toString());
      } else if (computeTime <= UB) {
        newBlock.setNumZeroChanges("N stays the same");
      } else {
        this.numZeros--;
        newBlock.setNumZeroChanges("N was decreased by 1");
      }
    }
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

  public synchronized void addMessage(String msg) {
    this.messages.add(msg);
  }

  public synchronized ArrayList<String> getMessage() {
    return new ArrayList<>(this.messages);
  }

  @Override
  public String toString() {
    return this.blocks.subList(0, 5).stream().map(Block::toString)
        .collect(Collectors.joining("\n"));
  }
}
