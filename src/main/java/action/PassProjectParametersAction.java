package action;

import entity.Project;
import entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ProjectService;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class PassProjectParametersAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(PassProjectParametersAction.class);

    private ActionResult viewProject = new ActionResult("view-project");
    private ActionResult editMainProjectInfo = new ActionResult("edit-main-project-info");
    private ActionResult addFile = new ActionResult("add-file");
    private ActionResult createClient = new ActionResult("create-client");
    private ActionResult viewClient = new ActionResult("view-client");
    private ActionResult editClient = new ActionResult("edit-client");
    private ActionResult specifySenior = new ActionResult("specify-senior");

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        log.debug("execute...");
        String projectId = req.getParameter("projectId");
        String passParam = req.getParameter("passProjectId");
        log.debug("projectId: {}", projectId);
        log.debug("passParam: {}", passParam);


        Project project;

        try (ProjectService projectService = new ProjectService()) {
            log.debug("projectService created");
            project = projectService.findProjectById(Integer.valueOf(projectId));
            log.debug("project: {}", project);
        } catch (Exception e) {
            throw new ActionException("Failed to findProjectById", e);
        }

        req.setAttribute("project", project);
        req.setAttribute("projectId", project.getId());
        req.setAttribute("projectName", project.getName());
        req.setAttribute("projectStartDate", project.getStartDate());
        req.setAttribute("projectDeadline", project.getDeadline());
        req.setAttribute("projectFinished", project.isFinished());

        if (passParam.equalsIgnoreCase("view-project")) {
            return viewProject;
        } else if (passParam.equalsIgnoreCase("edit-main-project-info")) {
            return editMainProjectInfo;
        } else if (passParam.equalsIgnoreCase("add-file")) {
            return addFile;
        } else if (passParam.equalsIgnoreCase("create-client")) {
            req.setAttribute("countriesMap", getCountries());
            return createClient;
        } else if (passParam.equalsIgnoreCase("view-client")) {
            req.setAttribute("countriesMap", getCountries());
            return viewClient;
        } else if (passParam.equalsIgnoreCase("edit-client")) {
            req.setAttribute("nameFirstName", project.getClient().getFirstName());
            req.setAttribute("fullNameLastName", project.getClient().getLastName());
            req.setAttribute("clientEmail", project.getClient().getEmail());
            req.setAttribute("clientCountry", project.getClient().getCountry());
            req.setAttribute("clientCity", project.getClient().getCity());
            req.setAttribute("clientAddress", project.getClient().getAddress());
            req.setAttribute("clientTelephone", project.getClient().getTelephone());
            req.setAttribute("clientBankAccountNumber", project.getClient().getBankAccountNumber());
            req.setAttribute("clientEinSsn", project.getClient().getEinSsn());
            req.setAttribute("clientType", project.getClient().getClientType());
            req.setAttribute("clientId", project.getClient().getId());
            req.setAttribute("projectId", project.getId());
            req.setAttribute("countriesMap", getCountries());
            return editClient;
        } else if (passParam.equalsIgnoreCase("specify-senior")) {
            List<User> seniors;
            try (UserService userService = new UserService();) {
                log.debug("userService created");
                seniors = userService.findAllSeniors();
            } catch (Exception e) {
                throw new ActionException("Failed to findAllSeniors", e);
            }
            if (project.getSenior() != null) {
                req.setAttribute("projectSenior", project.getSenior());
                req.setAttribute("seniorFirstName", project.getSenior().getFirstName());
                req.setAttribute("seniorLastName", project.getSenior().getLastName());
                req.setAttribute("seniorId", project.getSenior().getId());
            }
            req.setAttribute("seniors", seniors);
            return specifySenior;
        } else {
            throw new ActionException("Failed to pass project parameters.");
        }
    }

    private Map<String, String> getCountries() {
        String[] locales = Locale.getISOCountries();
        Map<String, String> countriesMap = new TreeMap<>();
        for (String countryCode : locales) {
            Locale obj = new Locale("", countryCode);
            countriesMap.put(obj.getCountry(), obj.getDisplayCountry(Locale.US));
        }
        return countriesMap;
    }
}
