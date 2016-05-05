package action;

import entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import validator.Validator;
import validator.Violation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Set;

public class ChangePasswordAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(ChangePasswordAction.class);

    private Validator validator;

    private ActionResult pageReturn = new ActionResult("edit-profile");

    public ChangePasswordAction() {
        validator = new Validator();
    }

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        log.debug("execute()...");
        String currentPassword = req.getParameter("current-password");
        String password = req.getParameter("password");
        String repeatPassword = req.getParameter("repeatPassword");

        String firstName = req.getParameter("userFirstName");
        String lastName = req.getParameter("userLastName");
        String email = req.getParameter("userEmail");
        String degree = req.getParameter("userDegree");
        String role = req.getParameter("userRole");

        log.debug("firstName: {}", firstName);
        log.debug("lastName: {}", lastName);
        log.debug("email: {}", email);
        log.debug("degree: {}", degree);
        log.debug("role: {}", role);
        log.debug("currentPassword: {}", currentPassword);
        log.debug("password: {}", password);

        Set<Violation> violations = validator.validatePassword(password, repeatPassword);

        HttpSession session = req.getSession(false);
        User currentUser = (User) session.getAttribute("user");
        log.debug("currentUser: {}", currentUser);
        if (!currentPassword.equals(currentUser.getPassword())) {
            Violation violation = new Violation();
            violation.setName("wrongPasswordViolation");
            violation.setViolation("Password is wrong");
            violations.add(violation);
        }

        if (!violations.isEmpty()) {
            for (Violation violation : violations) {
                log.debug("violation.getName: {}", violation.getName());
                log.debug("violation.getViolation: {}", violation.getViolation());
                req.setAttribute(violation.getName(), violation.getViolation());
            }
            req.setAttribute("userFirstName", firstName);
            req.setAttribute("userLastName", lastName);
            req.setAttribute("userEmail", email);
            req.setAttribute("userDegree", degree);
            if (currentUser.getRole() != null) {
                req.setAttribute("userRole", role);
            }
            req.setAttribute("current-password", currentPassword);
            return pageReturn;
        }

        currentUser.setPassword(password);

        try (UserService userService = new UserService()) {
            userService.changePassword(currentUser);
        } catch (Exception e) {
            throw new ActionException("Failed to changePassword()");
        }
        req.getSession().setAttribute("user", currentUser);
        req.setAttribute("changePasswordSuccess", "Successfully changed password.");
        req.setAttribute("userFirstName", firstName);
        req.setAttribute("userLastName", lastName);
        req.setAttribute("userEmail", email);
        req.setAttribute("userDegree", degree);
        if (currentUser.getRole() != null) {
            req.setAttribute("userRole", role);
        }
        req.setAttribute("current-password", currentPassword);
        return pageReturn;
    }
}
