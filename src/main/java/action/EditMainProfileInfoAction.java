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

public class EditMainProfileInfoAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(EditMainProfileInfoAction.class);

    private Validator validator;

    private ActionResult editSuccess = new ActionResult("edit-profile");
    private ActionResult editAgain = new ActionResult("edit-profile");

    public EditMainProfileInfoAction() {
        validator = new Validator();
    }

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        String firstName = req.getParameter("userFirstName");
        String lastName = req.getParameter("userLastName");
        String email = req.getParameter("userEmail");
        String degree = req.getParameter("userDegree");
        String role = req.getParameter("userRole");

        Map<String, String[]> parameterMap = req.getParameterMap();
        Set<Violation> violations = validator.validateMainUserInfoInput(parameterMap);
        log.debug("violations.size(): {}", violations.size());

        HttpSession session = req.getSession(false);
        User currentUser = (User) session.getAttribute("user");
        log.debug("currentUser is null: {}", currentUser == null);

        if (!violations.isEmpty()) {
            for (Violation violation : violations) {
                req.setAttribute(violation.getName(), violation.getViolation());
            }
            req.setAttribute("userFirstName", firstName);
            req.setAttribute("userLastName", lastName);
            req.setAttribute("userEmail", email);
            req.setAttribute("userDegree", degree);
            if (currentUser.getRole() != null) {
                req.setAttribute("userRole", role);
            }
            return editAgain;
        }

        currentUser.setFirstName(firstName);
        currentUser.setLastName(lastName);
        currentUser.setEmail(email);
        currentUser.setDegree(degree);
        if (role != null) {
            currentUser.setRole(User.Role.valueOf(role));
        }

        UserService userService = new UserService();
        try {
            userService.updateMainProfileInfo(currentUser);
        } catch (DaoException e) {
            log.debug("Failed to updateMainProfileInfo()");
            try {
                userService.close();
            } catch (Exception ex) {
                throw new ActionException("Failed to close service", ex);
            }
            req.setAttribute("editMainProfileInfoError", "Email already registered");
            return editAgain;
        }
        req.getSession().setAttribute("user", currentUser);
        req.setAttribute("editMainProfileInfoSuccess", "Changes have been saved successfully.");

        req.setAttribute("userFirstName", firstName);
        req.setAttribute("userLastName", lastName);
        req.setAttribute("userEmail", email);
        req.setAttribute("userDegree", degree);
        if (currentUser.getRole() != null) {
            req.setAttribute("userRole", role);
        }
        return editSuccess;
    }
}
