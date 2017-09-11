package action;

import entity.FileDoc;
import entity.Module;
import entity.Project;
import entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.FileDocService;
import service.ModuleService;
import service.ProjectService;
import service.UserService;
import validator.Validator;
import validator.Violation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

public class UploadFileAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(UploadFileAction.class);

    private Validator validator;

    private ActionResult returnPage = new ActionResult("add-file");
    private ActionResult viewAddModuleFilePage = new ActionResult("add-module-file");

    public UploadFileAction() {
        validator = new Validator();
    }

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Part filePart = req.getPart("file");
        String description = req.getParameter("description");
        String fileName = filePart.getSubmittedFileName();
        InputStream fileContent = filePart.getInputStream();
        String staffId = req.getParameter("staffId");
        String projectId = req.getParameter("projectId");

        // from module
        String moduleId = req.getParameter("moduleId");
        String sender = req.getParameter("sender");

        FileDoc fileDoc = new FileDoc();
        User user;
        Project project;
        Module module;
        fileDoc.setDescription(description);
        fileDoc.setFileContent(fileContent);
        fileDoc.setName(fileName);

        Set<Violation> violations = validator.validateFileUpload(description, filePart);
        log.debug("violations.size(): {}", violations.size());
        if(!violations.isEmpty()) {
            for (Violation violation : violations) {
                req.setAttribute(violation.getName(), violation.getViolation());
                log.debug("violation.getName(): {}", violation.getName());
                log.debug("violation.getViolation(): {}", violation.getViolation());
            }
            req.setAttribute("projectId", projectId);
            req.setAttribute("description", description);
            if (sender != null) {
                req.setAttribute("moduleId", moduleId);
                return viewAddModuleFilePage;
            }
            return returnPage;
        }

        try (UserService userService = new UserService()) {
            log.debug("findUserById()");
            user = userService.findUserById(Integer.valueOf(staffId));
        } catch (Exception e) {
            throw new ActionException("Failed to close findUserById", e);
        }
        try (ProjectService projectService = new ProjectService()) {
            log.debug("findProjectById()");
            project = projectService.findProjectById(Integer.valueOf(projectId));
        } catch (Exception e) {
            throw new ActionException("Failed to close findProjectById", e);
        }

        if (sender != null) {
            try (ModuleService moduleService = new ModuleService()) {
                log.debug("findModuleById()");
                module = moduleService.findModuleById(Integer.valueOf(moduleId));
            } catch (Exception e) {
                throw new ActionException("Failed to close findModuleById", e);
            }
            fileDoc.setModule(module);
        }


        fileDoc.setUser(user);
        fileDoc.setProject(project);
        log.debug("fileDoc.getId(): {}",fileDoc.getId());
        log.debug("fileDoc.getUser(): {}",fileDoc.getUser());
        log.debug("fileDoc.getDescription(): {}",fileDoc.getDescription());
        log.debug("fileDoc.getLastModified(): {}",fileDoc.getLastModified());
        log.debug("fileDoc.getStatus(: {}",fileDoc.getStatus());
        log.debug("fileDoc.getProject(): {}",fileDoc.getProject());
        log.debug("fileDoc.getFileContent(): {}",fileDoc.getFileContent());

        try (FileDocService fileDocService = new FileDocService()) {
            log.debug("createFileDoc()");
            fileDoc = fileDocService.createFileDoc(fileDoc);
        } catch (Exception e) {
            throw new ActionException("Failed to createFileDoc", e);
        }

        req.setAttribute("fileDoc", fileDoc);
        req.setAttribute("projectId", projectId);
        req.setAttribute("fileDocDescription", description);
        req.setAttribute("uploadFileSuccess", "Файл успешно добавлен");

        if ((sender != null) && sender.equals("module-sender")) {
            req.setAttribute("moduleId", moduleId);
            return viewAddModuleFilePage;
        }

        return returnPage;
    }
}
