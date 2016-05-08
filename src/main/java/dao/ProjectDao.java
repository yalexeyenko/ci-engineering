package dao;

import entity.Project;

import java.util.List;

public interface ProjectDao extends Dao<Project> {
    void updateProjectClient(Project projectWithClient);
    void updateProjectSenior(Project projectWithSenior);
    List<Project> findAllPersonalProjects(int managerId);
}
