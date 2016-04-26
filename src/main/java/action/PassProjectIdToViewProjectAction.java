package action;

import dao.DaoException;
import entity.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ProjectService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PassProjectIdToViewProjectAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(PassProjectIdToViewProjectAction.class);

    private ActionResult viewProject = new ActionResult("view-project");

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        String projectId = req.getParameter("projectId");

        ProjectService projectService = new ProjectService();
        Project project = null;
        try {
            project = projectService.findProjectById(Integer.valueOf(projectId));
        } catch (DaoException e) {
            try {
                projectService.close();
            } catch (Exception ex) {
                throw new ActionException("Failed to close service", ex);
            }
        }
        try {
            projectService.close();
        } catch (Exception ex) {
            throw new ActionException("Failed to close service", ex);
        }

        req.setAttribute("project", project);
        return viewProject;
    }
}
