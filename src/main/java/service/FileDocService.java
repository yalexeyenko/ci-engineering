package service;

import dao.DaoFactory;
import dao.FileDocDao;
import entity.FileDoc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class FileDocService implements AutoCloseable  {
    private static final Logger log = LoggerFactory.getLogger(FileDocService.class);

    private DaoFactory jdbcDaoFactory;
    private FileDocDao fileDocDao;

    public FileDocService() {
        jdbcDaoFactory = DaoFactory.newInstance(DaoFactory.JDBC);
        fileDocDao = (FileDocDao) jdbcDaoFactory.createDao(FileDoc.class);
    }

    public FileDoc createFileDoc(FileDoc fileDoc) {
        return fileDocDao.insert(fileDoc);
    }

    public FileDoc findById(int id) {
        return fileDocDao.findById(id);
    }

    public List<FileDoc> findAllFileDocs() {
        return fileDocDao.findAll();
    }

    public void updateFileDoc(FileDoc fileDoc) {
        fileDocDao.update(fileDoc);
    }

    public boolean deleteFileDoc(int id) {
        return fileDocDao.delete(id);
    }

    public List<FileDoc> findAllFileDocsByProjectId(int projectId) {
        return fileDocDao.findAllFileDocsByProjectId(projectId);
    }

    public List<FileDoc> findAllFileDocsByModuleId(int moduleId) {
        return fileDocDao.findAllFileDocsByModuleId(moduleId);
    }

    @Override
    public void close() throws Exception {
        log.debug("close()...");
        if (jdbcDaoFactory != null) {
            jdbcDaoFactory.close();
        }
    }
}
