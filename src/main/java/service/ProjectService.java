package service;

import dao.DaoException;
import dao.DaoFactory;
import dao.ProjectDao;
import entity.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ProjectService implements AutoCloseable {
    private static final Logger log = LoggerFactory.getLogger(ProjectService.class);

    private DaoFactory jdbcDaoFactory;
    private ProjectDao projectDao;

    public ProjectService() {
        jdbcDaoFactory = DaoFactory.newInstance(DaoFactory.JDBC);
        projectDao = (ProjectDao) jdbcDaoFactory.createDao(Project.class);
    }

    public Project createNewProject(Project project) throws DaoException {
        log.debug("createNewProject()...");
        log.debug("project is null: {}", project == null);
        log.debug("projectDao is null: {}", projectDao == null);
        return projectDao.insert(project);
    }

    public Project findProjectById(int id) throws DaoException {
        log.debug("findProjectById()...");
        return projectDao.findById(id);
    }

    public List<Project> findAllProjects() throws DaoException {
        return projectDao.findAll();
    }

    public List<Project> findAllProjectsLimited(int start, int count) throws DaoException {
        return projectDao.findAll(start, count);
    }

    public void updateProject(Project project) throws DaoException {
        projectDao.update(project);
    }

    public void updateProjectClient(Project project) throws DaoException {
        projectDao.updateProjectClient(project);
    }

    public void updateProjectSenior(Project project) throws DaoException {
        projectDao.updateProjectSenior(project);
    }

    public boolean deleteProjectById(int id) throws DaoException {
        return projectDao.delete(id);
    }

    @Override
    public void close() throws Exception {
        if (jdbcDaoFactory != null) {
            jdbcDaoFactory.close();
        }
    }

}
