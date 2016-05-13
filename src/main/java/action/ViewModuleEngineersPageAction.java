package action;

import entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ViewModuleEngineersPageAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(ViewModuleEngineersPageAction.class);

    private ActionResult viewModuleEngineersPage = new ActionResult("view-module-engineers");

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        log.debug("execute...");
        String projectId = req.getParameter("projectId");
        String moduleId = req.getParameter("moduleId");
        List<User> moduleEngineers;
        List<User> engineers;

        try (UserService userService = new UserService()) {
            moduleEngineers = userService.findEngineersByModuleId(Integer.valueOf(moduleId));
            engineers = userService.findAllEngineers();
            log.debug("moduleEngineers.size(): {}", moduleEngineers.size());
            log.debug("engineers.size(): {}", engineers.size());
        } catch (Exception e) {
            throw new ActionException("Failed to findEngineersByModuleId()", e);
        }

        req.setAttribute("moduleEngineers",  moduleEngineers);
        req.setAttribute("engineers",  engineers);
        req.setAttribute("projectId", projectId);
        req.setAttribute("moduleId", moduleId);
        return viewModuleEngineersPage;
    }
}
