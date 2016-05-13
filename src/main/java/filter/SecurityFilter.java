package filter;

import entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebFilter(filterName = "SecurityFilter", urlPatterns = "/do/*")
public class SecurityFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(SecurityFilter.class);
    private static final String WELCOME = "/WEB-INF/jsp/welcome.jsp";
    private static final String FORBIDDEN = "/WEB-INF/jsp/forbidden.jsp";

    private Map<User.Role, List<String>> accessMap;

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        doFilter((HttpServletRequest) req, (HttpServletResponse) resp, chain);
    }

    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        User.Role role;
        try {
            User user = (User) request.getSession(false).getAttribute("user");
            role = user.getRole();
            if (role == null) {
                throw new FilterException();
            }
        } catch (Exception e) {
            role = User.Role.UNREGISTERED;
        }
        log.debug("role: {}", role.name());
        String pathInfo = request.getPathInfo();
        if (checkAccess(pathInfo, role)) {
            log.debug("checkAccess(): true");
            chain.doFilter(request, response);
        } else {
            if (role == User.Role.UNREGISTERED) {
                log.debug("checkAccess(): false, forward: WELCOME");
                request.getRequestDispatcher(WELCOME).forward(request, response);
            } else {
                log.debug("checkAccess(): false, forward: FORBIDDEN");
                request.getRequestDispatcher(FORBIDDEN).forward(request, response);
            }
        }

    }

    public void init(FilterConfig config) throws ServletException {
        accessMap = new HashMap<>();

        List<String> managerPaths = new ArrayList<>();
        managerPaths.add("/main-page");
        managerPaths.add("/pass-userId");
        managerPaths.add("/manager-content-projects");
        managerPaths.add("/manager-content-action");
        managerPaths.add("/manager-main");
        managerPaths.add("/signOut");
        managerPaths.add("/view-profile");
        managerPaths.add("/edit-profile");
        managerPaths.add("/edit-main-profile-info");
        managerPaths.add("/change-password");
        managerPaths.add("/create-project");
        managerPaths.add("/createProject");
        managerPaths.add("/upload");
        managerPaths.add("/download");
        managerPaths.add("/delete-file");
        managerPaths.add("/view-project-files");
        managerPaths.add("/view-project-modules");
        managerPaths.add("/view-module");
        managerPaths.add("/edit-main-module-info");
        managerPaths.add("/view-module-files");
        managerPaths.add("/add-module-file");
        managerPaths.add("/add-module");
        managerPaths.add("/create-module");
        managerPaths.add("/pass-projectId");
        managerPaths.add("/view-project");
        managerPaths.add("/edit-main-project-info");
        managerPaths.add("/add-file");
        managerPaths.add("/edit-main-project-info-post");
        managerPaths.add("/create-client");
        managerPaths.add("/createClientAction");
        managerPaths.add("/view-client");
        managerPaths.add("/edit-client");
        managerPaths.add("/editClientAction");
        managerPaths.add("/specify-senior");
        managerPaths.add("/specifySeniorAction");
        accessMap.put(User.Role.MANAGER, managerPaths);

        List<String> seniorPaths = new ArrayList<>();
        seniorPaths.add("/main-page");
        seniorPaths.add("/pass-userId");
        seniorPaths.add("/senior-main");
        seniorPaths.add("/signOut");
        seniorPaths.add("/view-profile");
        seniorPaths.add("/edit-profile");
        seniorPaths.add("/edit-main-profile-info");
        seniorPaths.add("/change-password");
        accessMap.put(User.Role.SENIOR, seniorPaths);

        List<String> adminPaths = new ArrayList<>();
        adminPaths.add("/main-page");//
        adminPaths.add("/admin-content-action");
        adminPaths.add("/admin-content-staff");
        adminPaths.add("/admin-view-user");
        adminPaths.add("/change-role");
        adminPaths.add("/pass-userId");//
        adminPaths.add("/signOut");//
        adminPaths.add("/view-profile");//
        adminPaths.add("/edit-profile");//
        adminPaths.add("/edit-main-profile-info");//
        adminPaths.add("/change-password");//
        accessMap.put(User.Role.ADMIN, adminPaths);

        List<String> engineerPaths = new ArrayList<>();
        engineerPaths.add("/engineer-main");
        engineerPaths.add("/main-page");//
        engineerPaths.add("/pass-userId");//
        engineerPaths.add("/signOut");//
        engineerPaths.add("/view-profile");//
        engineerPaths.add("/edit-profile");//
        engineerPaths.add("/edit-main-profile-info");//
        engineerPaths.add("/change-password");//
        accessMap.put(User.Role.ENGINEER, engineerPaths);

        List<String> registeredPaths = new ArrayList<>();
        registeredPaths.add("/user-main");
        registeredPaths.add("/main-page");//
        registeredPaths.add("/pass-userId");//
        registeredPaths.add("/signOut");//
        registeredPaths.add("/view-profile");//
        registeredPaths.add("/edit-profile");//
        registeredPaths.add("/edit-main-profile-info");//
        registeredPaths.add("/change-password");//
        accessMap.put(User.Role.REGISTERED, registeredPaths);

        List<String> unregisteredPaths = new ArrayList<>();
        unregisteredPaths.add("/welcome");
        unregisteredPaths.add("/signIn");
        unregisteredPaths.add("/signUp");
        accessMap.put(User.Role.UNREGISTERED, unregisteredPaths);
    }

    private boolean checkAccess(String pathInfo, User.Role role) {
        log.debug("pathInfo: {}", pathInfo);
        log.debug("role: {}", role.name());
        List<String> paths = accessMap.get(role);
        log.debug("paths: {}", paths);
        return paths.contains(pathInfo);
    }

}
