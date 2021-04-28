public enum FileNames {

    SETTINGS("settings.txt"), FILE_LOG("file.log");

    private String filename;

    FileNames(String filename) {
        this.filename = filename;
    }

    public String toString() {
        return filename;
    }
}
