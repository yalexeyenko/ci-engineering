package action;

import entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PassUserParametersAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(PassUserParametersAction.class);

    private ActionResult adminViewUser = new ActionResult("admin-view-user");
    private ActionResult editProfile = new ActionResult("edit-profile");

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        log.debug("execute...");
        String userId = req.getParameter("userId");
        String passParam = req.getParameter("passUserId");
        log.debug("userId: {}", userId);
        log.debug("passUserId: {}", passParam);

        User user;

        try (UserService userService = new UserService();) {
            user = userService.findUserById(Integer.valueOf(userId));
        } catch (Exception e) {
            log.debug("Failed to findUserById()");
            throw new ActionException("Failed to close service", e);
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
        } else {
            throw new ActionException("Failed to pass user parameters.");
        }
    }
}
