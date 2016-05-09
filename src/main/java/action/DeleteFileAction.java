package action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.FileDocService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteFileAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(DeleteFileAction.class);

    private ActionResult returnPage = new ActionResult("view-project-files");

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        log.debug("execute()...");

        String fileDocId = req.getParameter("fileDocId");
        String projectId = req.getParameter("projectId");
        log.debug("fileDocId: {}", fileDocId);
        log.debug("projectId: {}", projectId);

        boolean deleted;

        try (FileDocService fileDocService = new FileDocService()) {
            deleted = fileDocService.deleteFileDoc(Integer.parseInt(fileDocId));
        } catch (Exception e) {
            throw new ActionException("Failed to deleteFileDoc()");
        }

        if (!deleted) {
            req.setAttribute("removalFailure", "Failed to remove file.");
            log.debug("Failed to remove file.");
            return returnPage;
        }
        else {
            req.setAttribute("removalSuccess", "File successfully removed");
            req.setAttribute("projectId", projectId);
            log.debug("File successfully removed");
            return returnPage;
        }
    }
}
