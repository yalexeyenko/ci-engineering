package action;

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

    private ActionResult specifySeniorSuccess = new ActionResult("specify-senior");

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        log.debug("execute...");
        String seniorId = req.getParameter("projectSenior");
        String projectId = req.getParameter("projectId");
        log.debug("seniorId: {}", seniorId);
        log.debug("projectId: {}", projectId);


        Project project;
        User senior;


        try (ProjectService projectService = new ProjectService()) {
            log.debug("findProjectById()");
            project = projectService.findProjectById(Integer.valueOf(projectId));
        } catch (Exception e) {
            throw new ActionException("Failed to close service", e);
        }

        try (UserService userService = new UserService()) {
            senior = userService.findUserById(Integer.valueOf(seniorId));
        } catch (Exception e) {
            throw new ActionException("Failed to close service", e);
        }

        project.setSenior(senior);

        try (ProjectService projectService = new ProjectService()) {
            projectService.updateProjectSenior(project);
        } catch (Exception e) {
            throw new ActionException("Failed to close service", e);
        }

        List<User> seniors;
        try (UserService userService = new UserService()) {
            seniors = userService.findAllSeniors();
        } catch (Exception e) {
            throw new ActionException("Failed to close service", e);
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