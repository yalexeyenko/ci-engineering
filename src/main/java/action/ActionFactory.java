package action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ActionFactory {
    private static final Logger log = LoggerFactory.getLogger(ActionFactory.class);

    private Map<String, Action> actions;

    public ActionFactory() {
        this.actions = new HashMap<>();

        actions.put("GET/welcome", new ShowPageAction("welcome"));//UNREGISTERED

        actions.put("GET/main-page", new StaffMainPageAction());//all but UNREGISTERED

        actions.put("GET/admin-content-staff", new AdminContentStaffAction());//ADMIN
        actions.put("GET/admin-view-user", new ShowPageAction("admin-view-user"));//ADMIN
        actions.put("POST/change-role", new ChangeRoleAction());//ADMIN

        actions.put("GET/pass-userId", new PassUserParametersAction());//all but UNREGISTERED

        actions.put("GET/manager-content-projects", new ManagerContentProjectsAction());//MANAGER
        actions.put("GET/manager-main", new ShowPageAction("manager-main"));//MANAGER

        actions.put("GET/user-main", new ShowPageAction("user-main"));//REGISTERED
        actions.put("GET/engineer-main", new ShowPageAction("engineer-main"));//ENGINEER
        actions.put("GET/senior-main", new ShowPageAction("senior-main"));//SENIOR

        actions.put("POST/signIn", new SignInAction());//UNREGISTERED
        actions.put("POST/signUp", new SignUpAction());//UNREGISTERED
        actions.put("GET/signOut", new SignOutAction());//all but UNREGISTERED

        actions.put("GET/view-profile", new ShowPageAction("view-profile"));//all but UNREGISTERED
        actions.put("GET/edit-profile", new ShowPageAction("edit-profile"));//all but UNREGISTERED
        actions.put("POST/edit-main-profile-info", new EditMainProfileInfoAction());//all but UNREGISTERED
        actions.put("POST/change-password", new ChangePasswordAction());//all but UNREGISTERED

        actions.put("GET/create-project", new ShowPageAction("create-project"));//MANAGER
        actions.put("POST/createProject", new CreateProjectAction());//MANAGER
        actions.put("POST/upload", new UploadFileAction());//MANAGER
        actions.put("GET/view-project-files", new ViewProjectFilesAction());

        actions.put("GET/pass-projectId", new PassProjectParametersAction());//MANAGER
        actions.put("POST/pass-projectId", new PassProjectParametersAction());//MANAGER
        actions.put("GET/view-project", new ShowPageAction("view-project"));//MANAGER
        actions.put("GET/edit-main-project-info", new ShowPageAction("edit-main-project-info"));//MANAGER
        actions.put("GET/add-file", new ShowPageAction("add-file"));//MANAGER
        actions.put("POST/edit-main-project-info-post", new EditMainProjectInfoAction());//MANAGER

        actions.put("GET/create-client", new ShowPageAction("create-client"));//MANAGER
        actions.put("POST/createClientAction", new CreateClientAction());//MANAGER

        actions.put("GET/view-client", new ShowPageAction("view-client"));//MANAGER
        actions.put("GET/edit-client", new ShowPageAction("edit-client"));//MANAGER
        actions.put("POST/editClientAction", new EditClientAction());//MANAGER

        actions.put("GET/specify-senior", new ShowPageAction("specify-senior"));//MANAGER
        actions.put("POST/specifySeniorAction", new SpecifySeniorAction());//MANAGER
    }

    public Action getAction(String actionName) {
        log.debug("getAction()...:{}", actionName);
        return actions.get(actionName);
    }
}