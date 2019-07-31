package com.pro0inter.chatserver.FileUpload;

import org.apache.tomcat.util.http.fileupload.util.Streams;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Service
public class SimpleStroageServiceImpl implements StorageService {
    private static File uploaded_fld;
    @Override
    public void init() {

        uploaded_fld=new File(getClass().getClassLoader().getResource("").getFile()+"uploaded_fld");
        uploaded_fld.mkdir();

    }

    @Override
    public String store(MultipartFile file) {
        try {
            file.transferTo(new File(uploaded_fld, file.getOriginalFilename()));
            return file.getOriginalFilename();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public  List<FileInfo> list() {
        List<FileInfo> list=new ArrayList<>();
        for(File f:uploaded_fld.listFiles() ) {
            list.add(new FileInfo(f.getName(),f.getName(),f.length()));
        }
        return list;
    }

    @Override
    public Resource load(String filename) {
        for (File f:uploaded_fld.listFiles() ) {
            if(f.getName().equals(filename)){
                return new FileSystemResource(f);
            }
        }
        return null;
    }

    @Override
    public synchronized String replace_file(String old_filename, MultipartFile file) {
        delete(old_filename);
        return store(file);
    }


    @Override
    public int delete(String filename) {
        List<File> to_del=new ArrayList<>();
        for (File f:uploaded_fld.listFiles() ) {
            if(f.getName().equals(filename)){
                to_del.add(f);
            }
        }
        if(to_del.size()==1){
            to_del.get(0).delete();
            return 1;
        }
        return 0;
    }

    @Override
    public int deleteAll() {
        int files_count=uploaded_fld.listFiles().length;
        for (File f:uploaded_fld.listFiles() ) {
            f.delete();
        }
        return files_count;
    }
}
