package action;

import entity.Project;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ProjectService;
import validator.Validator;
import validator.Violation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Set;

public class EditMainProjectInfoAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(EditMainProjectInfoAction.class);

    private Validator validator;

    private ActionResult pageReturn = new ActionResult("edit-main-project-info");

    public EditMainProjectInfoAction() {
        validator = new Validator();
    }

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        log.debug("execute...");
        String projectId = req.getParameter("projectId");
        String projectName = req.getParameter("projectName");
        LocalDate projectDeadline = new LocalDate(req.getParameter("projectDeadline"));
        boolean projectFinished = Boolean.parseBoolean(req.getParameter("projectFinished"));

        Map<String, String[]> parameterMap = req.getParameterMap();
        Set<Violation> violations = validator.validateProjectInfo(parameterMap);

        if (!violations.isEmpty()) {
            for (Violation violation : violations) {
                req.setAttribute(violation.getName(), violation.getViolation());
            }
            req.setAttribute("projectId", projectId);
            req.setAttribute("projectName", projectName);
            req.setAttribute("projectDeadline", projectDeadline);
            req.setAttribute("projectFinished", projectFinished);
            log.debug("projectFinished: {}", projectFinished);
            return pageReturn;
        }

        Project project = new Project();
        project.setId(Integer.valueOf(projectId));
        project.setName(projectName);
        project.setDeadline(projectDeadline);
        project.setFinished(projectFinished);

        try (ProjectService projectService = new ProjectService()) {
            projectService.updateProject(project);
        } catch (Exception e) {
            throw new ActionException("Failed to updateProject()", e);
        }
        req.setAttribute("project", project);
        req.setAttribute("changesSavedSuccessfully", "Изменения сохранены");
        req.setAttribute("projectId", projectId);
        req.setAttribute("projectName", projectName);
        req.setAttribute("projectDeadline", projectDeadline);
        req.setAttribute("projectFinished", projectFinished);
        log.debug("projectFinished: {}", projectFinished);

        return pageReturn;
    }
}
