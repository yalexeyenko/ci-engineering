package action;

import entity.FileDoc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.FileDocService;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class DownloadFileAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(DownloadFileAction.class);

    private Map<String, String> contentTypesMap;

    private ActionResult returnPage = new ActionResult("view-project-files");
    private ActionResult viewModuleFilesPage = new ActionResult("view-module-files");

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        log.debug("execute()...");
        String sender = req.getParameter("sender");
        String projectId = req.getParameter("projectId");
        String moduleId = req.getParameter("moduleId");

        if (contentTypesMap == null) {
            contentTypesMapInit();
        }
        String fileDocId = req.getParameter("fileDocId");
        log.debug("fileDocId: {}", fileDocId);
        FileDoc fileDoc;
        try (FileDocService fileDocService = new FileDocService()) {
            fileDoc = fileDocService.findById(Integer.parseInt(fileDocId));
        } catch (Exception e) {
            throw new ActionException("Failed to findById");
        }
        String fileName = fileDoc.getName();
        String extension = fileName.split("\\.")[1];
        String contentType = contentTypesMap.get(extension);
        log.debug("fileName: {}", fileName);
        log.debug("extension: {}", extension);
        log.debug("contentType: {}", contentType);

        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileDoc.getFileContent());
        ServletOutputStream servletOutputStream = resp.getOutputStream();

        resp.setContentType(contentType);
        resp.setHeader("Content-Disposition", "attachment; filename = " + URLEncoder.encode(fileName, "utf-8"));
        resp.setContentLength(bufferedInputStream.available());

        int bytesRead = bufferedInputStream.read();
        while (bytesRead != -1) {
            servletOutputStream.write(bytesRead);
            bytesRead = bufferedInputStream.read();
        }
        if (bufferedInputStream != null) {
            bufferedInputStream.close();
            if (servletOutputStream != null) servletOutputStream.close();
            req.setAttribute("downloadSuccess", "File successfully downloaded.");
            log.debug("download success. returnPage");

            if ((sender != null) && sender.equals("module-sender")) {
                if (projectId != null) req.setAttribute("projectId", projectId);
                if (moduleId != null) req.setAttribute("moduleId", moduleId);
                return viewModuleFilesPage;
            }
            return returnPage;
        } else {
            if (servletOutputStream != null) servletOutputStream.close();
            req.setAttribute("downloadFail", "Failed to download file.");

            if ((sender != null) && sender.equals("module-sender")) {
                if (moduleId != null) req.setAttribute("moduleId", moduleId);
                if (projectId != null) req.setAttribute("projectId", projectId);
                return viewModuleFilesPage;
            }
            return returnPage;
        }
    }

    private void contentTypesMapInit() {
        contentTypesMap = new HashMap();
        contentTypesMap.put("pdf", "application/pdf");
        contentTypesMap.put("doc", "application/msword");
        contentTypesMap.put("docx", "application/msword");
        contentTypesMap.put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        contentTypesMap.put("zip", "application/zip");
        contentTypesMap.put("dwg", "application/acad");
    }
}
