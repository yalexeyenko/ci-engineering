package servlet;

import action.Action;
import action.ActionFactory;
import action.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

@WebServlet(name = "UploadServlet", urlPatterns = "/upload/*")
@MultipartConfig(maxFileSize = 50 * 1024 * 1024)
public class UploadServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(UploadServlet.class);

    private ActionFactory actionFactory;

    @Override
    public void init() throws ServletException {
        log.debug("init()...");
        actionFactory = new ActionFactory();
    }

    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("service()...");
        log.debug("req.getMethod(): {}", req.getMethod());
        log.debug("req.getPathInfo(): {}", req.getPathInfo());
        String actionName = req.getMethod() + req.getPathInfo();
        log.debug("actionName: {}", actionName);

        Action action = actionFactory.getAction(actionName);

        if (action == null) {
            log.debug("action == null");
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Not found");
            return;
        }
        ActionResult result = action.execute(req, resp);
        if (result.isRedirect()) {
            String location = req.getContextPath() + "/do/" + result.getView();
            log.debug("location: {}", location);
            log.debug("redirect:");
            resp.sendRedirect(location);
        } else {
            String path = "/WEB-INF/jsp/" + result.getView() + ".jsp";
            log.debug("location: {}", path);
            log.debug("forward():");
            req.getRequestDispatcher(path).forward(req, resp);
        }


    }
}
