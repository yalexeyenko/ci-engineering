package action;

import dao.DaoException;
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
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class EditClientAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(EditClientAction.class);

    private Validator validator;

    private ActionResult editClientAgain = new ActionResult("edit-client");
    private ActionResult editClientSuccess = new ActionResult("edit-client");

    public EditClientAction() {
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
        String clientId = req.getParameter("clientId");

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
            req.setAttribute("clientEinSsn",clientEinSsn );
            req.setAttribute("clientType", clientType);
            req.setAttribute("projectId", projectId);
            req.setAttribute("clientId", clientId);
            req.setAttribute("countriesMap", getCountries());
            return editClientAgain;
        }

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

        ClientService clientService = new ClientService();

        try {
            clientService.updateClient(client);
        } catch (DaoException e) {
            log.debug("Failed to updateClient()");
            try {
                clientService.close();
            } catch (Exception ex) {
                throw new ActionException("Failed to close service", ex);
            }
            req.setAttribute("editClientInfoError", "Verify your data. Cannot edit client.");
            return editClientAgain;
        }

        req.setAttribute("nameFirstName", nameFirstName);
        req.setAttribute("fullNameLastName", fullNameLastName);
        req.setAttribute("clientEmail", clientEmail);
        req.setAttribute("clientCountry", clientCountry);
        req.setAttribute("clientCity", clientCity);
        req.setAttribute("clientAddress", clientAddress);
        req.setAttribute("clientTelephone", clientTelephone);
        req.setAttribute("clientBankAccountNumber", clientBankAccountNumber);
        req.setAttribute("clientEinSsn",clientEinSsn );
        req.setAttribute("clientType", clientType);
        req.setAttribute("projectId", projectId);
        req.setAttribute("countriesMap", getCountries());
        req.setAttribute("clientId", clientId);
        req.setAttribute("clientEdited", "Changes have been saved successfully.");

        try {
            clientService.close();
        } catch (Exception ex) {
            throw new ActionException("Failed to close service", ex);
        }
        return editClientSuccess;
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
