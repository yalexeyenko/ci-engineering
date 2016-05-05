package action;

import dao.DaoException;
import entity.Project;
import entity.User;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ProjectService;
import service.ServiceException;
import service.UserService;
import validator.Validator;
import validator.Violation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Set;

public class CreateProjectAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(CreateProjectAction.class);

    private Validator validator;

    private ActionResult pageReturn = new ActionResult("create-project");

    public CreateProjectAction() {
        validator = new Validator();
    }

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        log.debug("execute()...");
        String projectName = req.getParameter("projectName");
        String projectDeadline = req.getParameter("projectDeadline");
        log.debug("projectName: {}", projectName);
        log.debug("projectDeadline: {}", projectDeadline);

        Map<String, String[]> parameterMap = req.getParameterMap();
        Set<Violation> violations = validator.validateProjectInfo(parameterMap);

        log.debug("violations.size(): {}", violations.size());
        if (!violations.isEmpty()) {
            for (Violation violation : violations) {
                req.setAttribute(violation.getName(), violation.getViolation());
            }
            req.setAttribute("unsavedName", projectName);
            req.setAttribute("unsavedDeadline", projectDeadline);
            return pageReturn;
        }

        Project currentProject = new Project();
        User manager = null;

        currentProject.setName(projectName);
        currentProject.setDeadline(new LocalDate(projectDeadline));

        try (UserService userService = new UserService()) {
            log.debug("managerId: {}", ((User) req.getSession().getAttribute("user")).getId());
            manager = userService.findUserById(((User) req.getSession().getAttribute("user")).getId());
        } catch (Exception e) {
            throw new ActionException("Failed to findUserById", e);
        }

        currentProject.setManager(manager);

        try (ProjectService projectService = new ProjectService()) {
            currentProject = projectService.createNewProject(currentProject);
        } catch (Exception e) {
                throw new ActionException("Failed to createNewProject()", e);
        }

        req.setAttribute("project", currentProject);
        req.setAttribute("projectCreatedSuccess", "Project successfully created.");
        return pageReturn;

    }
}
