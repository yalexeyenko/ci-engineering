package action;

import dao.DaoException;
import entity.Project;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ProjectService;
import validator.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateProjectAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(CreateProjectAction.class);

    private Validator projectValidator;

    private ActionResult mainPage = new ActionResult("main-page", true);
    private ActionResult createProjectAgain = new ActionResult("createProject");

    public CreateProjectAction() {
        projectValidator = new Validator();
    }

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        log.debug("execute()...");
        String projectName = req.getParameter("projectName");
        String projectDeadline = req.getParameter("projectDeadline");
        log.debug("projectName: {}", projectName);
        log.debug("projectDeadline: {}", projectDeadline);

        Project currentProject = new Project();
        currentProject.setName(projectName);
        currentProject.setDeadline(new LocalDate(projectDeadline));

        ProjectService projectService = new ProjectService();
        try {
            currentProject = projectService.createNewProject(currentProject);
        } catch (DaoException e) {
            try {
                projectService.close();
            } catch (Exception ex) {
                throw new ActionException("Failed to close service", ex);
            }
            req.setAttribute("createProjectError", "Verify data. Cannot create project");
            return createProjectAgain;
        }

        req.getSession().setAttribute("project", currentProject);
        return mainPage;

    }
}
