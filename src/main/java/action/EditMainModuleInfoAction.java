package action;

import entity.Module;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ModuleService;
import validator.Validator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditMainModuleInfoAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(EditMainModuleInfoAction.class);

    private Validator validator;

    private ActionResult returnPage = new ActionResult("edit-main-module-info");

    public EditMainModuleInfoAction() {
        validator = new Validator();
    }

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        log.debug("execute()...");
        String projectId = req.getParameter("projectId");
        String moduleId = req.getParameter("moduleId");
        String moduleName = req.getParameter("moduleName");
        LocalDate moduleDeadline = new LocalDate(req.getParameter("moduleDeadline"));
        boolean moduleFinished = Boolean.parseBoolean(req.getParameter("moduleFinished"));

        Module module = new Module();
        module.setId(Integer.valueOf(moduleId));
        module.setName(moduleName);
        module.setDeadline(moduleDeadline);
        module.setFinished(moduleFinished);

        try (ModuleService moduleService = new ModuleService()) {
            moduleService.updateModule(module);
        } catch (Exception e) {
            throw new ActionException("Failed to updateModule()", e);
        }

        req.setAttribute("projectId", projectId);
        req.setAttribute("module", module);
        req.setAttribute("moduleId", moduleId);
        req.setAttribute("moduleName", moduleName);
        req.setAttribute("moduleDeadline", moduleDeadline);
        req.setAttribute("moduleFinished", moduleFinished);
        req.setAttribute("changesSavedSuccessfully", "Changes successfully saved.");

        return returnPage;
    }
}
