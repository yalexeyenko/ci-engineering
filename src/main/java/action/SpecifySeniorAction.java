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

public class SpecifySeniorAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(SpecifySeniorAction.class);

    private ActionResult specifySeniorAgain = new ActionResult("specify-senior");
    private ActionResult specifySeniorSuccess = new ActionResult("specify-senior");

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
            req.setAttribute("seniorId", seniorId);
            req.setAttribute("projectId", projectId);
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
        if (project.getSenior() != null) {
            req.setAttribute("projectSenior", project.getSenior());
            req.setAttribute("seniorFirstName", project.getSenior().getFirstName());
            req.setAttribute("seniorLastName", project.getSenior().getLastName());
            req.setAttribute("seniorId", project.getSenior().getId());
            req.setAttribute("projectId", projectId);
            req.setAttribute("project", project);
            req.setAttribute("specifySeniorSuccess", "Changes were successfully saved.");
        }
        req.setAttribute("seniors", seniors);
        return specifySeniorSuccess;
    }
}