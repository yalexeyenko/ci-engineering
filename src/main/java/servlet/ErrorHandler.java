package servlet;

import filter.SecurityFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ErrorHandler", urlPatterns = "/error")
public class ErrorHandler extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(SecurityFilter.class);
    private static final String ERROR_PAGE = "/WEB-INF/jsp/error-page.jsp";

    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Throwable throwable = (Throwable) req.getAttribute("javax.servlet.error.exception");
        Class throwableClass = (Class) req.getAttribute("javax.servlet.error.exception_type");
        Integer statusCode = (Integer) req.getAttribute("javax.servlet.error.status_code");
        String errorMessage = (String) req.getAttribute("javax.servlet.error.message");
        String requestUri = (String) req.getAttribute("javax.servlet.error.request_uri");
        if (requestUri == null) {
            requestUri = "Unknown";
        }
        log.debug("statusCode: {}", statusCode);
        log.debug("requestUri: {}", requestUri);
        log.debug("errorMessage : {}", errorMessage);
        log.debug("throwable: {}", throwable);
        log.debug("throwableClass: {}", throwableClass);

        req.setAttribute("statusCode", statusCode);
        req.setAttribute("requestUri", requestUri);
        req.getRequestDispatcher(ERROR_PAGE).forward(req, resp);

    }
}
