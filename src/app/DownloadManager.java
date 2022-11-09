package app;

import java.io.*;
import java.net.URL;

public class DownloadManager {
    private String path;
    private String enforcedNamingSystem;

    public DownloadManager (String path, String enforcedNamingSystem) {
        this.path = path;
        this.enforcedNamingSystem = enforcedNamingSystem;
    }

    public void downloadMedia(String linkToMedia, int imageNumber) throws IOException {
        String filetype;
        if (linkToMedia.contains(".jpg")) {
            filetype = ".jpg";
        } else if (linkToMedia.contains(".png")) {
            filetype = ".png";
        } else if (linkToMedia.contains(".gif")) {
            filetype = ".gif";
        } else if (linkToMedia.contains(".mp4")) {
            filetype = ".mp4";
        } else if(linkToMedia.contains(".mp3")) {
            filetype = ".mp3";
        } else if(linkToMedia.contains(".webm")) {
            filetype = ".webm";
        } else if(linkToMedia.contains(".webp")) {
            filetype = ".jpg";
        } else {
            filetype = ".jpg";
        }
        URL url = new URL(linkToMedia);
        InputStream in = new BufferedInputStream(url.openStream());
        OutputStream out = new BufferedOutputStream(new FileOutputStream(path + this.enforcedNamingSystem + "_" + imageNumber + filetype));

        for ( int i; (i = in.read()) != -1; ) {
            out.write(i);
        }
        in.close();
        out.close();
    }

    public static void downloadMedia(String linkToMedia, String path, String filename, String filetype) throws IOException {
        URL url = new URL(linkToMedia);
        InputStream in = new BufferedInputStream(url.openStream());
        OutputStream out = new BufferedOutputStream(new FileOutputStream(path + filename + filetype));

        for ( int i; (i = in.read()) != -1; ) {
            out.write(i);
        }
        in.close();
        out.close();
    }

    public static void sleep(int seconds) {
        int milliseconds = seconds * 1000;
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}