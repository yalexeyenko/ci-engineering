package action;

import dao.DaoException;
import entity.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ProjectService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PassProjectIdToEditMainProjectInfoAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(PassProjectIdToViewProjectAction.class);

    private ActionResult editMainProjectInfo = new ActionResult("edit-main-project-info");

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        String projectId = req.getParameter("projectId");

        Project project = null;
        ProjectService projectService = new ProjectService();
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
        return editMainProjectInfo;
    }
}
