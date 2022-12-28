package app;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Encryption {
    String algorithm;
    String key256bit;
    Cipher cipher;
    Key secretKey;
    
    public Encryption(String key256bit, String algorithm) {
        try {
            this.algorithm = algorithm;
            this.key256bit = key256bit;
            this.secretKey = new SecretKeySpec(key256bit.getBytes(), algorithm);
            this.cipher = Cipher.getInstance(algorithm);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * NON-STATIC METHODS
     */

    public Key getKey() {
        return this.secretKey;
    }

    public void setKey(String key256bit) {
        this.secretKey = new SecretKeySpec(key256bit.getBytes(), this.algorithm);
    }

    public String encrypt(String inputText) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        // Encryption
        this.cipher.init(Cipher.ENCRYPT_MODE, this.secretKey);
        byte[] encrypted = this.cipher.doFinal(inputText.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public String decrypt(String encryptedText) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        // Decryption
        this.cipher.init(Cipher.DECRYPT_MODE, this.secretKey);
        byte[] decrypted = this.cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decrypted);
    }

    /**
     * STATIC METHODS
     */

    public static String encrypt(String inputText, String key256bit, String algorithm) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        // Encryption
        Key secretKey = new SecretKeySpec(key256bit.getBytes(), algorithm);
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encrypted = cipher.doFinal(inputText.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static String decrypt(String encryptedText, String key256bit, String algorithm) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        // Decryption
        Key secretKey = new SecretKeySpec(key256bit.getBytes(), algorithm);
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] encrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(encrypted);
    }

    public static void printAlgorithmList() {
        String[] algorithmList = {"AES"};
        System.out.print("List of algorithms:\n     -->     ");
        for (String algorithm : algorithmList) {
            System.out.print(algorithm + "     ");
        }
    }
}