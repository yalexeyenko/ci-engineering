package action;

import entity.Project;
import entity.User;
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

        try (ProjectService projectService = new ProjectService()) {
            log.debug("findAllPersonalProjects()");
            projects = projectService.findAllPersonalProjects(((User) req.getSession().getAttribute("user")).getId());
        } catch (Exception e) {
            log.debug("Failed to findAllPersonalProjects()");
            throw new ActionException("Failed to close findAllPersonalProjects", e);
        }

        req.setAttribute("projects",  projects);
        return managerContentProjectsPage;
    }
}
