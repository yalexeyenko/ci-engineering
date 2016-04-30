package action;

import dao.DaoException;
import entity.Project;
import entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ProjectService;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class PassProjectIdAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(PassProjectIdAction.class);

    private ActionResult viewProject = new ActionResult("view-project");
    private ActionResult editMainProjectInfo = new ActionResult("edit-main-project-info");
    private ActionResult createClient = new ActionResult("create-client");
    private ActionResult viewClient = new ActionResult("view-client");
    private ActionResult editClient = new ActionResult("edit-client");
    private ActionResult specifySenior = new ActionResult("specify-senior");

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        log.debug("execute...");
        String projectId = req.getParameter("projectId");
        String passParam = req.getParameter("passProjectId");
        log.debug("projectId: {}", projectId);
        log.debug("passParam: {}", passParam);

        log.debug("!!!!!!!!!!!!!!!projectId: {}", projectId);
        UserService userService;
        ProjectService projectService = new ProjectService();
        Project project = null;
        try {
            Integer integer = Integer.valueOf(projectId);
            log.debug("integer: {}", integer);
            project = projectService.findProjectById(integer);
            log.debug("after integer: {}", integer);
            log.debug("project: {}", project);

        } catch (DaoException e) {
            log.debug("Failed to findProjectById()");
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
        } else if (passParam.equalsIgnoreCase("view-client")) {
            return viewClient;
        } else if (passParam.equalsIgnoreCase("edit-client")) {
            return editClient;
        } else if (passParam.equalsIgnoreCase("specify-senior")) {
            userService = new UserService();
            List<User> seniors = null;
            try {
                seniors = userService.findAllSeniors();
            } catch (DaoException e) {
                log.debug("Failed to findAllSeniors()");
                try {
                    projectService.close();
                } catch (Exception ex) {
                    throw new ActionException("Failed to close service", ex);
                }
            }
            req.setAttribute("seniors", seniors);
            return specifySenior;
        }
        return null;// todo error page
    }
}
