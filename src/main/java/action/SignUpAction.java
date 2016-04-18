package action;

import entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignUpAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(SignUpAction.class);

    private ActionResult home = new ActionResult("home", true);
    private ActionResult singUpAgain = new ActionResult("welcome");

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        log.debug("execute()...");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        log.debug("firstName: {}", firstName);
        log.debug("lastName: {}", lastName);
        log.debug("email: {}", email);
        log.debug("password: {}", password);

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);

        UserService userService = new UserService();
        user = userService.createUser(user);

        log.debug("user == null: {}", user == null);

        try {
            userService.close();
        } catch (Exception e) {
            throw new ActionException("Failed to close service", e);
        }

        if (user != null) {
            log.debug("user != null. home.getView(): {}", home.getView());
            req.getSession().setAttribute("user", user);
            log.debug("session user.getFirstName(): {}", user.getFirstName());
            return home;
        } else {
            log.debug("user == null. home.getView(): {}", home.getView());
            req.setAttribute("signUpError", "email already exists");
            return singUpAgain;
        }
    }
}
