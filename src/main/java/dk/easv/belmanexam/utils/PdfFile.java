package dk.easv.belmanexam.utils;

import java.io.File;

public class PdfFile {
    private byte[] byteData;
    private File file;

    public void setFile(File file) {
        this.file = file;
    }
    public byte[] getByteData() {
        return byteData;
    }
    public void setByteData(byte[] byteData) {
        this.byteData = byteData;
    }
    public File getFile() {
        return file;
    }
}
