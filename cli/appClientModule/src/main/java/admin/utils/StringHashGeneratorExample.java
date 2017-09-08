package src.main.java.admin.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StringHashGeneratorExample {

  public static void main(String[] args) {
    try {

      System.out.println(digest("SHA1", "entity.UcsawsPersona@5ecaeee0entity.UcsawsEvento@5d08e4be"));
      System.out.println(digest("SHA-256", "entity.UcsawsPersona@5ecaeee0entity.UcsawsEvento@5d08e4be"));

    } catch (Exception e) {
      System.out.println(e);
    }
  }

  private static String encodeHex(byte[] digest) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < digest.length; i++) {
      sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
    }
    return sb.toString();
  }

  public static String digest(String alg, String input) {
    try {
      MessageDigest md = MessageDigest.getInstance(alg);
      byte[] buffer = input.getBytes("UTF-8");
      md.update(buffer);
      byte[] digest = md.digest();
      return encodeHex(digest);
    } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
      e.printStackTrace();
      return e.getMessage();
    }
  }

}
