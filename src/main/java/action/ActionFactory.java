package action;

import java.util.HashMap;
import java.util.Map;

public class ActionFactory {
    private Map<String, Action> actions;

    public ActionFactory() {
        this.actions = new HashMap<>();

        actions.put("GET/welcome", new ShowPageAction("welcome"));
        actions.put("GET/user-main", new ShowPageAction("user-main"));
        actions.put("GET/admin-main", new ShowPageAction("admin-main"));
        actions.put("GET/manager-main", new ShowPageAction("manager-main"));
        actions.put("GET/engineer-main", new ShowPageAction("engineer-main"));
        actions.put("GET/senior-main", new ShowPageAction("senior-main"));
        actions.put("POST/signIn", new SignInAction());
        actions.put("POST/signUp", new SignUpAction());
        actions.put("GET/signOut", new SignOutAction());
        actions.put("POST/editProfile", new EditProfileAction());
        actions.put("GET/edit-profile", new ShowPageAction("edit-profile"));
    }

    public Action getAction(String actionName) {
        return actions.get(actionName);
    }
}