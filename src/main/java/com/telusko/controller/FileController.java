package com.telusko.controller;


 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.telusko.entity.FileEntity;
import com.telusko.service.FileService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public FileEntity uploadFile(@RequestParam("fileName") String fileName,
                                 @RequestParam("writerName") String writerName,
                                 @RequestParam("file") MultipartFile file) throws IOException {
        return fileService.uploadFile(fileName, writerName, file);
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String id) {
        FileEntity fileEntity = fileService.getFileById(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileEntity.getFileName() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(fileEntity.getFileData());
    }

    // Get all files by writer name
    @GetMapping("/writer/{writerName}")
    public List<FileEntity> getFilesByWriterName(@PathVariable String writerName) {
        return fileService.getFilesByWriterName(writerName);
    }

    // Get file by file name
    @GetMapping("/name/{fileName}")
    public FileEntity getFileByFileName(@PathVariable String fileName) {
        return fileService.getFileByFileName(fileName);
    }

    @PutMapping("/{id}")
    public FileEntity updateFile(@PathVariable String id,
                                 @RequestParam("fileName") String fileName,
                                 @RequestParam("writerName") String writerName,
                                 @RequestParam("file") MultipartFile file) throws IOException {
        return fileService.updateFile(id, fileName, writerName, file);
    }

    @DeleteMapping("/{id}")
    public void deleteFile(@PathVariable String id) {
        fileService.deleteFile(id);
    }
}