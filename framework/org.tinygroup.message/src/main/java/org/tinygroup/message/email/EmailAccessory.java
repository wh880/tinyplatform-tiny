package org.tinygroup.message.email;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by luoguo on 2014/4/18.
 */
public class EmailAccessory {
    public EmailAccessory() {

    }

    public EmailAccessory(File file) throws IOException {
        this.fileName = file.getName();
        FileInputStream inputStream = new FileInputStream(file);
        content = new byte[(int) file.length()];
        inputStream.read(content);
        inputStream.close();
    }

    public EmailAccessory(String fileName, byte[] content) {
        setFileName(fileName);
        setContent(content);
    }

    String fileName;
    byte[] content;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
