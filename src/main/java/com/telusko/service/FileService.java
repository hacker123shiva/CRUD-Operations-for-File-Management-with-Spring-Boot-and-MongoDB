package com.telusko.service;

 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.telusko.dao.FileRepository;
import com.telusko.entity.FileEntity;
import com.telusko.exception.DuplicateFileNameException;
import com.telusko.exception.FileNotFoundException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    // Upload file and ensure unique file name
    public FileEntity uploadFile(String fileName, String writerName, MultipartFile file) throws IOException {
        Optional<FileEntity> existingFile = fileRepository.findByFileName(fileName);
        if (existingFile.isPresent()) {
            throw new DuplicateFileNameException("A file with the name '" + fileName + "' already exists.");
        }

        FileEntity fileEntity = new FileEntity(fileName, writerName, file.getBytes());
        return fileRepository.save(fileEntity);
    }

    // Get file by ID
    public FileEntity getFileById(String id) {
        return fileRepository.findById(id).orElseThrow(() -> new FileNotFoundException("File not found with id " + id));
    }

    // Get all files by author (writer) name
    public List<FileEntity> getFilesByWriterName(String writerName) {
        return fileRepository.findByWriterName(writerName);
    }

    // Get file by file name
    public FileEntity getFileByFileName(String fileName) {
        return fileRepository.findByFileName(fileName).orElseThrow(() -> new FileNotFoundException("File not found with name " + fileName));
    }

    // Update file details
    public FileEntity updateFile(String id, String fileName, String writerName, MultipartFile file) throws IOException {
        FileEntity fileEntity = getFileById(id);

        // Check if the new file name is unique
        Optional<FileEntity> existingFile = fileRepository.findByFileName(fileName);
        if (existingFile.isPresent() && !existingFile.get().getId().equals(id)) {
            throw new DuplicateFileNameException("A file with the name '" + fileName + "' already exists.");
        }

        fileEntity.setFileName(fileName);
        fileEntity.setWriterName(writerName);
        fileEntity.setFileData(file.getBytes());
        return fileRepository.save(fileEntity);
    }

    // Delete file
    public void deleteFile(String id) {
        FileEntity fileEntity = getFileById(id);
        fileRepository.delete(fileEntity);
    }
}