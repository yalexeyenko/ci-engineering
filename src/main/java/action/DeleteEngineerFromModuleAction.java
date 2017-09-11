package action;

import entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ModuleService;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class DeleteEngineerFromModuleAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(DeleteEngineerFromModuleAction.class);

    private ActionResult viewModuleEngineersPage = new ActionResult("view-module-engineers");

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String engineerId = req.getParameter("engineerId");
        String projectId = req.getParameter("projectId");
        String moduleId = req.getParameter("moduleId");

        try (ModuleService moduleService = new ModuleService()) {
            moduleService.deleteEngineerFromModule(Integer.valueOf(moduleId), Integer.valueOf(engineerId));
        } catch (Exception e) {
            throw new ActionException("Failed to deleteEngineerFromModule()", e);
        }

        List<User> moduleEngineers;
        List<User> engineers;

        try (UserService userService = new UserService()) {
            moduleEngineers = userService.findEngineersByModuleId(Integer.valueOf(moduleId));
        } catch (Exception e) {
            throw new ActionException("Failed to findEngineersByModuleId()", e);
        }

        try (UserService userService = new UserService()) {
            engineers = userService.findAllEngineers();
        } catch (Exception e) {
            throw new ActionException("Failed to findAllEngineers()", e);
        }

        req.setAttribute("removalSuccess", "Инженер успешно удален");
        req.setAttribute("moduleEngineers", moduleEngineers);
        req.setAttribute("engineers", engineers);
        req.setAttribute("projectId", projectId);
        req.setAttribute("moduleId", moduleId);
        return viewModuleEngineersPage;
    }
}
