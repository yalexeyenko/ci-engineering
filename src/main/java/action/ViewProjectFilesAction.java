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

public class ViewProjectFilesAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(ViewProjectFilesAction.class);

    private ActionResult viewProjectFilesPage = new ActionResult("view-project-files");

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        log.debug("execute...");
        String projectId = req.getParameter("projectId");
        List<FileDoc> fileDocs;

        try (FileDocService fileDocService = new FileDocService()) {
            log.debug("findAllProjectFileDocs()");
            fileDocs = fileDocService.findAllFileDocsByProjectId(Integer.valueOf(projectId));
        } catch (Exception e) {
            throw new ActionException("Failed to close findAllProjectFileDocs", e);
        }

        req.setAttribute("fileDocs",  fileDocs);
        req.setAttribute("projectId", projectId);
        return viewProjectFilesPage;
    }
}
