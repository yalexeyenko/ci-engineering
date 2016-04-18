package action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignOutAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(SignOutAction.class);

    private ActionResult welcome = new ActionResult("welcome", true);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        log.debug("SignOutAction execute...");
        req.getSession().invalidate();
        return welcome;
    }
}
