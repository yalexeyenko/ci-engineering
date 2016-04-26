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

public class EditClientAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(EditClientAction.class);

    private ActionResult editClientAgain = new ActionResult("editClientAction");
    private ActionResult viewClient = new ActionResult("view-client");

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
        String clientId = req.getParameter("clientId");

        Client client = new Client();
        client.setFirstName(nameFirstName);
        client.setLastName(fullNameLastName);
        client.setEmail(clientEmail);
        client.setCountry(clientCountry);
        client.setCity(clientCity);
        client.setAddress(clientAddress);
        client.setTelephone(clientTelephone);
        client.setBankAccountNumber(clientBankAccountNumber);
        client.setEinSsn(clientEinSsn);
        client.setClientType(Client.ClientType.valueOf(clientType));
        client.setId(Integer.valueOf(clientId));

        String projectId = req.getParameter("projectId");

        ProjectService projectService = new ProjectService();
        ClientService clientService = new ClientService();

        Project project;

        try {
            clientService.updateClient(client);
            project = projectService.findProjectById(Integer.valueOf(projectId));
        } catch (DaoException e) {
            try {
                projectService.close();
            } catch (Exception ex) {
                throw new ActionException("Failed to close service", ex);
            }
            req.setAttribute("editClientInfoError", "Verify your data. Cannot edit client.");
            return editClientAgain;
        }
        req.setAttribute("project", project);
        return viewClient;
    }
}
