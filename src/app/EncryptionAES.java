package app;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
public class EncryptionAES {
    String key256bit;
    Cipher cipher;
    Key aesKey;
    // TODO: Implement Encryption
    public EncryptionAES(String key256bit) {
        try {
            this.key256bit = key256bit;
            this.aesKey = new SecretKeySpec(key256bit.getBytes(), "AES");
            this.cipher = Cipher.getInstance("AES");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public String getKey() {
        return this.key256bit;
    }

    public void setKey(String key256bit) {
        this.key256bit = key256bit;
    }

    public String encrypt(String rawText) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        // Encryption
        cipher.init(Cipher.ENCRYPT_MODE, aesKey);
        byte[] encrypted = cipher.doFinal(rawText.getBytes());
        System.out.println(new String(encrypted)); // TODO: remove this
        return new String(encrypted);
    }

    public void decrypt(String encryptedText) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        // Decryption
        cipher.init(Cipher.DECRYPT_MODE, aesKey);
        String decrypted = new String(cipher.doFinal(Base64.getDecoder().decode(encryptedText)));
        System.out.println(decrypted); // TODO: remove this
    }

    public static void main(String[] args) {
        String input = "Hallo du liebe Welt #23 34!";
        String key = "&E)H@McQfThWmZq4t7w!z%C*F-JaNdRg";
        try {
            EncryptionAES aes = new EncryptionAES("&E)H@McQfThWmZq4t7w!z%C*F-JaNdRg");
            String encrypted = aes.encrypt(input);
            aes.decrypt(encrypted);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}