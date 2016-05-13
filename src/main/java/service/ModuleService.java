package service;

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

    public Module createModule(Module module) {
        return moduleDao.insert(module);
    }

    public Module findModuleById(int id) {
        return moduleDao.findById(id);
    }

    public void updateModule(Module module) {
        moduleDao.update(module);
    }

    public boolean deleteModuleById(int id) {
        return moduleDao.delete(id);
    }

    public List<Module> findAllModules() {
        return moduleDao.findAll();
    }

    public void addEngineerToModule(int moduleId, int engineerId) {
        moduleDao.addEngineerToModule(moduleId, engineerId);
    }

    public boolean deleteEngineerFromModule(int moduleId, int engineerId) {
        return moduleDao.deleteEngineerFromModule(moduleId, engineerId);
    }

    public List<Module> findModulesByProjectId(int projectId) {
        return moduleDao.findModulesByProjectId(projectId);
    }

    @Override
    public void close() throws Exception {
        if (jdbcDaoFactory != null) {
            jdbcDaoFactory.close();
        }
    }
}
