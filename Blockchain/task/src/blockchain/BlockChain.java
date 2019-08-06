package blockchain;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class BlockChain {

  private ArrayList<Block> blocks = new ArrayList<>();
  private Integer numZeros;

  public BlockChain(Integer numZeros) {
    this.numZeros = numZeros;
  }

  public boolean validate() {
    for (int i = 0; i < this.blocks.size(); ++i) {
      if (i > 0 && !this.blocks.get(i).getPrevHash().equals(this.blocks.get(i - 1).getHash())) {
        return false;
      }
      if (!this.blocks.get(i).getHash().startsWith("0".repeat(this.numZeros))) {
        return false;
      }
    }
    return true;
  }

  public void newBlock() {
    this.blocks.add(
        new Block(this.blocks.size(),
            this.blocks.size() == 0 ? "0" : this.blocks.get(this.blocks.size() - 1).getHash(),
            this.numZeros)
    );
  }

  @Override
  public String toString() {
    return this.blocks.stream().map(Block::toString).collect(Collectors.joining("\n"));
  }
}
