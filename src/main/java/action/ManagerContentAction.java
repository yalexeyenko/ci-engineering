package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ManagerContentAction implements Action {
    private ActionResult managerContentProjects = new ActionResult("manager-content-projects", true);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        String menuButton = "managersButton";
        if (req.getParameter("menuButton") == null) {
            req.setAttribute("menuButton", menuButton);
        } else {
            menuButton = req.getParameter(menuButton);
        }
        switch (menuButton) {
            case "projects":
                return managerContentProjects;
            default:
                return managerContentProjects;
        }
    }
}
