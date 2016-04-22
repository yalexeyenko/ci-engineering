package validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
        regexMap.put("current_password", "^[A-Za-z0-9!@#$%^&*()_]{6,20}$");
        regexMap.put("repeatPassword", "^[A-Za-z0-9!@#$%^&*()_]{6,20}$");
    }

    public Validator() {
    }

    public Set<Violation> validate(Map<String, String[]> parameterMap) {
        log.debug("validate...");

        Set<Violation> violations = new HashSet<>();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {

            String value = entry.getValue()[0];
            String key = entry.getKey();
            String regex = regexMap.get(key);

            log.debug("key: {}", key);
            log.debug("value: {}", value);
            log.debug("regex: {}", regex);

//            log.debug("repeatPassword.equals(key): {}", "repeatPassword".equals(key));

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
                Violation violation = new Violation();
                violation.setName(key + "Violation");
                violation.setViolation("Please specify a valid " + key);
                violations.add(violation);
            }
        }
        return violations;
    }
}
