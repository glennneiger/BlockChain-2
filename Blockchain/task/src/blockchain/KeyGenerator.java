package blockchain;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class KeyGenerator {

  private KeyPair pair;

  public KeyGenerator(int keyLength) throws NoSuchAlgorithmException {
    var keyGen = KeyPairGenerator.getInstance("RSA");
    keyGen.initialize(keyLength);
    this.pair = keyGen.generateKeyPair();
  }

  public PrivateKey getPrivateKey() {
    return this.pair.getPrivate();
  }

  public PublicKey getPublicKey() {
    return this.pair.getPublic();
  }
}
