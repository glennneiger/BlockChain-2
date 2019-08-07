package blockchain;

import blockchain.utils.MessageUtil;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class BlockChain implements Serializable {

  private static Long LB = 1L; // one sec.
  private static Long UB = LB * 10L; // ten secs.
  private static Integer messageId = 1;

  private ArrayList<Block> blocks = new ArrayList<>();
  private Integer numZeros = 0;
  private ArrayList<Message> messages = new ArrayList<>();

  private boolean validBlock(Block newBlock) {
    return this.blocks.size() == 0 ||
        (
            newBlock.getPrevHash().equals(this.blocks.get(this.blocks.size() - 1).getHash()) &&
                newBlock.getHash().startsWith("0".repeat(this.numZeros)) &&
                newBlock.getMessage().stream().allMatch(
                    msg -> {
                      try {
                        return MessageUtil.verifySignature(
                            (msg.getId() + msg.getMessage()).getBytes(), msg.getSignature(),
                            msg.getPublicKey());
                      } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
                        e.printStackTrace();
                      }
                      return false; // Shouldn't hit.
                    }
                )
        );
  }

  public synchronized void offer(Block newBlock) {
    if (validBlock(newBlock)) {
      this.blocks.add(newBlock);
      this.messages = new ArrayList<>(
          this.messages.subList(newBlock.getMessageSize(), this.messages.size()));
      messageId++;
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

  public synchronized void addMessage(Message msg) {
    this.messages.add(msg);
  }

  public synchronized ArrayList<Message> getMessage() {
    return new ArrayList<>(this.messages);
  }

  public synchronized Integer getMessageId() {
    return messageId;
  }

  @Override
  public String toString() {
    return this.blocks.subList(0, 5).stream().map(Block::toString)
        .collect(Collectors.joining("\n"));
  }
}
