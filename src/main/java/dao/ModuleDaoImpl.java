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

    private static final String INSERT = "INSERT INTO module (name, startDate, deadLine, finished) VALUES (?, ?, ?, ?) ";
    private static final String FIND_BY_ID = "SELECT FROM module WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM module";
    private static final String FIND_ALL_LIMIT = "SELECT * FROM module LIMIT ? OFFSET ?";
    private static final String UPDATE_MODULE = "UPDATE module SET name = ?, startDate = ?, deadline = ?, finished = ? WHERE id = ?";
    private static final String DELETE_MODULE_BY_ID = "DELETE FROM module WHERE id = ?";

    private final Connection connection;

    public ModuleDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Module insert(Module module) throws DaoException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, module.getName());
            preparedStatement.setDate(2, new java.sql.Date(module.getStartDate().toDate().getTime()));
            preparedStatement.setDate(3, new java.sql.Date(module.getDeadline().toDate().getTime()));
            preparedStatement.setBoolean(4, module.isFinished());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            module.setId(resultSet.getInt(1));
            return module;
        } catch (SQLException e) {
            throw new DaoException("SQL INSERT error.", e);
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
    public Module findById(int id) throws DaoException {
        Module module = new Module();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            module.setId(resultSet.getInt("id"));
            module.setName(resultSet.getString("name"));
            module.setStartDate(new LocalDate(resultSet.getDate("startDate")));
            module.setDeadline(new LocalDate(resultSet.getDate("deadline")));
            module.setFinished(resultSet.getBoolean("finished"));
            return module;
        } catch (SQLException e) {
            throw new DaoException("SQL FIND_BY_ID error.", e);
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
    public List<Module> findAll() throws DaoException {
        List<Module> modules = new ArrayList<>();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL);
            while (resultSet.next()) {
                Module module = new Module();
                module.setId(resultSet.getInt("id"));
                module.setName(resultSet.getString("name"));
                module.setStartDate(new LocalDate(resultSet.getDate("startDate")));
                module.setDeadline(new LocalDate(resultSet.getDate("deadline")));
                module.setFinished(resultSet.getBoolean("finished"));
                modules.add(module);
            }
        } catch (SQLException e) {
            throw new DaoException("SQL FIND_ALL error.", e);
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

    @Override
    public List<Module> findAll(int start, int count) throws DaoException {
        return null;
    }

    @Override
    public void update(Module module) throws DaoException {
        PreparedStatement preparedStatement = null;
        //noinspection Duplicates TODO
        try {
            preparedStatement = connection.prepareStatement(UPDATE_MODULE);
            preparedStatement.setString(1, module.getName());
            preparedStatement.setDate(2, new java.sql.Date(module.getStartDate().toDate().getTime()));
            preparedStatement.setDate(3, new java.sql.Date(module.getDeadline().toDate().getTime()));
            preparedStatement.setBoolean(4, module.isFinished());
            preparedStatement.setInt(5, module.getId());
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
    public boolean delete(int id) throws DaoException {
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
}
