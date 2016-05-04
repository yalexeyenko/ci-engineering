package validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.joda.time.LocalDate;

import javax.servlet.http.Part;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Validator {
    private static final Logger log = LoggerFactory.getLogger(Validator.class);

    private static Map<String, String> regexMap = new HashMap<>();

    static {
        regexMap.put("userFirstName", "[A-Za-zА-Яа-я0-9]{3,20}$");
        regexMap.put("userLastName", "[A-Za-zА-Яа-я0-9]{3,20}$");
        regexMap.put("userDegree", "[A-Za-zА-Яа-я 0-9]{3,60}$");
        regexMap.put("userRole", "[A-Za-z]{3,20}$");
        regexMap.put("userEmail", "^([\\w-]+(?:\\.[\\w-]+)*)@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)$");

        regexMap.put("password", "^[A-Za-z0-9!@#$%^&*()_]{6,20}$");
        regexMap.put("current-password", "^[A-Za-z0-9!@#$%^&*()_]{6,20}$");
        regexMap.put("repeatPassword", "^[A-Za-z0-9!@#$%^&*()_]{6,20}$");

        regexMap.put("projectName", "[A-Za-z 0-9]{3,60}$");

        regexMap.put("nameFirstName", "[A-Za-zА-Яа-я 0-9]{3,60}$");
        regexMap.put("fullNameLastName", "[A-Za-zА-Яа-я 0-9]{3,60}$");
        regexMap.put("clientEmail", "^([\\w-]+(?:\\.[\\w-]+)*)@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)$");
        regexMap.put("clientCountry", "[A-Za-zА-Яа-я]{2}$");
        regexMap.put("clientCity", "[A-Za-zА-Яа-я ]{3,60}$");
        regexMap.put("clientAddress", "[A-Za-zА-Яа-я 0-9]{3,60}$");
        regexMap.put("clientTelephone", "^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$");
        regexMap.put("clientBankAccountNumber", "KZ[0-9]{18}");
        regexMap.put("clientEinSsn", "^\\d{3}-\\d{2}-\\d{4}$");
        regexMap.put("clientType", "[A-Za-z]{3,20}$");

        regexMap.put("description", "[A-Za-zА-Яа-я 0-9]{3,60}$");

    }

    public Validator() {
    }

    public Set<Violation> validateMainUserInfoInput(Map<String, String[]> parameterMap) {
        log.debug("validateMainUserInfoInput...");
        Set<Violation> violations = new HashSet<>();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String value = entry.getValue()[0];
            String key = entry.getKey();
            String regex;
            if (key.equals("password") || key.equals("repeatPassword")) continue;
            if (regexMap.containsKey(key)) {
                regex = regexMap.get(key);
            } else {
                continue;
            }
            log.debug("key: {}", key);
            log.debug("value: {}", value);
            log.debug("regex: {}", regex);
            if (!value.matches(regex)) {
                if (key.equals("userFirstName")) {
                    addViolation(key, violations, "Specify valid first name.");
                } else if (key.equals("userLastName")) {
                    addViolation(key, violations, "Specify valid last name.");
                } else if (key.equals("userEmail")) {
                    addViolation(key, violations, "Specify valid email.");
                }
            }
        }
        return violations;
    }

    public Set<Violation> validatePassword(String password, String repeatPassword) {
        log.debug("validatePassword()...");
        Set<Violation> violations = new HashSet<>();
        if (!password.matches(regexMap.get("password"))) {
            addViolation("password", violations, "Invalid password.");
        }
        if (!repeatPassword.matches(regexMap.get("repeatPassword"))) {
            addViolation("repeatPassword", violations, "Specify valid password.");
        }
        if (!password.equals(repeatPassword)) {
            addViolation("mismatch", violations, "Password mismatch.");
        }
        return violations;
    }

    public Set<Violation> validateProjectInfo(Map<String, String[]> parameterMap) {
        log.debug("validateProjectInfo()...");
        Set<Violation> violations = new HashSet<>();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            if (entry.getKey().equals("projectId") || entry.getKey().equals("projectFinished")) {
                continue;
            }
            String key = entry.getKey();
            String value = entry.getValue()[0];
            String regex;
            if (key.equals("projectName")) {
                if (regexMap.containsKey(key)) {
                    regex = regexMap.get(key);
                    if (!value.matches(regex)) {
                        log.debug(value + " matches " + regex + ": {}", value.matches(regex));
                        addViolation(key, violations, "Please specify a valid project name.");
                    }
                }
            } else if (key.equals("projectDeadline") || key.equals("projectStartDate")) {
                if (!validateDate(value)) {
                    addViolation(key, violations, "Please specify a valid date.");
                } else if (validateDate(value)) {
                    if (!validateDateRange(value)) {
                        addViolation(key, violations, "Verify your date.");
                    }
                }
            }
        }
        return violations;
    }

    public Set<Violation> validateClientInfo(Map<String, String[]> parameterMap) {
        log.debug("validateClientInfo()...");
        Set<Violation> violations = new HashSet<>();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            if (entry.getKey().equals("projectId") || entry.getKey().equals("clientId")) {
                continue;
            }
            String value = entry.getValue()[0];
            String key = entry.getKey();
            String regex;
            if (regexMap.containsKey(key)) {
                regex = regexMap.get(key);
            } else {
                continue;
            }
            log.debug("key: {}", key);
            log.debug("value: {}", value);
            log.debug("regex: {}", regex);
            if (!value.matches(regex)) {
                if (key.equals("nameFirstName")) {
                    addViolation(key, violations, "Specify valid first name.");
                } else if (key.equals("fullNameLastName")) {
                    addViolation(key, violations, "Specify valid last name.");
                } else if (key.equals("clientEmail")) {
                    addViolation(key, violations, "Specify valid email.");
                } else if (key.equals("clientCountry")) {
                    addViolation(key, violations, "Specify valid country.");
                } else if (key.equals("clientCity")) {
                    addViolation(key, violations, "Specify valid city.");
                } else if (key.equals("clientAddress")) {
                    addViolation(key, violations, "Specify valid address.");
                } else if (key.equals("clientTelephone")) {
                    addViolation(key, violations, "Specify valid telephone.");
                } else if (key.equals("clientBankAccountNumber")) {
                    addViolation(key, violations, "Specify valid bank account number.");
                } else if (key.equals("clientEinSsn")) {
                    addViolation(key, violations, "Specify valid EIN/SSN.");
                } else if (key.equals("clientType")) {
                    addViolation(key, violations, "Specify valid client type.");
                }
            }
        }
        return violations;
    }

    public Set<Violation> validateFileUpload(String description, Part filePart) {
        log.debug("validateClientInfo()...");
        log.debug("description: {}", description);
        log.debug("filePart.getSize(): {}", filePart.getSize());
        Set<Violation> violations = new HashSet<>();
        if (!description.matches(regexMap.get("description"))) {
            addViolation("description", violations, "Please specify a valid description.");
        }
        if (filePart.getSize() > 4194304L) {
            addViolation("filePartSize", violations, "File must be less than 4MB.");
        }
        return violations;
    }

    private boolean validateDateRange(String value) {
        LocalDate recivedDate = new LocalDate(value);
        LocalDate minDate = new LocalDate();
        LocalDate maxDate = new LocalDate("2050-01-01");
        if (recivedDate.isBefore(minDate) || recivedDate.isAfter(maxDate)) {
            return false;
        }
        return true;
    }

    public void addViolation(String key, Set<Violation> violations) {
        addViolation(key, violations, "Please specify a valid " + key);
    }

    public void addViolation(String key, Set<Violation> violations, String violationString) {
        Violation violation = new Violation();
        violation.setName(key + "Violation");
        violation.setViolation(violationString);
        violations.add(violation);
    }

    public boolean validateDate(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try {
            Date date = sdf.parse(dateString);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
