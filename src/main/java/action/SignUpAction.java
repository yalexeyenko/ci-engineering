package action;

import entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import validator.Validator;
import validator.Violation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SignUpAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(SignUpAction.class);

    private Validator validator;

    private ActionResult mainPage = new ActionResult("main-page", true);
    private ActionResult singUpAgain = new ActionResult("welcome");

    public SignUpAction() {
        validator = new Validator();
    }

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        log.debug("execute()...");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        String password = req.getParameter("password");


        Map<String, String[]> parameterMap = req.getParameterMap();
        Set<Violation> violations = validator.validateMainUserInfoInput(parameterMap);
        Set<Violation> passwordViolations = validator.validatePassword(parameterMap);
        violations.addAll(passwordViolations);


        log.debug("violations.size(): {}", violations.size());
        if (!violations.isEmpty()) {
            for (Violation violation : violations) {
                log.debug("violation.getName(): {}", violation.getName());
                log.debug("violation.getViolation(): {}", violation.getViolation());
                req.setAttribute(violation.getName(), violation.getViolation());
            }
            return singUpAgain;
        }

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(User.Role.GUEST);

        UserService userService = new UserService();
        user = userService.createUser(user);

        log.debug("user == null: {}", user == null);

        try {
            userService.close();
        } catch (Exception e) {
            throw new ActionException("Failed to close service", e);
        }

        if (user != null) {
            log.debug("user != null. userMain.getView(): {}", mainPage.getView());
            req.getSession(false).setAttribute("user", user);
            log.debug("session user.getFirstName(): {}", user.getFirstName());
            return mainPage;
        } else {
            log.debug("user == null. userMain.getView(): {}", mainPage.getView());
            req.setAttribute("signUpError", "Email already registered");
            return singUpAgain;
        }
    }
}
