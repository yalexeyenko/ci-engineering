package action;

import dao.DaoException;
import entity.Project;
import entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ProjectService;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class PassUserIdAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(PassUserIdAction.class);

    private ActionResult adminViewUser = new ActionResult("admin-view-user");
    private ActionResult editProfile = new ActionResult("edit-profile");

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        log.debug("execute...");
        String userId = req.getParameter("userId");
        String passParam = req.getParameter("passUserId");
        log.debug("userId: {}", userId);
        log.debug("passUserId: {}", passParam);

        UserService userService = new UserService();
        User user = null;
        try {
            Integer integer = Integer.valueOf(userId);
            log.debug("integer: {}", integer);
            user = userService.findUserById(integer);
        } catch (DaoException e) {
            log.debug("Failed to findUserById()");
            try {
                userService.close();
            } catch (Exception ex) {
                throw new ActionException("Failed to close service", ex);
            }
        }
        try {
            userService.close();
        } catch (Exception ex) {
            throw new ActionException("Failed to close service", ex);
        }

        req.setAttribute("adUser", user);
        if (passParam.equalsIgnoreCase("admin-view-user")) {
            return adminViewUser;
        } else if (passParam.equalsIgnoreCase("edit-user")) {
            req.setAttribute("userId", user.getId());
            req.setAttribute("userFirstName", user.getFirstName());
            req.setAttribute("userLastName", user.getLastName());
            req.setAttribute("userEmail", user.getEmail());
            if (user.getDegree() != null) {
                req.setAttribute("userDegree", user.getDegree());
            }
            if (user.getRole() != null) {
                req.setAttribute("userRole", user.getRole());
            }
            return editProfile;
        }
        return null;// todo error page
    }
}
