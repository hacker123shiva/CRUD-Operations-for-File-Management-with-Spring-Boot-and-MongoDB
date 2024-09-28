package com.telusko.dao;




import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.telusko.entity.FileEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends MongoRepository<FileEntity, String> {

    List<FileEntity> findByWriterName(String writerName);
    
    Optional<FileEntity> findByFileName(String fileName); // To ensure the file name is unique
}