package dao;

import entity.Project;

public interface ProjectDao extends Dao<Project> {
    void updateProjectClient(Project projectWithClient) throws DaoException;
}
