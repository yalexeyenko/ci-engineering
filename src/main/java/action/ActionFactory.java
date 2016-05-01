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
        actions.put("GET/admin-view-user", new ShowPageAction("admin-view-user"));
        actions.put("GET/pass-userId", new PassUserIdAction());
        actions.put("POST/change-role", new ChangeRoleAction());

        actions.put("GET/manager-content-projects", new ManagerContentProjectsAction());
        actions.put("GET/manager-content-action", new ManagerContentAction());

        actions.put("GET/manager-main", new ShowPageAction("manager-main"));
        actions.put("GET/user-main", new ShowPageAction("user-main"));
        actions.put("GET/engineer-main", new ShowPageAction("engineer-main"));
        actions.put("GET/senior-main", new ShowPageAction("senior-main"));

        actions.put("POST/signIn", new SignInAction());
        actions.put("POST/signUp", new SignUpAction());
        actions.put("GET/signOut", new SignOutAction());

        actions.put("GET/view-profile", new ShowPageAction("view-profile"));
        actions.put("GET/edit-profile", new ShowPageAction("edit-profile"));
        actions.put("POST/edit-main-profile-info", new EditMainProfileInfoAction());
        actions.put("POST/change-password", new ChangePasswordAction());

        actions.put("GET/create-project", new ShowPageAction("create-project"));
        actions.put("POST/createProject", new CreateProjectAction());

        actions.put("GET/pass-projectId", new PassProjectIdAction());
        actions.put("GET/view-project", new ShowPageAction("view-project"));
        actions.put("GET/edit-main-project-info", new ShowPageAction("edit-main-project-info"));
        actions.put("POST/edit-main-project-info", new EditMainProjectInfoAction());

        actions.put("GET/create-client", new ShowPageAction("create-client"));
        actions.put("POST/createClientAction", new CreateClientAction());

        actions.put("GET/view-client", new ShowPageAction("view-client"));
        actions.put("GET/edit-client", new ShowPageAction("edit-client"));
        actions.put("POST/editClientAction", new EditClientAction());

        actions.put("GET/specify-senior", new ShowPageAction("specify-senior"));
        actions.put("POST/specifySeniorAction", new SpecifySeniorAction());
    }

    public Action getAction(String actionName) {
        return actions.get(actionName);
    }
}