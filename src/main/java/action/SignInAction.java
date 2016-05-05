package action;

import entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignInAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(SignInAction.class);

    private ActionResult mainPage = new ActionResult("main-page", true);
    private ActionResult loginAgain = new ActionResult("welcome");

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        log.debug("execute()...");
        String email = req.getParameter("email_in");
        String password = req.getParameter("password_in");
        log.debug("email: {}", email);
        log.debug("password: {}", password);

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        try (UserService userService = new UserService()) {
            user = userService.findUserByCredentials(email, password);
        } catch (Exception e) {
            throw new ActionException("Failed to findUserByCredentials()", e);
        }
        log.debug("user: {}", user);
        if (user != null) {
            req.getSession(false).setAttribute("user", user);
            return mainPage;
        } else {
            req.setAttribute("signInError", "Email or password is wrong");
            return loginAgain;
        }

    }
}
