package dao;

import entity.Project;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectDaoImpl implements ProjectDao {
    private static final Logger log = LoggerFactory.getLogger(ProjectDaoImpl.class);

    private static final String INSERT_PROJECT = "INSERT INTO project (startDate, name, deadLine, finished) VALUES (?, ?, ?, ?) ";
    private static final String FIND_PROJECT_BY_ID = "SELECT FROM project WHERE id = ?";
    private static final String FIND_ALL_PROJECTS = "SELECT * FROM project";
    private static final String FIND_ALL_PROJECTS_LIMITED = "SELECT * FROM project LIMIT ? OFFSET ?";
    private static final String UPDATE_PROJECT = "UPDATE project SET name = ?, startDate = ?, deadline = ?, finished = ? WHERE id = ?";
    private static final String DELETE_PROJECT_BY_ID = "DELETE FROM project WHERE id = ?";

    private final Connection connection;

    public ProjectDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Project insert(Project project) throws DaoException {
        log.debug("insert()...");
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(INSERT_PROJECT, Statement.RETURN_GENERATED_KEYS);
            log.debug("preparedStatement is null: {}", preparedStatement == null);
            preparedStatement.setDate(1, new java.sql.Date(project.getStartDate().toDate().getTime()));
            preparedStatement.setString(2, project.getName());
            preparedStatement.setDate(3, new java.sql.Date(project.getDeadline().toDate().getTime()));
            preparedStatement.setBoolean(4, project.isFinished());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            project.setId(resultSet.getInt(1));
            return project;
        } catch (SQLException e) {
            throw new DaoException("SQL INSERT_PROJECT error.", e);
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
    public Project findById(int id) throws DaoException {
        Project project = new Project();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(FIND_PROJECT_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            project.setStartDate(new LocalDate(resultSet.getDate("startDate")));
            project.setDeadline(new LocalDate(resultSet.getDate("deadline")));
            project.setName(resultSet.getString("name"));
            project.setId(resultSet.getInt("id"));
            project.setFinished(resultSet.getBoolean("finished"));
            return project;
        } catch (SQLException e) {
            throw new DaoException("SQL FIND_PROJECT_BY_ID error.", e);
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
    public List<Project> findAll() throws DaoException {
        List<Project> projects = new ArrayList<>();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL_PROJECTS);
            while (resultSet.next()) {
                Project project = new Project();
                project.setId(resultSet.getInt("id"));
                project.setName(resultSet.getString("name"));
                project.setStartDate(new LocalDate(resultSet.getDate("startDate")));
                project.setDeadline(new LocalDate(resultSet.getDate("deadline")));
                project.setFinished(resultSet.getBoolean("finished"));
                projects.add(project);
            }
        } catch (SQLException e) {
            throw new DaoException("SQL FIND_ALL_PROJECTS error.", e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw new DaoException("Failed to close Statement", e);
                }
            }
        }
        return projects;
    }

    @Override
    public List<Project> findAll(int start, int count) throws DaoException {
        return null;
    }

    @Override
    public void update(Project project) throws DaoException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(UPDATE_PROJECT);
            preparedStatement.setString(1, project.getName());
            preparedStatement.setDate(2, new java.sql.Date(project.getStartDate().toDate().getTime()));
            preparedStatement.setDate(3, new java.sql.Date(project.getDeadline().toDate().getTime()));
            preparedStatement.setBoolean(4, project.isFinished());
            preparedStatement.setInt(5, project.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("SQL UPDATE_PROJECT error.", e);
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
            preparedStatement = connection.prepareStatement(DELETE_PROJECT_BY_ID);
            preparedStatement.setInt(1, id);
            return (preparedStatement.executeUpdate() != 0);
        } catch (SQLException e) {
            throw new DaoException("SQL DELETE_PROJECT_BY_ID error.", e);
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
