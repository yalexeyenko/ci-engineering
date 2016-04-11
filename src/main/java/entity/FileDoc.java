package entity;

import org.joda.time.LocalDate;

import java.io.FileInputStream;


public class FileDoc extends NamedEntity {
    private FileInputStream fileDocOrigin;
    private FileInputStream fileDocPDF;
    private String description;
    private User author;
    private LocalDate lastModified;
    private Status status;

    public FileDoc() {
        lastModified = new LocalDate();
    }

    public FileInputStream getFileDocOrigin() {
        return fileDocOrigin;
    }

    public void setFileDocOrigin(FileInputStream fileDocOrigin) {
        if (fileDocOrigin == null) {
            throw new NullPointerException("Attempt to set null to fileDocOrigin");
        }
        this.fileDocOrigin = fileDocOrigin;
    }

    public FileInputStream getFileDocPDF() {
        return fileDocPDF;
    }

    public void setFileDocPDF(FileInputStream fileDocPDF) {
        if (fileDocPDF == null) {
            throw new NullPointerException("Attempt to set null to fileDocPDF");
        }
        this.fileDocPDF = fileDocPDF;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null) {
            throw new NullPointerException("Attempt to set null to description");
        }
        this.description = description;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        if (author == null) {
            throw new NullPointerException("Attempt to set null to author");
        }
        this.author = author;
    }

    public LocalDate getLastModified() {
        return lastModified;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "FileDoc{" +
                "name='" + getName() + '\'' +
                ", lastModified=" + lastModified +
                '}';
    }

    private enum Status {
        check,
        approved,
        rework
    }
}
