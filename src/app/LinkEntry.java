package app;

public class LinkEntry {
    private int id;
    private String link;
    private String filename;
    private String progress;

    public LinkEntry(int id, String link, String filename, String progress) {
        this.id = id;
        this.link = link;
        this.filename = filename;
        this.progress = progress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getProgress() {
        return progress.toUpperCase();
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

}
