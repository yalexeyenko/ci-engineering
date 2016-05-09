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
import java.io.IOException;

@WebServlet(name = "DownloadServlet", urlPatterns = "/download/*")
@MultipartConfig(maxFileSize = 50 * 1024 * 1024)
public class DownloadServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(DownloadServlet.class);

    private ActionFactory actionFactory;

    @Override
    public void init() throws ServletException {
        actionFactory = new ActionFactory();
    }

    @SuppressWarnings("Duplicates")// todo duplicate code underline
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

        action.execute(req, resp);
    }
}
