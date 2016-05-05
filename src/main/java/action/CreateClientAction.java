package action;

import entity.Client;
import entity.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ClientService;
import service.ProjectService;
import validator.Validator;
import validator.Violation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

public class CreateClientAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(CreateClientAction.class);

    private Validator validator;

    private ActionResult pageReturn = new ActionResult("create-client");

    public CreateClientAction() {
        validator = new Validator();
    }

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        String nameFirstName = req.getParameter("nameFirstName");
        String fullNameLastName = req.getParameter("fullNameLastName");
        String clientEmail = req.getParameter("clientEmail");
        String clientCountry = req.getParameter("clientCountry");
        String clientCity = req.getParameter("clientCity");
        String clientAddress = req.getParameter("clientAddress");
        String clientTelephone = req.getParameter("clientTelephone");
        String clientBankAccountNumber = req.getParameter("clientBankAccountNumber");
        String clientEinSsn = req.getParameter("clientEinSsn");
        String clientType = req.getParameter("clientType");

        String projectId = req.getParameter("projectId");

        Map<String, String[]> parameterMap = req.getParameterMap();
        Set<Violation> violations = validator.validateClientInfo(parameterMap);
        log.debug("violations.size(): {}", violations.size());

        if (!violations.isEmpty()) {
            for (Violation violation : violations) {
                req.setAttribute(violation.getName(), violation.getViolation());
            }
            req.setAttribute("nameFirstName", nameFirstName);
            req.setAttribute("fullNameLastName", fullNameLastName);
            req.setAttribute("clientEmail", clientEmail);
            req.setAttribute("clientCountry", clientCountry);
            req.setAttribute("clientCity", clientCity);
            req.setAttribute("clientAddress", clientAddress);
            req.setAttribute("clientTelephone", clientTelephone);
            req.setAttribute("clientBankAccountNumber", clientBankAccountNumber);
            req.setAttribute("clientEinSsn", clientEinSsn);
            req.setAttribute("clientType", clientType);
            log.debug("clientType: {}", clientType);
            req.setAttribute("projectId", projectId);
            req.setAttribute("countriesMap", getCountries());
            return pageReturn;
        }

        Client currentClient = new Client();
        Project currentProject;

        currentClient.setFirstName(nameFirstName);
        currentClient.setLastName(fullNameLastName);
        currentClient.setEmail(clientEmail);
        currentClient.setCountry(clientCountry);
        currentClient.setCity(clientCity);
        currentClient.setAddress(clientAddress);
        currentClient.setTelephone(clientTelephone);
        currentClient.setBankAccountNumber(clientBankAccountNumber);
        currentClient.setEinSsn(clientEinSsn);
        currentClient.setClientType(Client.ClientType.valueOf(clientType));

        try (ClientService clientService = new ClientService()) {
            currentClient = clientService.createClient(currentClient);
        } catch (Exception e) {
            throw new ActionException("Failed to close createClient()", e);
        }

        try (ProjectService projectService = new ProjectService()) {
            currentProject = projectService.findProjectById(Integer.valueOf(projectId));
        } catch (Exception e) {
            throw new ActionException("Failed to close findProjectById()", e);
        }

        currentProject.setClient(currentClient);

        try (ProjectService projectService = new ProjectService()) {
            projectService.updateProjectClient(currentProject);
        } catch (Exception e) {
            throw new ActionException("Failed to close updateProjectClient()", e);
        }

        req.setAttribute("nameFirstName", nameFirstName);
        req.setAttribute("fullNameLastName", fullNameLastName);
        req.setAttribute("clientEmail", clientEmail);
        req.setAttribute("clientCountry", clientCountry);
        req.setAttribute("clientCity", clientCity);
        req.setAttribute("clientAddress", clientAddress);
        req.setAttribute("clientTelephone", clientTelephone);
        req.setAttribute("clientBankAccountNumber", clientBankAccountNumber);
        req.setAttribute("clientEinSsn", clientEinSsn);
        req.setAttribute("clientType", clientType);
        req.setAttribute("projectId", projectId);
        req.setAttribute("project", currentProject);
        req.setAttribute("countriesMap", getCountries());
        req.setAttribute("clientCreated", "Client was successfully created.");

        return pageReturn;
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
