package action;

import dao.DaoException;
import entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import validator.Validator;
import validator.Violation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Set;

public class EditProfileAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(SignInAction.class);

    private Validator formValidator;

    public EditProfileAction() {
        formValidator = new Validator();
    }

    private ActionResult home = new ActionResult("home", true);
    private ActionResult editProfileAgain = new ActionResult("edit-profile");

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        String degree = req.getParameter("degree");
        String role = req.getParameter("role");
        String currentPassword = req.getParameter("current_password");
        String password = req.getParameter("password");

        Map<String, String[]> parameterMap = req.getParameterMap();
        Set<Violation> violations = formValidator.validate(parameterMap);

        HttpSession session = req.getSession(true);// true or false???
        User currentUser = (User) session.getAttribute("user");
        log.debug("currentUser is null: {}", currentUser == null);
        if (!currentPassword.equals(currentUser.getPassword())) {
            Violation violation = new Violation();
            violation.setName("wrongPasswordViolation");
            violation.setViolation("Password is wrong");
            violations.add(violation);
        }
        if (currentPassword.equals(password)) {
            Violation violation = new Violation();
            violation.setName("duplicatePasswordViolation");
            violation.setViolation("Password already exists");
            violations.add(violation);
        }

        if (!violations.isEmpty()) {
            for (Violation violation : violations) {
                log.debug("violation.getName: {}", violation.getName());
                log.debug("violation.getViolation: {}", violation.getViolation());
                req.setAttribute(violation.getName(), violation.getViolation());
            }
            return editProfileAgain;
        }

        currentUser.setFirstName(firstName);
        currentUser.setLastName(lastName);
        currentUser.setEmail(email);
        currentUser.setDegree(degree);
        if (role != null) {
            currentUser.setRole(User.Role.valueOf(role));
        }
        currentUser.setPassword(password);

        UserService userService = new UserService();
        try {
            userService.updateUser(currentUser);
        } catch (DaoException e) {
            try {
                userService.close();
            } catch (Exception ex) {
                throw new ActionException("Failed to close service", ex);
            }
            req.setAttribute("editProfileError", "Email already registered");
            return editProfileAgain;
        }
        req.getSession().setAttribute("user", currentUser);
        return home;

    }
}
