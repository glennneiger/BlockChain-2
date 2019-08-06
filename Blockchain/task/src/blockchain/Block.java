package blockchain;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

public class Block implements Serializable {

  private Integer id;
  private Long creationTimestamp;
  private String prevHash;
  private String hash;
  private Long computeTime;
  private Integer magicNumber;
  private String minerId;
  private String numZeroChanges;

  public Block(int blockId, String minerId, String prevHash, int numZeros) {
    this.creationTimestamp = new Date().getTime();
    this.id = blockId;
    this.minerId = minerId;
    this.prevHash = prevHash;
    this.calcHash(numZeros);
  }

  private void calcHash(int numZeros) {
    do {
      this.magicNumber = new Random().nextInt() & Integer.MAX_VALUE;
      this.hash = StringUtil.applySha256(
          this.prevHash +
              this.id.toString() +
              this.creationTimestamp.toString() +
              this.magicNumber.toString());
    } while (!this.hash.startsWith("0".repeat(numZeros)));
    this.computeTime = (new Date().getTime() - this.creationTimestamp) / 1000;
  }

  public String getHash() {
    return this.hash;
  }

  public String getPrevHash() {
    return this.prevHash;
  }

  public Long getComputeTime() {
    return this.computeTime;
  }

  public synchronized void setNumZeroChanges(String change) {
    this.numZeroChanges = change;
  }

  @Override
  public String toString() {
    return String.format(
        "Block:\n"
            + "Created by miner # %s\n"
            + "Id: %s\n"
            + "Timestamp: %s\n"
            + "Magic number: %s\n"
            + "Hash of the previous block:\n%s\n"
            + "Hash of the block:\n%s\n"
            + "Block was generating for %s seconds\n"
            + "%s\n",
        this.minerId,
        this.id,
        this.creationTimestamp.toString(),
        this.magicNumber,
        this.prevHash,
        this.hash,
        this.computeTime,
        this.numZeroChanges
    );
  }
}
