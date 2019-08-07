package blockchain;

import java.security.PublicKey;

public class Message {
  private final String message;
  private final String id;
  private final byte[] signature;
  private final PublicKey publicKey;

  public Message(String message, Integer id, byte[] signature, PublicKey publicKey) {
    this.message = message;
    this.id = id.toString();
    this.signature = signature;
    this.publicKey = publicKey;
  }

  public String getMessage() {
    return this.message;
  }

  public String getId() {
    return this.id;
  }

  public byte[] getSignature() {
    return this.signature;
  }

  public PublicKey getPublicKey() {
    return this.publicKey;
  }
}
