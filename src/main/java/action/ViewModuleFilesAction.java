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

public class ViewModuleFilesAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(ViewModuleFilesAction.class);

    private ActionResult viewModuleFilesPage = new ActionResult("view-module-files");

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        log.debug("execute...");
        String projectId = req.getParameter("projectId");
        String moduleId = req.getParameter("moduleId");
        List<FileDoc> fileDocs;

        try (FileDocService fileDocService = new FileDocService()) {
            fileDocs = fileDocService.findAllFileDocsByModuleId(Integer.valueOf(moduleId));
        } catch (Exception e) {
            throw new ActionException("Failed to findAllFileDocsByModuleId()", e);
        }

        req.setAttribute("fileDocs",  fileDocs);
        req.setAttribute("projectId", projectId);
        req.setAttribute("moduleId", moduleId);
        return viewModuleFilesPage;
    }
}
