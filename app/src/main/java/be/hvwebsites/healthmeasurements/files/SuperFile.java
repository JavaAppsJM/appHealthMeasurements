package be.hvwebsites.healthmeasurements.files;

public class SuperFile {
    String basedir;
    String fileName;

    public SuperFile(String basedir) {
        this.basedir = basedir;
    }

    public String getBasedir() {
        return basedir;
    }

    public void setBasedir(String basedir) {
        this.basedir = basedir;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
