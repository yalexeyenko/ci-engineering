package action;

import entity.Module;
import entity.Project;
import entity.User;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ModuleService;
import service.ProjectService;
import service.UserService;
import validator.Validator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateModuleAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(CreateProjectAction.class);

    private Validator validator;

    private ActionResult pageReturn = new ActionResult("add-module");

    public CreateModuleAction() {
        validator = new Validator();
    }

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        log.debug("execute()...");
        String moduleName = req.getParameter("moduleName");
        String moduleDeadline = req.getParameter("moduleDeadline");
        String projectId = req.getParameter("projectId");
        log.debug("moduleName: {}", moduleName);
        log.debug("moduleDeadline: {}", moduleDeadline);
        log.debug("projectId: {}", projectId);

        Module module = new Module();
        User manager;
        Project project;

        module.setName(moduleName);
        module.setDeadline(new LocalDate(moduleDeadline));

        try (UserService userService = new UserService()) {
            manager = userService.findUserById(((User) req.getSession().getAttribute("user")).getId());
        } catch (Exception e) {
            throw new ActionException("Failed to findUserById", e);
        }

        module.setManager(manager);

        try (ProjectService projectService = new ProjectService()) {
            project = projectService.findProjectById(Integer.valueOf(projectId));
        } catch (Exception e) {
            throw new ActionException("Failed to findProjectById()", e);
        }

        module.setProject(project);

        try (ModuleService moduleService = new ModuleService()) {
            module = moduleService.createModule(module);
        } catch (Exception e) {
            throw new ActionException("Failed to createModule()", e);
        }

        req.setAttribute("module", module);
        req.setAttribute("projectId", projectId);
        req.setAttribute("moduleCreatedSuccess", "Раздел успешно создан");
        return pageReturn;
    }
}
