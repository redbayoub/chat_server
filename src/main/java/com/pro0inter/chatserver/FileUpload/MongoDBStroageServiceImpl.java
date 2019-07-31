package com.pro0inter.chatserver.FileUpload;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.gridfs.GridFSDBFile;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsCriteria;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Primary
@Service
public class MongoDBStroageServiceImpl implements StorageService {
    @Autowired
    GridFsOperations gridFsOperations;
    @Autowired
    GridFSBucket gridFSBucket;
    @Override
    public void init() {
        // nothing
    }

    @Override
    public String store(MultipartFile file) {
        try {
            ObjectId id=gridFsOperations.store(file.getInputStream(), file.getOriginalFilename(),file.getContentType());
            return id.toHexString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<FileInfo> list() {
        List<FileInfo> infos=new ArrayList<>();
        gridFsOperations.find(new Query()).forEach(new Consumer<GridFSFile>() {
            @Override
            public void accept(GridFSFile gridFSFile) {
                infos.add(new FileInfo(gridFSFile.getObjectId().toHexString(),gridFSFile.getFilename(),gridFSFile.getLength()));
            }
        });
        return infos;
    }

    @Override
    public Resource load(String id) {
        GridFSFile gridFSFile= gridFsOperations.findOne(whereId(id));
        GridFsResource resource = getGridFsResource(gridFSFile);
        return resource;
    }

    private GridFsResource getGridFsResource(GridFSFile gridFSFile) {
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        return new GridFsResource(gridFSFile,gridFSDownloadStream);
    }

    private Query whereId(String id) {
        return new Query(Criteria.where("_id").is(new ObjectId(id)));
    }

    @Override
    public synchronized String replace_file(String old_fileID, MultipartFile file) {
        delete(old_fileID);
        return store(file);
    }

    @Override
    public int delete(String id) {
        gridFsOperations.delete(whereId(id));
        return 1;

    }

    @Override
    public int deleteAll() {
        List<GridFSFile> fileList = new ArrayList<GridFSFile>();
        GridFSFindIterable files=gridFsOperations.find(new Query());
        files.into(fileList);
        int fileNB=fileList.size();
        gridFsOperations.delete(new Query());
        return fileNB;
    }
}
