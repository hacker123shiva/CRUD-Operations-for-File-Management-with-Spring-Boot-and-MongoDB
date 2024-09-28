package com.telusko.entity;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "files")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileEntity {

    @Id
    private String id;
    private String fileName;
    private String writerName;
    private byte[] fileData;

    public FileEntity(String fileName, String writerName, byte[] fileData) {
        this.fileName = fileName;
        this.writerName = writerName;
        this.fileData = fileData;
    }
}
