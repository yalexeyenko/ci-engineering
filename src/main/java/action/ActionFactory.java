package action;

import java.util.HashMap;
import java.util.Map;

public class ActionFactory {
    private Map<String, Action> actions;

    public ActionFactory() {
        this.actions = new HashMap<>();

        actions.put("GET/welcome", new ShowPageAction("welcome"));

        actions.put("GET/main-page", new StaffMainPageAction());
        actions.put("GET/admin-content-action", new AdminContentAction());
        actions.put("GET/admin-content-staff", new AdminContentStaffAction());


        actions.put("GET/manager-main", new ShowPageAction("manager-main"));
        actions.put("GET/manager-content-action", new ManagerContentAction());
        actions.put("GET/manager-content-projects", new ManagerContentProjectsAction());

        actions.put("GET/user-main", new ShowPageAction("user-main"));
        actions.put("GET/engineer-main", new ShowPageAction("engineer-main"));
        actions.put("GET/senior-main", new ShowPageAction("senior-main"));

        actions.put("POST/signIn", new SignInAction());
        actions.put("POST/signUp", new SignUpAction());
        actions.put("GET/signOut", new SignOutAction());

        actions.put("POST/editProfile", new EditProfileAction());
        actions.put("GET/edit-profile", new ShowPageAction("edit-profile"));

        actions.put("GET/create-project", new ShowPageAction("create-project"));
        actions.put("POST/createProject", new CreateProjectAction());
    }

    public Action getAction(String actionName) {
        return actions.get(actionName);
    }
}