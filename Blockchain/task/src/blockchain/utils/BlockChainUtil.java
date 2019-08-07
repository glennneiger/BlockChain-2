package blockchain.utils;

import blockchain.BlockChain;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class BlockChainUtil {

  public static void save(BlockChain blockChain, String path) {
    var file = new File(path);
    try {
      file.createNewFile();
    } catch (IOException e) {
    }
    try (FileOutputStream fos = new FileOutputStream(
        file, false); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
      oos.writeObject(blockChain);
    } catch (IOException e) {
      System.out.println("failed to write, error: " + e.getMessage());
    }
  }

  public static BlockChain load(String path) {
    BlockChain blockChain = null;
    try (FileInputStream fis = new FileInputStream(
        path); ObjectInputStream ois = new ObjectInputStream(fis)) {
      blockChain = (BlockChain) ois.readObject();
    } catch (IOException | ClassNotFoundException e) {
      System.out.println("failed to read, error: " + e.getMessage());
    }
    return blockChain;
  }
}
