package validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.joda.time.LocalDate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Validator {
    private static final Logger log = LoggerFactory.getLogger(Validator.class);

    private static Map<String, String> regexMap = new HashMap<>();

    static {
        regexMap.put("firstName", "[A-Za-z0-9]{3,20}$");
        regexMap.put("lastName", "[A-Za-z0-9]{3,20}$");
        regexMap.put("degree", "[A-Za-z 0-9]{3,60}$");
        regexMap.put("role", "[A-Za-z]{3,20}$");
        regexMap.put("email", "^([\\w-]+(?:\\.[\\w-]+)*)@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)$");

        regexMap.put("password", "^[A-Za-z0-9!@#$%^&*()_]{6,20}$");
        regexMap.put("current-password", "^[A-Za-z0-9!@#$%^&*()_]{6,20}$");
        regexMap.put("repeatPassword", "^[A-Za-z0-9!@#$%^&*()_]{6,20}$");

        regexMap.put("projectName", "[A-Za-z 0-9]{3,60}$");

    }

    public Validator() {
    }

    public Set<Violation> validateMainUserInfoInput(Map<String, String[]> parameterMap) {
        log.debug("validateMainUserInfoInput...");
        Set<Violation> violations = new HashSet<>();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String value = entry.getValue()[0];
            String key = entry.getKey();
            String regex = null;
            if (regexMap.containsKey(key)) {
                regex = regexMap.get(key);
            } else {
                continue;
            }
            log.debug("key: {}", key);
            log.debug("value: {}", value);
            log.debug("regex: {}", regex);
            if (!value.matches(regex)) {
                addViolation(key, violations);
            }
        }
        return violations;
    }

    public Set<Violation> validatePassword(Map<String, String[]> parameterMap) {
        log.debug("validatePassword()...");
        Set<Violation> violations = new HashSet<>();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String value = entry.getValue()[0];
            String key = entry.getKey();
            String regex = regexMap.get(key);
            log.debug("key: {}", key);
            log.debug("value: {}", value);
            log.debug("regex: {}", regex);
            if (key.equals("repeatPassword")) {
                log.debug("password: {}", parameterMap.get("password")[0]);
                log.debug("repeatPassword: {}", value);
                if (!(value.equals(parameterMap.get("password")[0]))) {
                    Violation violation = new Violation();
                    violation.setName(key + "Violation");
                    violation.setViolation("Password mismatch");
                    violations.add(violation);
                }
                continue;
            }
            if (!value.matches(regex)) {
                addViolation(key, violations);
            }
        }
        return violations;
    }

    public Set<Violation> validateProjectCreation(Map<String, String[]> parameterMap) {
        log.debug("validateProjectCreation()...");
        Set<Violation> violations = new HashSet<>();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue()[0];
            String regex;
            if (key.equals("projectName")) {
                if (regexMap.containsKey(key)) {
                    regex = regexMap.get(key);
                    if (!value.matches(regex)) {
                        addViolation(key, violations, "Please specify a valid project name.");
                    }
                }
            } else if (key.equals("projectDeadline")) {
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
        Violation violation = new Violation();
        violation.setName(key + "Violation");
        violation.setViolation("Please specify a valid " + key);
        violations.add(violation);
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
