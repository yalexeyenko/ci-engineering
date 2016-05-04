package service;

import dao.DaoException;
import dao.DaoFactory;
import dao.FileDocDao;
import dao.UserDao;
import entity.FileDoc;
import entity.User;
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

    public FileDoc createFileDoc(FileDoc fileDoc) throws DaoException {
        return fileDocDao.insert(fileDoc);
    }

    public FileDoc findById(int id) throws DaoException {
        return fileDocDao.findById(id);
    }

    public List<FileDoc> findAllFileDocs() throws DaoException {
        return fileDocDao.findAll();
    }

    public void updateFileDoc(FileDoc fileDoc) throws DaoException {
        fileDocDao.update(fileDoc);
    }

    public boolean deleteFileDoc(int id) throws DaoException {
        return fileDocDao.delete(id);
    }

    @Override
    public void close() throws Exception {
        log.debug("close()...");
        if (jdbcDaoFactory != null) {
            jdbcDaoFactory.close();
        }
    }
}
