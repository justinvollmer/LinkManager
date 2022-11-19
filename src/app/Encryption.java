package app;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.util.Base64;

public class Encryption {
    String algorithm; // ALGORITHMS: AES,
    String key256bit;
    Cipher cipher;
    Key aesKey;
    // TODO: Implement Encryption
    public Encryption(String key256bit, String algorithm) {
        try {
            this.algorithm = algorithm;
            this.key256bit = key256bit;
            this.aesKey = new SecretKeySpec(key256bit.getBytes(), algorithm);
            this.cipher = Cipher.getInstance(algorithm);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * NON-STATIC METHODS
     */

    public String getKey() {
        return this.key256bit;
    }

    public void setKey(String key256bit) {
        this.key256bit = key256bit;
    }

    public String encrypt(String rawText) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        // Encryption
        this.cipher.init(Cipher.ENCRYPT_MODE, this.aesKey);
        byte[] encrypted = this.cipher.doFinal(rawText.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public String decrypt(String encryptedText) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        // Decryption
        this.cipher.init(Cipher.DECRYPT_MODE, this.aesKey);
        byte[] decrypted = this.cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decrypted);
    }

    /**
     * STATIC METHODS
     */

    public static void printAlgorithmList() {
        String[] algorithmList = {"AES", "Test", "Test2"};
        System.out.print("List of algorithms:\n     -->     ");
        for (String algorithm : algorithmList) {
            System.out.print(algorithm + "     ");
        }
    }
}