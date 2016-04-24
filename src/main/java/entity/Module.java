package entity;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

public class Module extends NamedEntity {
    private LocalDate startDate;
    private LocalDate deadline;
    private boolean finished;
    private List<FileDoc> fileDocs;
    private List<User> specialists;

    public Module() {
        finished = false;
        fileDocs = new ArrayList<>();
        specialists = new ArrayList<>();
        startDate = new LocalDate();
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        if (deadline == null) {
            throw new NullPointerException("Attempt to set null to deadline");
        }
        this.deadline = deadline;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public boolean addFileDoc(FileDoc fileDoc) {
        return this.fileDocs.add(fileDoc);
    }

    public boolean removeFileDoc(FileDoc fileDoc) {
        return this.fileDocs.remove(fileDoc);
    }

    public void clearFileDocs() {
        this.fileDocs.clear();
    }

    public boolean addSpecialist(User specialist) {
        return this.specialists.add(specialist);
    }

    public boolean removeSpecialist(User specialist) {
        return this.specialists.remove(specialist);
    }

    public void clearSpecialists() {
        this.specialists.clear();
    }

    @Override
    public String toString() {
        return "Module{" +
                "name='" + getName() + '\'' +
                ", deadline=" + deadline +
                '}';
    }
}
