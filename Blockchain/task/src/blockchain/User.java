package blockchain;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

public class User implements Runnable {

  private BlockChain blockChain;
  private Message msg;

  public User(BlockChain blockChain, String msg) {
    try {
      this.blockChain = blockChain;
      KeyGenerator keyGen = new KeyGenerator(1024);
      var id = this.blockChain.getMessageId();
      this.msg = new Message(
          msg, id, MessageHelper.sign(id.toString() + msg, keyGen.getPrivateKey()),
          keyGen.getPublicKey()
      );
    } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void run() {
    this.blockChain.addMessage(this.msg);
  }
}
