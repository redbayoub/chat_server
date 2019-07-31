package com.pro0inter.chatserver.FileUpload;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public interface StorageService {
    void init();

    String store(MultipartFile file);

    List<FileInfo> list();

    Resource load(String id);

    String replace_file(String old_fileID, MultipartFile file);

    int delete(String id);

    int deleteAll();

}
