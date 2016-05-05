package action;

import dao.DaoException;
import entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AdminContentStaffAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(AdminContentStaffAction.class);
    private ActionResult adminContentStaffPage = new ActionResult("admin-main");

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        log.debug("execute...");
        List<User> users;

        try (UserService userService = new UserService()) {
            users = userService.findAllUsers();
            log.debug("users.size(): {}", users.size());
        } catch (Exception e) {
            throw new ActionException("Failed to findAllUsers()", e);
        }

        req.setAttribute("users",  users);
        return adminContentStaffPage;
    }
}
