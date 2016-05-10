package action;

import entity.Module;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ModuleService;
import validator.Validator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditMainModuleInfoPageAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(EditMainModuleInfoPageAction.class);

    private Validator validator;

    private ActionResult editModulePage = new ActionResult("edit-main-module-info");

    public EditMainModuleInfoPageAction() {
        validator = new Validator();
    }

    @SuppressWarnings("Duplicates")
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String projectId = req.getParameter("projectId");
        String moduleId = req.getParameter("moduleId");
        log.debug("projectId: {}", projectId);
        log.debug("moduleId: {}", moduleId);

        Module module;

        try (ModuleService moduleService = new ModuleService()) {
            module = moduleService.findModuleById(Integer.valueOf(moduleId));
        } catch (Exception e) {
            throw new ActionException("Failed to findModuleById()", e);
        }

        req.setAttribute("projectId", projectId);
        req.setAttribute("module", module);
        req.setAttribute("moduleId", module.getId());
        req.setAttribute("moduleName", module.getName());
        req.setAttribute("moduleStartDate", module.getStartDate());
        req.setAttribute("moduleDeadline", module.getDeadline());
        req.setAttribute("moduleFinished", module.isFinished());
        return editModulePage;

    }
}
