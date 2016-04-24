package service;

import dao.DaoException;
import dao.DaoFactory;
import dao.ModuleDao;
import entity.Module;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ModuleService implements AutoCloseable {
    private static final Logger log = LoggerFactory.getLogger(ModuleService.class);

    private DaoFactory jdbcDaoFactory;
    private ModuleDao moduleDao;

    public ModuleService() {
        jdbcDaoFactory = DaoFactory.newInstance(DaoFactory.JDBC);
        moduleDao = (ModuleDao) jdbcDaoFactory.createDao(Module.class);
    }

    public Module createModule(Module module) throws DaoException {
        return moduleDao.insert(module);
    }

    public Module findModuleById(int id) throws DaoException {
        return moduleDao.findById(id);
    }

    public void updateModule(Module module) throws DaoException {
        moduleDao.update(module);
    }

    public boolean deleteModuleById(int id) throws DaoException {
        return moduleDao.delete(id);
    }

    public List<Module> findAllModules() throws DaoException {
        return moduleDao.findAll();
    }

    public List<Module> findAllModulesLimited(int start, int count) throws DaoException {
        return moduleDao.findAll(start, count);
    }

    @Override
    public void close() throws Exception {
        if (jdbcDaoFactory != null) {
            jdbcDaoFactory.close();
        }
    }
}
