package com.pro0inter.chatserver.FileUpload;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/upload")
public class FileUploadController {

    @Autowired
    private MongoDBStroageServiceImpl storageService;

    @GetMapping("/list/")
    public ResponseEntity<List> listUploadedFiles(){
        return ResponseEntity.ok().body(storageService.list());
    }

    @GetMapping(value = "/files/{id}",produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String id) throws IOException {
       Resource res = storageService.load(id);
       if(res!= null && res.exists()) {
           return ResponseEntity.ok()
                   .header(HttpHeaders.CONTENT_DISPOSITION,
                             res.getFilename()).body(res);

       }else
           return ResponseEntity.notFound().build();
    }

    @PutMapping(value = "/files/{old_id}/",produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    @ResponseBody
    public ResponseEntity replace_file(@PathVariable("old_id") String old_id,
                                       @RequestParam("file") MultipartFile file) {
        String new_id=storageService.replace_file(old_id, file);
        if(new_id!=null && !new_id.isEmpty())
            return ResponseEntity.accepted().body(new_id);
        else
            return ResponseEntity.badRequest().build();

    }

    @PostMapping("/")
    public ResponseEntity handleFileUpload(@RequestParam("file") MultipartFile file) {
        String id=storageService.store(file);

        if(id!=null && !id.isEmpty())
            return ResponseEntity.ok().body(id);
        else
            return ResponseEntity.badRequest().build();
    }


}
