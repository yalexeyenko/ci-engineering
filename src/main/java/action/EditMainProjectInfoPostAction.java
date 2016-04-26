package action;

import dao.DaoException;
import entity.Project;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ProjectService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditMainProjectInfoPostAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(EditMainProjectInfoPostAction.class);

    private ActionResult viewProject = new ActionResult("view-project");
    private ActionResult editMainProjectInfoAgain = new ActionResult("edit-main-project-info");

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        log.debug("execute...");
        String projectId = req.getParameter("projectId");
        String projectName = req.getParameter("projectName");
        LocalDate projectStartDate = new LocalDate(req.getParameter("projectStartDate"));
        LocalDate projectDeadline = new LocalDate(req.getParameter("projectDeadline"));
        boolean projectFinished = Boolean.valueOf(req.getParameter("projectFinished"));

        Project project = new Project();
        project.setId(Integer.valueOf(projectId));
        project.setName(projectName);
        project.setStartDate(projectStartDate);
        project.setDeadline(projectDeadline);
        project.setFinished(projectFinished);

        ProjectService projectService = new ProjectService();
        try {
            projectService.updateProject(project);
        } catch (DaoException e) {
            try {
                projectService.close();
            } catch (Exception ex) {
                throw new ActionException("Failed to close service", ex);
            }
            req.setAttribute("editMainProjectInfoError", "Verify your data. Cannot update project main info.");
            return editMainProjectInfoAgain;
        }
        req.setAttribute("project", project);
        return viewProject;
    }

}
