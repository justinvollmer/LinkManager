package config;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Config {
    public static Properties prop = new Properties();
    // public static String path = System.getProperty("user.home") + "\\AppData\\Roaming\\MassLinkOpener\\config\\config.properties"; // is used when installed via installer
    public static String path = "src/config/config.properties"; // is used when pulled from repo

    public static String getProperties(String property) throws Exception {
        property = property.toLowerCase();
        try (InputStream input = new FileInputStream(path)) {
            prop.load(input);
            return prop.getProperty(property);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public static void setProperties(String key, String value) throws Exception {
        key = key.toLowerCase();
        try (OutputStream output = new FileOutputStream(path)) {
            prop.setProperty(key, value);
            prop.store(output, null);
        } catch (Exception e) {
            throw new Exception();
        }
    }
}
