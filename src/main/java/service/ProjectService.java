package service;

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

    public Project createNewProject(Project project) {
        log.debug("createNewProject()...");
        log.debug("project is null: {}", project == null);
        log.debug("projectDao is null: {}", projectDao == null);
        return projectDao.insert(project);
    }

    public Project findProjectById(int id) {
        log.debug("findProjectById()...");
        return projectDao.findById(id);
    }

    public List<Project> findAllProjects() {
        return projectDao.findAll();
    }

    public List<Project> findAllPersonalProjects(int managerId) {
        return projectDao.findAllPersonalProjects(managerId);
    }

    public void updateProject(Project project) {
        projectDao.update(project);
    }

    public void updateProjectClient(Project project) {
        projectDao.updateProjectClient(project);
    }

    public void updateProjectSenior(Project project) {
        projectDao.updateProjectSenior(project);
    }

    public boolean deleteProjectById(int id) {
        return projectDao.delete(id);
    }

    @Override
    public void close() throws Exception {
        if (jdbcDaoFactory != null) {
            jdbcDaoFactory.close();
        }
    }

}
