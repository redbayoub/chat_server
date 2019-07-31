package com.pro0inter.chatserver.FileUpload;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.nio.file.Path;

@JsonComponent
class FileInfo implements Serializable {
    String id;
    String filename;
    long filesize;

    public FileInfo(Path path) {
        this.filename = path.getFileName().toString();
        this.filesize=path.toFile().length();
    }

    public FileInfo(String id, String filename, long filesize) {
        this.id = id;
        this.filename = filename;
        this.filesize = filesize;
    }

    public FileInfo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public long getFilesize() {
        return filesize;
    }

    public void setFilesize(long filesize) {
        this.filesize = filesize;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "filename='" + filename + '\'' +
                ", filesize=" + filesize +
                '}';
    }
}
