package org.dim4es.springserver.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "data")
public class Data extends AbstractEntity {

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_type")
    private String fileType;

    public Data() {
    }

    public Data(String filePath, String fileType) {
        this.filePath = filePath;
        this.fileType = fileType;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
