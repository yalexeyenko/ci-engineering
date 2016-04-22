package action;

import entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChooseMainPageAction implements Action {
    private ActionResult userMain = new ActionResult("user-main", true);
    private ActionResult adminMain = new ActionResult("admin-main", true);
    private ActionResult managerMain = new ActionResult("manager-main", true);
    private ActionResult seniorMain = new ActionResult("senior-main", true);
    private ActionResult engineerMain = new ActionResult("engineer-main", true);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        User user = (User) req.getSession().getAttribute("user");
        if ((user != null) && (user.getRole() != null)) {
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
        }
        return userMain;

    }
}
