package action;

import entity.FileDoc;
import entity.Project;
import entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.FileDocService;
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
import java.util.HashSet;
import java.util.Set;

public class UploadFileAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(UploadFileAction.class);

    private Validator validator;

    private ActionResult uploadAgain = new ActionResult("add-file");
    private ActionResult uploadSuccess = new ActionResult("add-file");

    public UploadFileAction() {
        validator = new Validator();
    }

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String description = req.getParameter("description");
        Part filePart = req.getPart("file");
        String fileName = filePart.getSubmittedFileName();
        InputStream fileContent = filePart.getInputStream();
        String staffId = req.getParameter("staffId");
        String projectId = req.getParameter("projectId");

        FileDoc fileDoc = new FileDoc();
        User user;
        Project project;
        fileDoc.setDescription(description);
        fileDoc.setFileContent(fileContent);

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
            return uploadAgain;
        }


        try (UserService userService = new UserService()) {
            log.debug("findUserById()");
            user = userService.findUserById(Integer.valueOf(staffId));
        } catch (Exception e) {
            log.debug("Failed to findUserById()");
            throw new ActionException("Failed to close findUserById", e);
        }
        //noinspection Duplicates todo
        try (ProjectService projectService = new ProjectService()) {
            log.debug("findProjectById()");
            project = projectService.findProjectById(Integer.valueOf(projectId));
        } catch (Exception e) {
            log.debug("Failed to findProjectById()");
            throw new ActionException("Failed to close findProjectById", e);
        }

        fileDoc.setUser(user);
        fileDoc.setProject(project);
        log.debug("fileDoc.getId(): {}",fileDoc.getId());
        log.debug("fileDoc.getUser(): {}",fileDoc.getUser());
        log.debug("fileDoc.getDescription(): {}",fileDoc.getDescription());
        log.debug("fileDoc.getLastModified(): {}",fileDoc.getLastModified());
        log.debug("fileDoc.getStatus(: {}",fileDoc.getStatus());
        log.debug("fileDoc.getModule(): {}",fileDoc.getModule());
        log.debug("fileDoc.getProject(): {}",fileDoc.getProject());
        log.debug("fileDoc.getFileContent(): {}",fileDoc.getFileContent());

        try (FileDocService fileDocService = new FileDocService()) {
            log.debug("createFileDoc()");
            fileDoc = fileDocService.createFileDoc(fileDoc);
        } catch (Exception e) {
            log.debug("Failed to createFileDoc()");
            throw new ActionException("Failed to createFileDoc", e);
        }

        req.setAttribute("fileDoc", fileDoc);
        req.setAttribute("projectId", projectId);
        req.setAttribute("fileDocDescription", description);
        req.setAttribute("uploadFileSuccess", "File successfully created.");
        return uploadSuccess;

    }
}
