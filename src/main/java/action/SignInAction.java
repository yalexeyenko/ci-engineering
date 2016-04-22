package action;

import entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignInAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(SignInAction.class);

    private ActionResult userMain = new ActionResult("user-main", true);
    private ActionResult adminMain = new ActionResult("admin-main", true);
    private ActionResult managerMain = new ActionResult("manager-main", true);
    private ActionResult seniorMain = new ActionResult("senior-main", true);
    private ActionResult engineerMain = new ActionResult("engineer-main", true);
    private ActionResult loginAgain = new ActionResult("welcome");

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        String email = req.getParameter("email_in");
        String password = req.getParameter("password_in");

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        UserService userService = new UserService();
        user = userService.findUserByCredentials(email, password);
        log.debug("user == null: {}", user == null);
        try {
            userService.close();
        } catch (Exception e) {
            throw new ActionException("Failed to close service", e);
        }

        if (user != null) {
            req.getSession().setAttribute("user", user);

            if (user.getRole() != null) {
                switch (user.getRole().name()) {
                    case "ADMIN":
                        return adminMain;
                    case "ENGINEER":
                        return engineerMain;
                    case "MANAGER":
                        return managerMain;
                    case "SENIOR":
                        return seniorMain;
                    default:
                        return userMain;
                }
            } else {
                return userMain;
            }
        } else {
            req.setAttribute("signInError", "Email or password is wrong");
            return loginAgain;
        }

    }
}
