package app;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DownloadManager {
    private String path;
    private String enforcedNamingSystem;
    private List<String> filetypeList;
    public static final char[] illegalChars = {'<', '>', '/', '\\', '|', '*', ':', '"'};

    public DownloadManager(String path, String enforcedNamingSystem) {
        this.path = path;
        this.enforcedNamingSystem = enforcedNamingSystem;
        filetypeList = DownloadManager.getSupportedFiletypes();
    }

    public static ArrayList<String> getSupportedFiletypes() {
        ArrayList<String> supportedFiletypes = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/app/supportedFiletypes.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("//")) { // allows comments in the supportedFileTypes.txt
                    supportedFiletypes.add(line);
                }
            }
            br.close();
        } catch (IOException io) {
            System.err.println("IOException due to the filetype: " + io.getMessage());
        }
        return supportedFiletypes;
    }

    public void download(String linkToMedia, int imageNumber) throws IOException {
        String filetype = null;
        for (String filetypeFromList : filetypeList) {
            if (linkToMedia.contains(filetypeFromList) || filetypeFromList.equals(filetypeList.get(filetypeList.size() - 1))) {
                if (filetypeFromList.equals("webp") || (filetypeFromList.equals(filetypeList.get(filetypeList.size() - 1)) && !filetypeFromList.equals(filetypeList.get(filetypeList.size() - 1)))) {
                    filetype = "jpg";
                    break;
                } else {
                    filetype = filetypeFromList;
                    break;
                }
            }
        }

        URL url = new URL(linkToMedia);
        InputStream in = new BufferedInputStream(url.openStream());
        OutputStream out = new BufferedOutputStream(new FileOutputStream(path + this.enforcedNamingSystem + imageNumber + "." + filetype));

        for (int i; (i = in.read()) != -1; ) {
            out.write(i);
        }
        in.close();
        out.close();
    }

    public static void download(String linkToMedia, String path, String filename, String filetype) throws IOException {
        URL url = new URL(linkToMedia);
        InputStream in = new BufferedInputStream(url.openStream());
        OutputStream out = new BufferedOutputStream(new FileOutputStream(path + filename + "." + filetype));

        for (int i; (i = in.read()) != -1; ) {
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