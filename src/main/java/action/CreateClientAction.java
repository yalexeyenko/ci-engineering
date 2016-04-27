package action;

import dao.DaoException;
import entity.Client;
import entity.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ClientService;
import service.ProjectService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateClientAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(CreateClientAction.class);

    private ActionResult createClientAgain = new ActionResult("createClientAction");
    private ActionResult viewProject = new ActionResult("view-project");

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

        log.debug("params...");
        log.debug(nameFirstName);
        log.debug(fullNameLastName);
        log.debug(clientEmail);
        log.debug(clientCountry);
        log.debug(clientCity);
        log.debug(clientAddress);
        log.debug(clientTelephone);
        log.debug(clientBankAccountNumber);
        log.debug(clientEinSsn);
        log.debug(projectId);
        log.debug(clientType);
        log.debug("...params");

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


        ProjectService projectService = new ProjectService();
        ClientService clientService = new ClientService();

        currentClient = clientService.createClient(currentClient);
        try {
            currentProject = projectService.findProjectById(Integer.valueOf(projectId));
        } catch (DaoException e) {
            try {
                projectService.close();
            } catch (Exception ex) {
                throw new ActionException("Failed to close service", ex);
            }
            req.setAttribute("createClientError", "Verify your data. Cannot create client");
            return createClientAgain;
        }

        currentProject.setClient(currentClient);
        try {
            projectService.updateProjectClient(currentProject);
        } catch (DaoException e) {
            log.debug("Failed to updateProjectClient()");
            try {
                projectService.close();
            } catch (Exception ex) {
                throw new ActionException("Failed to close service", ex);
            }
            req.setAttribute("addClientError", "Verify your data. Cannot add client to project.");
            return createClientAgain;
        }

        req.setAttribute("project", currentProject);
        return viewProject;
    }
}
