package blockchain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class BlockChain {

  private ArrayList<Block> blocks = new ArrayList<>();
  private Integer numZeros;
  private String path;

  public BlockChain(Integer numZeros, String path) {
    this.numZeros = numZeros;
    this.path = path;
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

  public void save() {
    var file = new File(this.path);
    try {
      file.createNewFile();
    } catch (IOException e) {
    }
    try (FileOutputStream fos = new FileOutputStream(
        file, false); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
      oos.writeObject(this.blocks);
    } catch (IOException e) {
      System.out.println("failed to write, error: " + e.getMessage());
    }
  }

  @SuppressWarnings("unchecked")
  private void load() {
    try (FileInputStream fis = new FileInputStream(this.path); ObjectInputStream ois = new ObjectInputStream(fis)) {
      this.blocks = (ArrayList<Block>) ois.readObject();
    } catch (IOException | ClassNotFoundException e) {
      System.out.println("failed to read, error: " + e.getMessage());
    }
  }

  @Override
  public String toString() {
    return this.blocks.stream().map(Block::toString).collect(Collectors.joining("\n"));
  }
}
