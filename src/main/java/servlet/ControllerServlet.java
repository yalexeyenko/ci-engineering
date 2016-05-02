package servlet;

import action.Action;
import action.ActionFactory;
import action.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ControllerServlet", urlPatterns = "/do/*")
public class ControllerServlet extends javax.servlet.http.HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(ControllerServlet.class);

    private ActionFactory actionFactory;

    @Override
    public void init() throws ServletException {
        log.debug("init()...");
        actionFactory = new ActionFactory();
    }

    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("service()...");
        String actionName = req.getMethod() + req.getPathInfo();
        log.debug(actionName);

        Action action = actionFactory.getAction(actionName);

        if (action == null) {
            log.debug("action == null");
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Not found");
            return;
        }

        ActionResult result = action.execute(req, resp);


        if (result.isRedirect()) {
            String location = req.getContextPath() + "/do/" + result.getView();
            log.debug("redirect:");
            log.debug("location: {}", location);
            resp.sendRedirect(location);
        } else {
            String path = "/WEB-INF/jsp/" + result.getView() + ".jsp";
            log.debug("forward():");
            log.debug("location: {}", path);
            req.getRequestDispatcher(path).forward(req, resp);
        }

    }
}
