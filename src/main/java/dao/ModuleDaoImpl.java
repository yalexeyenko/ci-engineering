package dao;

import entity.Module;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ModuleDaoImpl implements ModuleDao {
    private static final Logger log = LoggerFactory.getLogger(ModuleDaoImpl.class);

    private static final String INSERT_MODULE = "INSERT INTO module (moduleName, startDate, deadLine, finished, staffId, projectId) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String FIND_MODULE_BY_ID = "SELECT moduleName, startDate, deadLine, finished, staffId, projectId FROM module WHERE id = ?";
    private static final String FIND_MODULES_BY_PROJECT_ID = "SELECT id, moduleName, startDate, deadLine, finished, staffId, projectId FROM module WHERE projectId = ?";
    private static final String FIND_ALL_MODULES = "SELECT * FROM module";
    private static final String UPDATE_MODULE = "UPDATE module SET moduleName = ?, deadline = ?, finished = ? WHERE id = ?";
    private static final String DELETE_MODULE_BY_ID = "DELETE FROM module WHERE id = ?";

    private final Connection connection;

    public ModuleDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Module insert(Module module) {
        log.debug("insert()...");
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(INSERT_MODULE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, module.getName());
            preparedStatement.setDate(2, new java.sql.Date(module.getStartDate().toDate().getTime()));
            preparedStatement.setDate(3, new java.sql.Date(module.getDeadline().toDate().getTime()));
            preparedStatement.setBoolean(4, module.isFinished());
            preparedStatement.setInt(5, module.getManager().getId());
            preparedStatement.setInt(6, module.getProject().getId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            module.setId(resultSet.getInt(1));
            return module;
        } catch (SQLException e) {
            throw new DaoException("SQL INSERT_MODULE error.", e);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DaoException("Failed to close PreparedStatement", e);
                }
            }
        }
    }

    @Override
    public Module findById(int id) {
        log.debug("findById()...");
        Module module = new Module();
        UserDaoImpl userDao = new UserDaoImpl(connection);
        ProjectDaoImpl projectDao = new ProjectDaoImpl(connection);
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(FIND_MODULE_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            module.setId(id);
            module.setName(resultSet.getString("moduleName"));
            module.setStartDate(new LocalDate(resultSet.getDate("startDate")));
            module.setDeadline(new LocalDate(resultSet.getDate("deadline")));
            module.setFinished(resultSet.getBoolean("finished"));
            module.setManager(userDao.findById(resultSet.getInt("staffId")));
            module.setProject(projectDao.findById(resultSet.getInt("projectId")));
            return module;
        } catch (SQLException e) {
            throw new DaoException("SQL FIND_MODULE_BY_ID error.", e);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DaoException("Failed to close PreparedStatement", e);
                }
            }
        }
    }

    @SuppressWarnings("Duplicates")
    @Override
    public List<Module> findAll() {
        List<Module> modules = new ArrayList<>();
        UserDaoImpl userDao = new UserDaoImpl(connection);
        ProjectDaoImpl projectDao = new ProjectDaoImpl(connection);
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL_MODULES);
            while (resultSet.next()) {
                Module module = new Module();
                module.setId(resultSet.getInt("id"));
                module.setName(resultSet.getString("moduleName"));
                module.setStartDate(new LocalDate(resultSet.getDate("startDate")));
                module.setDeadline(new LocalDate(resultSet.getDate("deadline")));
                module.setFinished(resultSet.getBoolean("finished"));
                module.setManager(userDao.findById(resultSet.getInt("staffId")));
                module.setProject(projectDao.findById(resultSet.getInt("projectId")));
                modules.add(module);
            }
        } catch (SQLException e) {
            throw new DaoException("SQL FIND_ALL_MODULES error.", e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw new DaoException("Failed to close Statement", e);
                }
            }
        }
        return modules;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void update(Module module) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(UPDATE_MODULE);
            preparedStatement.setString(1, module.getName());
            preparedStatement.setDate(2, new java.sql.Date(module.getDeadline().toDate().getTime()));
            preparedStatement.setBoolean(3, module.isFinished());
            preparedStatement.setInt(4, module.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("SQL UPDATE_MODULE error.", e);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DaoException("Failed to close PreparedStatement", e);
                }
            }
        }
    }

    @Override
    public boolean delete(int id) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(DELETE_MODULE_BY_ID);
            preparedStatement.setInt(1, id);
            return (preparedStatement.executeUpdate() != 0);
        } catch (SQLException e) {
            throw new DaoException("SQL DELETE_MODULE_BY_ID error.", e);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DaoException("Failed to close PreparedStatement", e);
                }
            }
        }
    }

    @SuppressWarnings("Duplicates")
    @Override
    public List<Module> findModulesByProjectId(int projectId) {
        log.debug("findModulesByProjectId()...");
        UserDaoImpl userDao = new UserDaoImpl(connection);
        ProjectDaoImpl projectDao = new ProjectDaoImpl(connection);
        List<Module> modules = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(FIND_MODULES_BY_PROJECT_ID);
            preparedStatement.setInt(1, projectId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Module module = new Module();
                module.setId(resultSet.getInt("id"));
                module.setName(resultSet.getString("moduleName"));
                module.setStartDate(new LocalDate(resultSet.getDate("startDate")));
                module.setDeadline(new LocalDate(resultSet.getDate("deadline")));
                module.setFinished(resultSet.getBoolean("finished"));
                module.setManager(userDao.findById(resultSet.getInt("staffId")));
                module.setProject(projectDao.findById(resultSet.getInt("projectId")));
                modules.add(module);
            }
        } catch (SQLException e) {
            throw new DaoException("SQL FIND_MODULES_BY_PROJECT_ID error.", e);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DaoException("Failed to close PreparedStatement", e);
                }
            }
        }
        return modules;
    }
}