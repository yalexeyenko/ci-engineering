package dao;

import entity.Client;
import entity.Project;
import entity.User;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectDaoImpl implements ProjectDao {
    private static final Logger log = LoggerFactory.getLogger(ProjectDaoImpl.class);

    private static final String INSERT_PROJECT = "INSERT INTO project (startDate, projectName, deadLine, finished, staffId) VALUES (?, ?, ?, ?, ?) ";
    private static final String FIND_PROJECT_BY_ID = "SELECT startDate, deadline, projectName, id, finished, clientId, seniorId, staffId FROM project WHERE id = ?";
    private static final String FIND_ALL_PROJECTS = "SELECT * FROM project";
    private static final String FIND_ALL_PERSONAL_PROJECTS = "SELECT startDate, deadline, projectName, id, finished, clientId, seniorId, staffId FROM project WHERE staffId = ?";
    private static final String UPDATE_PROJECT = "UPDATE project SET projectName = ?, deadline = ?, finished = ? WHERE id = ?";
    private static final String DELETE_PROJECT_BY_ID = "DELETE FROM project WHERE id = ?";
    private static final String UPDATE_PROJECT_CLIENT = "UPDATE project SET clientId = ? WHERE id = ?";
    private static final String UPDATE_PROJECT_SENIOR = "UPDATE project SET seniorId = ? WHERE id = ?";

    private final Connection connection;

    public ProjectDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Project insert(Project project) {
        log.debug("insert()...");
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(INSERT_PROJECT, Statement.RETURN_GENERATED_KEYS);
            log.debug("preparedStatement is null: {}", preparedStatement == null);
            preparedStatement.setDate(1, new java.sql.Date(project.getStartDate().toDate().getTime()));
            preparedStatement.setString(2, project.getName());
            preparedStatement.setDate(3, new java.sql.Date(project.getDeadline().toDate().getTime()));
            preparedStatement.setBoolean(4, project.isFinished());
            preparedStatement.setInt(5, project.getManager().getId());
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
    public Project findById(int id) {
        log.debug("findById()...");
        ClientDao clientDao = new ClientDaoImpl(connection);
        UserDao userDao = new UserDaoImpl(connection);
        Project project = new Project();
        Client client;
        User senior;
        User manager;
        PreparedStatement preparedStatement = null;
        int clientId;
        int seniorId;
        int staffId;
        try {
            preparedStatement = connection.prepareStatement(FIND_PROJECT_BY_ID);
            log.debug("preparedStatement is null: {}", preparedStatement == null);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            project.setStartDate(new LocalDate(resultSet.getDate("startDate")));
            project.setDeadline(new LocalDate(resultSet.getDate("deadline")));
            project.setName(resultSet.getString("projectName"));
            project.setId(resultSet.getInt("id"));
            project.setFinished(resultSet.getBoolean("finished"));
            if (resultSet.getInt("clientId") > 0) {
                clientId = resultSet.getInt("clientId");
                client = clientDao.findById(clientId);
                project.setClient(client);
            }
            if (resultSet.getInt("seniorId") > 0) {
                seniorId = resultSet.getInt("seniorId");
                senior = userDao.findById(seniorId);
                project.setSenior(senior);
            }
            if (resultSet.getInt("staffId") > 0) {
                staffId = resultSet.getInt("staffId");
                manager = userDao.findById(staffId);
                project.setManager(manager);
            }
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
    public List<Project> findAll() {
        ClientDao clientDao = new ClientDaoImpl(connection);
        UserDao userDao = new UserDaoImpl(connection);
        List<Project> projects = new ArrayList<>();
        Client client;
        User senior;
        User manager;
        int clientId;
        int seniorId;
        int managerId;
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL_PROJECTS);
            while (resultSet.next()) {
                Project project = new Project();
                project.setId(resultSet.getInt("id"));
                project.setName(resultSet.getString("projectName"));
                project.setStartDate(new LocalDate(resultSet.getDate("startDate")));
                project.setDeadline(new LocalDate(resultSet.getDate("deadline")));
                project.setFinished(resultSet.getBoolean("finished"));
                if (resultSet.getInt("clientId") > 0) {
                    clientId = resultSet.getInt("clientId");
                    client = clientDao.findById(clientId);
                    project.setClient(client);
                }
                if (resultSet.getInt("seniorId") > 0) {
                    seniorId = resultSet.getInt("seniorId");
                    senior = userDao.findById(seniorId);
                    project.setSenior(senior);
                }
                if (resultSet.getInt("staffId") > 0) {
                    managerId = resultSet.getInt("staffId");
                    manager = userDao.findById(managerId);
                    project.setManager(manager);
                }
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
    public void update(Project project) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(UPDATE_PROJECT);
            preparedStatement.setString(1, project.getName());
            preparedStatement.setDate(2, new java.sql.Date(project.getDeadline().toDate().getTime()));
            preparedStatement.setBoolean(3, project.isFinished());
            preparedStatement.setInt(4, project.getId());
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
    public boolean delete(int id) {
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


    @Override
    public void updateProjectClient(Project projectWithClient) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(UPDATE_PROJECT_CLIENT);
            preparedStatement.setInt(1, projectWithClient.getClient().getId());
            preparedStatement.setInt(2, projectWithClient.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("SQL UPDATE_PROJECT_CLIENT error.", e);
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
    public void updateProjectSenior(Project projectWithSenior) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(UPDATE_PROJECT_SENIOR);
            preparedStatement.setInt(1, projectWithSenior.getSenior().getId());
            preparedStatement.setInt(2, projectWithSenior.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("SQL UPDATE_PROJECT_SENIOR error.", e);
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
    public List<Project> findAllPersonalProjects(int managerId) {
        log.debug("findAllPersonalProjects()...");
        ClientDao clientDao = new ClientDaoImpl(connection);
        UserDao userDao = new UserDaoImpl(connection);
        List<Project> projects = new ArrayList<>();
        Client client;
        User senior;
        User manager;
        int clientId;
        int seniorId;
        int staffId;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(FIND_ALL_PERSONAL_PROJECTS);
            log.debug("preparedStatement is null: {}", preparedStatement == null);
            preparedStatement.setInt(1, managerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Project project = new Project();
                project.setStartDate(new LocalDate(resultSet.getDate("startDate")));
                project.setDeadline(new LocalDate(resultSet.getDate("deadline")));
                project.setName(resultSet.getString("projectName"));
                project.setId(resultSet.getInt("id"));
                project.setFinished(resultSet.getBoolean("finished"));
                if (resultSet.getInt("clientId") > 0) {
                    clientId = resultSet.getInt("clientId");
                    client = clientDao.findById(clientId);
                    project.setClient(client);
                }
                if (resultSet.getInt("seniorId") > 0) {
                    seniorId = resultSet.getInt("seniorId");
                    senior = userDao.findById(seniorId);
                    project.setSenior(senior);
                }
                if (resultSet.getInt("staffId") > 0) {
                    staffId = resultSet.getInt("staffId");
                    manager = userDao.findById(staffId);
                    project.setManager(manager);
                }
                projects.add(project);
            }
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
        return projects;
    }
}
