package action;

import entity.FileDoc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.FileDocService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class DeleteFileAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(DeleteFileAction.class);

    private ActionResult returnPage = new ActionResult("view-project-files");
    private ActionResult viewModuleFilesPage = new ActionResult("view-module-files");

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        log.debug("execute()...");

        String fileDocId = req.getParameter("fileDocId");
        String projectId = req.getParameter("projectId");
        log.debug("fileDocId: {}", fileDocId);
        log.debug("projectId: {}", projectId);

        // from module
        String moduleId = req.getParameter("moduleId");
        String sender = req.getParameter("sender");

        boolean deleted;
        List<FileDoc> fileDocs;

        if (sender != null) {
            try (FileDocService fileDocService = new FileDocService()) {
                deleted = fileDocService.deleteFileDoc(Integer.parseInt(fileDocId));
                fileDocs = fileDocService.findAllFileDocsByModuleId(Integer.valueOf(moduleId));
            } catch (Exception e) {
                throw new ActionException("Failed to deleteFileDoc()");
            }
        } else {
            try (FileDocService fileDocService = new FileDocService()) {
                deleted = fileDocService.deleteFileDoc(Integer.parseInt(fileDocId));
                fileDocs = fileDocService.findAllFileDocsByProjectId(Integer.valueOf(projectId));
            } catch (Exception e) {
                throw new ActionException("Failed to deleteFileDoc()");
            }
        }

        if (!deleted) {
            req.setAttribute("removalFailure", "Failed to remove file.");
            req.setAttribute("projectId", projectId);
            req.setAttribute("fileDocs", fileDocs);
            log.debug("Failed to remove file.");

            if ((sender != null) && sender.equals("module-sender")) {
                if (projectId != null) {
                    req.setAttribute("projectId", projectId);
                }
                if (moduleId != null) {
                    req.setAttribute("moduleId", moduleId);
                }
                return viewModuleFilesPage;
            }
            return returnPage;
        } else {
            req.setAttribute("removalSuccess", "File successfully removed");
            req.setAttribute("projectId", projectId);
            req.setAttribute("fileDocs", fileDocs);
            log.debug("File successfully removed");

            if ((sender != null) && sender.equals("module-sender")) {
                if (moduleId != null) {
                    req.setAttribute("moduleId", moduleId);
                }
                if (projectId != null) {
                    req.setAttribute("projectId", projectId);
                }
                return viewModuleFilesPage;
            }
            return returnPage;
        }
    }
}
