package action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowPageAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(ShowPageAction.class);

    private ActionResult result;

    public ShowPageAction(String page) {
        this.result = new ActionResult(page);
    }

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        log.debug("execute()...");
        return result;
    }
}
