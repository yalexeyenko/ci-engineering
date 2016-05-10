package entity;

import org.joda.time.LocalDate;

import java.io.FileInputStream;
import java.io.InputStream;


public class FileDoc extends NamedEntity {
    private String description;
    private LocalDate lastModified;
    private Status status;
    private Project project;
    private User user;
    private Module module;
    InputStream fileContent;

    public FileDoc() {
        lastModified = new LocalDate();
        status = Status.DEFAULT;
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

    public LocalDate getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDate lastModified) {
        this.lastModified = lastModified;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public InputStream getFileContent() {
        return fileContent;
    }

    public void setFileContent(InputStream fileContent) {
        this.fileContent = fileContent;
    }

    @Override
    public String toString() {
        return "FileDoc{" +
                "name='" + getName() + '\'' +
                ", lastModified=" + lastModified +
                '}';
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public enum Status {
        ONCHECK,
        APPROVED,
        REWORK,
        DEFAULT
    }
}
