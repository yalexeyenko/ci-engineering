package action;

import entity.FileDoc;
import entity.Module;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ModuleService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ViewProjectModulesAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(ViewProjectModulesAction.class);

    private ActionResult viewProjectModulesPage = new ActionResult("view-project-modules");
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        log.debug("execute()...");
        String projectId = req.getParameter("projectId");
        List<Module> modules;

        try (ModuleService moduleService = new ModuleService()) {
            modules = moduleService.findModulesByProjectId(Integer.valueOf(projectId));
        } catch (Exception e) {
            throw new ActionException("Failed to findModulesByProjectId", e);
        }

        req.setAttribute("modules",  modules);
        req.setAttribute("projectId", projectId);
        return viewProjectModulesPage;
    }
}
