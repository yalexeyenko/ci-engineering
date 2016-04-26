package action;

import dao.DaoException;
import entity.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ProjectService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PassProjectIdAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(PassProjectIdAction.class);

    private ActionResult viewProject = new ActionResult("view-project");
    private ActionResult editMainProjectInfo = new ActionResult("edit-main-project-info");
    private ActionResult createClient = new ActionResult("create-client");

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        log.debug("execute...");
        String projectId = req.getParameter("projectId");
        String passParam = req.getParameter("passProjectId");
        log.debug("projectId: {}", projectId);
        log.debug("passParam: {}", passParam);

        log.debug("!!!!!!!!!!!!!!!projectId: {}", projectId);
        ProjectService projectService = new ProjectService();
        Project project = null;
        try {
            Integer integer = Integer.valueOf(projectId);
            log.debug("integer: {}", integer);
            project = projectService.findProjectById(integer);
            log.debug("after integer: {}", integer);
            log.debug("project: {}", project);

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
        if (passParam.equalsIgnoreCase("view-project")) {
            return viewProject;
        } else if (passParam.equalsIgnoreCase("edit-main-project-info")) {
            return editMainProjectInfo;
        } else if (passParam.equalsIgnoreCase("create-client")) {
            return createClient;
        }
        return null;
    }
}
