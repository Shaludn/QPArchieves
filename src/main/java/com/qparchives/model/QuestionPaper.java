package com.qparchives.model;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "question_papers")
public class QuestionPaper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String qpName;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String university;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String filePath;

    @Column(nullable = false)
    private LocalDate uploadDate;

    public QuestionPaper() {
    }

    public Long getId() {
        return id;
    }

    public String getQpName() {
        return qpName;
    }

    public void setQpName(String qpName) {
        this.qpName = qpName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public LocalDate getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDate uploadDate) {
        this.uploadDate = uploadDate;
    }
}