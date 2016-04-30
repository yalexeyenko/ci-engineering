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
import java.util.Map;
import java.util.Set;

public class ChangeRoleAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(ChangeRoleAction.class);

    private Validator validator;

    private ActionResult changeSuccess = new ActionResult("admin-view-user");
    private ActionResult changeAgain = new ActionResult("admin-view-user");

    public ChangeRoleAction() {
        validator = new Validator();
    }

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        log.debug("execute()...");
        String userRole = req.getParameter("role");
        String userId = req.getParameter("userId");
        log.debug("userRole: {}", userRole);
        log.debug("userId: {}", userId);

        Map<String, String[]> parameterMap = req.getParameterMap();
        Set<Violation> violations = validator.validateMainUserInfoInput(parameterMap);

        if (!violations.isEmpty()) {
            for (Violation violation : violations) {
                req.setAttribute(violation.getName(), violation.getViolation());
            }
            return changeAgain;
        }

        UserService userService = new UserService();
        User user;

        try {
            user = userService.findUserById(Integer.valueOf(userId));
        } catch (DaoException e) {
            log.debug("Failed to findUserById()");
            try {
                userService.close();
            } catch (Exception ex) {
                throw new ActionException("Failed to close service", ex);
            }
            req.setAttribute("changeRoleError", "Failed to change role. Verify your data.");
            return changeAgain;
        }
        log.debug("user.getRole(): {}", user.getRole());
        if (userRole != null) {
            user.setRole(User.Role.valueOf(userRole));
        }
        log.debug("user.getRole(): {}", user.getRole());

        try {
            userService.changeUserRole(user);
        } catch (DaoException e) {
            log.debug("Failed to changeUserRole()");
            try {
                userService.close();
            } catch (Exception ex) {
                throw new ActionException("Failed to close service", ex);
            }
            req.setAttribute("changeRoleError", "Failed to change role. Verify your data.");
            return changeAgain;
        }

        req.setAttribute("changeRoleSuccess", "Role has been changed successfully.");
        req.setAttribute("adUser", user);
        return changeSuccess;
    }
}
