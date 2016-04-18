package entity;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

public class Project extends NamedEntity {
    private Client client;
    private LocalDate startDate;
    private LocalDate deadline;
    private boolean finished;
    private FileDoc techTask;
    private List<Module> modules;
    private User senior;

    public Project() {
        finished = false;
        modules = new ArrayList<>();
        startDate = new LocalDate();
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        if (client == null) {
            throw new NullPointerException("Attempt to set null to client");
        }
        this.client = client;
    }

    public LocalDate getStartDate() {
        return startDate;
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

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public FileDoc getTechTask() {
        return techTask;
    }

    public void setTechTask(FileDoc techTask) {
        if (techTask == null) {
            throw new NullPointerException("Attempt to set null to techTask");
        }
        this.techTask = techTask;
    }

    public List<Module> getModules() {
        return modules;
    }

    public boolean addModule(Module module) {
        return this.modules.add(module);
    }

    public boolean removeModule(Module module) {
        return this.modules.remove(module);
    }

    public void clearModules() {
        this.modules.clear();
    }

    public User getSenior() {
        return senior;
    }

    public void setSenior(User senior) {
        if (senior == null) {
            throw new NullPointerException("Attempt to set null to SENIOR");
        }
        this.senior = senior;
    }

    @Override
    public String toString() {
        return "Project{" +
                "name='" + getName() + '\'' +
                ", startDate=" + startDate +
                ", deadline=" + deadline +
                '}';
    }
}
