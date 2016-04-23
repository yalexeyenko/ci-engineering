package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminContentAction implements Action {
    private ActionResult adminContentStaff = new ActionResult("admin-content-staff", true);
    private ActionResult adminContentProjects = new ActionResult("admin-main-projects", true);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        String menuButton = "usersButton";
        if (req.getParameter("menuButton") == null) {
            req.setAttribute("menuButton", menuButton);
        } else {
            menuButton = req.getParameter(menuButton);
        }
        switch (menuButton) {
            case "users":
                return adminContentStaff;
            case "projects":
                return adminContentProjects;
            default:
                return adminContentStaff;
        }
    }
}
