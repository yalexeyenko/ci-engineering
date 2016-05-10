package dao;

import entity.Module;

import java.util.List;

public interface ModuleDao extends Dao<Module> {
    List<Module> findModulesByProjectId(int projectId);
}
