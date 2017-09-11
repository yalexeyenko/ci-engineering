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

public class AddModuleEngineerAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(AddModuleEngineerAction.class);

    private ActionResult viewModuleEngineersPage = new ActionResult("view-module-engineers");

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String engineerId = req.getParameter("moduleEngineer");
        String projectId = req.getParameter("projectId");
        String moduleId = req.getParameter("moduleId");

        List<User> moduleEngineers;
        List<User> engineers;
        User engineer;

        try (UserService userService = new UserService()) {
            moduleEngineers = userService.findEngineersByModuleId(Integer.valueOf(moduleId));
        } catch (Exception e) {
            throw new ActionException("Failed to findEngineersByModuleId()", e);
        }

        try (UserService userService = new UserService()) {
            engineer = userService.findUserById(Integer.valueOf(engineerId));
        } catch (Exception e) {
            throw new ActionException("Failed to findUserById()", e);
        }

        try (UserService userService = new UserService()) {
            engineers = userService.findAllEngineers();
        } catch (Exception e) {
            throw new ActionException("Failed to findAllEngineers()", e);
        }

        if (!moduleEngineers.contains(engineer)) {
            try (ModuleService moduleService = new ModuleService()) {
                moduleService.addEngineerToModule(Integer.valueOf(moduleId), Integer.valueOf(engineerId));
            } catch (Exception e) {
                throw new ActionException("Failed to addEngineerToModule()", e);
            }

            try (UserService userService = new UserService()) {
                moduleEngineers = userService.findEngineersByModuleId(Integer.valueOf(moduleId));
            } catch (Exception e) {
                throw new ActionException("Failed to findEngineersByModuleId()", e);
            }

            req.setAttribute("addEngineerSuccess", "Инженер успешно добавлен");

            req.setAttribute("moduleEngineers", moduleEngineers);
            req.setAttribute("engineers", engineers);
            req.setAttribute("projectId", projectId);
            req.setAttribute("moduleId", moduleId);
            return viewModuleEngineersPage;
        }

        req.setAttribute("addEngineerFail", "Инженер уже добавлен");
        req.setAttribute("moduleEngineers", moduleEngineers);
        req.setAttribute("engineers", engineers);
        req.setAttribute("projectId", projectId);
        req.setAttribute("moduleId", moduleId);
        return viewModuleEngineersPage;


    }
}
