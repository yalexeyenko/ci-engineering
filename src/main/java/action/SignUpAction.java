package action;

import entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import validator.Validator;
import validator.Violation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        String firstName = req.getParameter("userFirstName");
        String lastName = req.getParameter("userLastName");
        String email = req.getParameter("userEmail");
        String password = req.getParameter("password");
        String repeatPassword = req.getParameter("repeatPassword");


        log.debug("firstName: {}", firstName);
        log.debug("lastName: {}", lastName);
        log.debug("email: {}", email);
        log.debug("password: {}", password);
        log.debug("repeatPassword: {}" ,repeatPassword);
        Map<String, String[]> parameterMap = req.getParameterMap();
        Set<Violation> violations = validator.validateMainUserInfoInput(parameterMap);
        Set<Violation> passwordViolations = validator.validatePassword(password, repeatPassword);
        violations.addAll(passwordViolations);


        log.debug("violations.size(): {}", violations.size());
        if (!violations.isEmpty()) {
            for (Violation violation : violations) {
                log.debug("violation.getName(): {}", violation.getName());
                log.debug("violation.getViolation(): {}", violation.getViolation());
                req.setAttribute(violation.getName(), violation.getViolation());
            }
            req.setAttribute("userFirstName", firstName);
            req.setAttribute("userLastName", lastName);
            req.setAttribute("userEmail", email);
            return singUpAgain;
        }

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(User.Role.REGISTERED);

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
