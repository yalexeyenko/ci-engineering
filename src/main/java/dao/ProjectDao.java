package dao;

import entity.Project;

import java.util.List;

public interface ProjectDao extends Dao<Project> {
    void updateProjectClient(Project projectWithClient) throws DaoException;
    void updateProjectSenior(Project projectWithSenior) throws DaoException;
    List<Project> findAllPersonalProjects(int managerId) throws DaoException;
}
