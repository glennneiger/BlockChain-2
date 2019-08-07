package blockchain.utils;

import blockchain.Message;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.ArrayList;

public class MessageUtil {

  static private final String type = "SHA256withRSA";

  public static boolean verifySignature(byte[] data, byte[] signature, PublicKey publicKey)
      throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
    var sig = Signature.getInstance(type);
    sig.initVerify(publicKey);
    sig.update(data);
    return sig.verify(signature);
  }

  public static byte[] sign(String data, PrivateKey privateKey)
      throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
    var sig = Signature.getInstance(type);
    sig.initSign(privateKey);
    sig.update(data.getBytes());
    return sig.sign();
  }

  public static String joinMessages(ArrayList<Message> messages) {
    if (messages.size() == 0) return "No message";
    var messageBodies = new ArrayList<String>();
    messages.forEach(
        (m) -> messageBodies.add(m.getMessage())
    );
    return String.join("\n", messageBodies);
  }
}
