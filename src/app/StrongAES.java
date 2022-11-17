package app;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
public class StrongAES
{
    public void run()
    {
        try
        { // TODO: Implement Encryption
            String text = "PUT TEXT HERE";
            String key = "PUT KEY HERE"; // 128 bit key
            // Create key and cipher
            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            // encrypt the text
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] encrypted = cipher.doFinal(text.getBytes());
            System.err.println(new String(encrypted));
            // decrypt the text
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            String decrypted = new String(cipher.doFinal(encrypted));
            System.err.println(decrypted);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}