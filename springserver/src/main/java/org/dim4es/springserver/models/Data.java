package org.dim4es.springserver.models;

import javax.persistence.*;

@Entity
@Table(name = "data")
public class Data {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_type")
    private String fileType;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person dataOwner;

    public Data() {
    }

    public Data(String filePath, String fileType) {
        this.filePath = filePath;
        this.fileType = fileType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Person getDataOwner() {
        return dataOwner;
    }

    public void setDataOwner(Person dataOwner) {
        this.dataOwner = dataOwner;
    }
}
