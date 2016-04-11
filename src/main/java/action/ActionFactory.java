package action;

import java.util.HashMap;
import java.util.Map;

public class ActionFactory {
    private Map<String, Action> actions;

    public ActionFactory() {
        this.actions = new HashMap<>();

        actions.put("GET/s", new ShowPageAction("signInUp"));
        actions.put("POST/signIn", new SignInAction());
        actions.put("POST/signUp", new SignUpAction());
    }

    public Action getAction(String actionName) {
        return actions.get(actionName);
    }
}
