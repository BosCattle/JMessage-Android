package tech.jiangtao.support.kit.util;

import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Class: EncryptUtils </br>
 * Description: 加密工具类 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 16/12/2016 11:55 PM</br>
 * Update: 16/12/2016 11:55 PM </br>
 **/

public class EncryptUtils {

  public static final String send = "power_chat";

  /**
   * 加密文件
   * @param seed 种子
   * @param clearbyte 二进制数据
   * @return
   * @throws Exception
   */
  public static byte[] encryptVoice(String seed, byte[] clearbyte)
      throws Exception {
    byte[] rawKey = getRawKey(seed.getBytes());
    byte[] result = encrypt(rawKey, clearbyte);
    return result;
  }

  /**
   * 解密
   * @param seed 种子
   * @param encrypted 二进制数据
   * @return
   * @throws Exception
   */
  public static byte[] decryptVoice(String seed, byte[] encrypted)
      throws Exception {
    byte[] rawKey = getRawKey(seed.getBytes());
    byte[] result = decrypt(rawKey, encrypted);
    return result;
  }

  private static byte[] getRawKey(byte[] seed) throws Exception {
    KeyGenerator kgen = KeyGenerator.getInstance("AES");
    SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "Crypto");
    sr.setSeed(seed);
    kgen.init(128, sr);
    SecretKey skey = kgen.generateKey();
    byte[] raw = skey.getEncoded();
    return raw;
  }

  private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
    SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
    Cipher cipher = Cipher.getInstance("AES");
    cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(
        new byte[cipher.getBlockSize()]));
    byte[] encrypted = cipher.doFinal(clear);
    return encrypted;
  }

  private static byte[] decrypt(byte[] raw, byte[] encrypted)
      throws Exception {
    SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
    Cipher cipher = Cipher.getInstance("AES");
    cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(
        new byte[cipher.getBlockSize()]));
    byte[] decrypted = cipher.doFinal(encrypted);
    return decrypted;
  }
}
