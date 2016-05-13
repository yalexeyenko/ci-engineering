package action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddModuleFilePageAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(AddModuleFilePageAction.class);

    private ActionResult viewAddModuleFilePage = new ActionResult("add-module-file");


    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String projectId = req.getParameter("projectId");
        String moduleId = req.getParameter("moduleId");

        req.setAttribute("projectId", projectId);
        req.setAttribute("moduleId", moduleId);
        return viewAddModuleFilePage;
    }
}
