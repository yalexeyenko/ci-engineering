package action;

import dao.DaoException;
import entity.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ProjectService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ManagerContentProjectsAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(ManagerContentProjectsAction.class);

    private ActionResult managerContentProjectsPage = new ActionResult("manager-main");

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        log.debug("execute...");
        List<Project> projects;
        ProjectService projectService = new ProjectService();

        try {
            projects = projectService.findAllProjects();
        } catch (DaoException e) {
            throw new ActionException("Failed to get all projects", e);
        }

        try {
            projectService.close();
        } catch (Exception e) {
            throw new ActionException("Failed to close service", e);
        }

        req.setAttribute("" +
                "projects",  projects);
        return managerContentProjectsPage;
    }
}
