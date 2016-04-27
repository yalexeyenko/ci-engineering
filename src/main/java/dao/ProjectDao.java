package dao;

import entity.Project;

public interface ProjectDao extends Dao<Project> {
    void updateProjectClient(Project projectWithClient) throws DaoException;
    void updateProjectSenior(Project projectWithSenior) throws DaoException;
}
