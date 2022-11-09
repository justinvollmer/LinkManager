package app;

import java.io.*;
import java.net.URL;

public class DownloadManager {
    private String path;

    public DownloadManager (String path) {
        this.path = path;
    }

    public static void downloadImage(String linkToImage, String path, String filename, String filetype) throws IOException {
        URL url = new URL(linkToImage);
        InputStream in = new BufferedInputStream(url.openStream());
        OutputStream out = new BufferedOutputStream(new FileOutputStream(path + filename + filetype));

        for ( int i; (i = in.read()) != -1; ) {
            out.write(i);
        }
        in.close();
        out.close();
    }
}