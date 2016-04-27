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

public class SpecifySeniorAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(SpecifySeniorAction.class);

    private ActionResult specifySeniorAgain = new ActionResult("specify-senior");
    private ActionResult viewProject = new ActionResult("view-project");

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        log.debug("execute...");
        String seniorId = req.getParameter("projectSenior");
        String projectId = req.getParameter("projectId");
        log.debug("seniorId: {}", seniorId);
        log.debug("projectId: {}", projectId);

        ProjectService projectService = new ProjectService();
        UserService userService = new UserService();

        Project project;
        User senior;


        try {
            log.debug("findProjectById()");
            project = projectService.findProjectById(Integer.valueOf(projectId));
        } catch (DaoException e) {
            log.debug("Failed to findProjectById()");
            try {
                projectService.close();
            } catch (Exception ex) {
                throw new ActionException("Failed to close service", ex);
            }
            req.setAttribute("specifySeniorError", "Verify your data. Cannot specify senior engineer.");
            return specifySeniorAgain;
        }
        try {
            log.debug("findUserById()");
            Integer seniorIdInt = Integer.valueOf(seniorId);
            log.debug("seniorIdInt: {}", seniorIdInt);
            senior = userService.findUserById(seniorIdInt);
        } catch (DaoException e) {
            log.debug("Failed to findUserById()");
            try {
                userService.close();
            } catch (Exception ex) {
                throw new ActionException("Failed to close service", ex);
            }
            req.setAttribute("specifySeniorError", "Verify your data. Cannot specify senior engineer.");
            return specifySeniorAgain;
        }
        project.setSenior(senior);
        try {
            log.debug("updateProjectSenior()");
            projectService.updateProjectSenior(project);
        } catch (DaoException e) {
            log.debug("Failed to updateProjectSenior()");
            try {
                projectService.close();
            } catch (Exception ex) {
                throw new ActionException("Failed to close service", ex);
            }
            req.setAttribute("specifySeniorError", "Verify your data. Cannot specify senior engineer.");
            return specifySeniorAgain;
        }
        req.setAttribute("project", project);
        return viewProject;
    }
}