package dao;

import entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private static final Logger log = LoggerFactory.getLogger(UserDaoImpl.class);

    private static final String FIND_USER_BY_ID = "SELECT firstName, id, lastName, email, password, degree, role FROM staff WHERE id = ?";
    private static final String INSERT_USER = "INSERT INTO staff (firstName, lastName, email, password, role) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_USER = "UPDATE staff SET firstName = ?, lastName = ?, email = ?, password = ?, " +
            "degree = ?, role = ? WHERE id = ?";
    private static final String UPDATE_MAIN_USER_INFO = "UPDATE staff SET firstName = ?, lastName = ?, email = ?, degree = ?, " +
            "role = ? WHERE id = ?";
    private static final String UPDATE_PASSWORD = "UPDATE staff SET password = ? WHERE id = ?";
    private static final String UPDATE_USER_ROLE = "UPDATE staff SET role = ? WHERE id = ?";
    private static final String DELETE_USER_BY_ID = "DELETE FROM staff WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM staff";
    private static final String FIND_ALL_SENIORS = "SELECT id, firstName, lastName, email, password, role, degree FROM staff WHERE role = ?";
    private static final String FIND_USER_BY_EMAIL_AND_PASSWORD = "SELECT id, firstName, lastName, email, password, role, degree FROM staff WHERE email= ? AND password= ?";
    private static final String FIND_ALL_LIMIT = "SELECT * FROM staff LIMIT ? OFFSET ?";

    private final Connection connection;

    public UserDaoImpl(Connection connection) {
        log.debug("connection: {}", connection);
        this.connection = connection;
    }

    @Override
    public User insert(User user) throws DaoException {
        log.debug("insert()...");
        log.debug("user.getFirstName(): {}", user.getFirstName());
        log.debug("user.getLastName(): {}", user.getLastName());
        log.debug("user.getEmail(): {}", user.getEmail());
        log.debug("user.getPassword(): {}", user.getPassword());
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            log.debug("connection is null: {}", connection == null);
            log.debug("preparedStatement is null: {}", preparedStatement == null);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getRole().name());
            log.debug("before executeQuery");
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            log.debug("resultSet is null: {}", resultSet == null);
            resultSet.next();
            log.debug("resultSet.next()");
            log.debug("resultSet.getInt(id), {}", resultSet.getInt(1));
            user.setId(resultSet.getInt(1));
            return user;
        } catch (SQLException e) {
            log.debug("!!!");
            throw new DaoException("SQL INSERT_USER error.", e);
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
    public User findById(int id) throws DaoException {
        log.debug("findById()...");
        log.debug("id: {}", id);
        User user = new User();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(FIND_USER_BY_ID);
            log.debug("preparedStatement is null: {}", preparedStatement == null);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            log.debug("resultSet is null: {}", resultSet == null);
            resultSet.next();
            user.setId(resultSet.getInt("id"));
            user.setFirstName(resultSet.getString("firstName"));
            user.setLastName(resultSet.getString("lastName"));
            user.setEmail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));
            if (resultSet.getString("degree") != null) {
                user.setDegree(resultSet.getString("degree"));
            }
            user.setRole(User.Role.valueOf(resultSet.getString("role")));
            return user;
        } catch (SQLException e) {
            throw new DaoException("SQL FIND_USER_BY_ID error.", e);
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
    public void update(User user) throws DaoException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(UPDATE_USER);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getDegree());
            if (user.getRole() == null) {
                preparedStatement.setString(6, null);
            } else {
                preparedStatement.setString(6, user.getRole().name());
            }
            preparedStatement.setInt(7, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("SQL UPDATE error.", e);
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
    public void updateMainProfileInfo(User user) throws DaoException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(UPDATE_MAIN_USER_INFO);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getDegree());
            if (user.getRole() == null) {
                preparedStatement.setString(5, null);
            } else {
                preparedStatement.setString(5, user.getRole().name());
            }
            preparedStatement.setInt(6, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.debug("Failed to updateMainProfileInfo()");
            throw new DaoException("SQL UPDATE_MAIN_USER_INFO error.", e);
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
    public void updatePassword(User user) throws DaoException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(UPDATE_PASSWORD);
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setInt(2, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.debug("Failed to updatePassword()");
            throw new DaoException("SQL UPDATE_PASSWORD error.", e);
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
    public void updateRole(User user) throws DaoException {
        log.debug("updateRole()...");
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(UPDATE_USER_ROLE);
            log.debug("user.getRole().name(): {}", user.getRole().name());
            log.debug("user.getInt: {}", user.getId());
            preparedStatement.setString(1, user.getRole().name());
            preparedStatement.setInt(2, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.debug("Failed to updateRole()");
            throw new DaoException("SQL UPDATE_USER_ROLE error.", e);
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
            preparedStatement = connection.prepareStatement(DELETE_USER_BY_ID);
            preparedStatement.setInt(1, id);
            return (preparedStatement.executeUpdate() != 0);
        } catch (SQLException e) {
            throw new DaoException("SQL DELETE_USER_BY_ID error.", e);
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
    public List<User> findAll() throws DaoException {
        List<User> users = new ArrayList<>();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL);
            while (resultSet.next()) {
                User user = new User();
                user.setFirstName(resultSet.getString("firstName"));
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setLastName(resultSet.getString("lastName"));
                user.setPassword(resultSet.getString("password"));
                if (resultSet.getString("degree") != null) {
                    user.setDegree(resultSet.getString("degree"));
                }
                if (resultSet.getString("role") != null) {
                    user.setRole(User.Role.valueOf(resultSet.getString("role")));
                }
                users.add(user);
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
        return users;
    }

    @Override
    public List<User> findAll(int start, int count) throws DaoException {
        return null;
    }

    @Override
    public User findUserByEmailAndPassword(String email, String password) throws DaoException {
        log.debug("findUserByEmailAndPassword()...");
        log.debug("email: {}", email);
        log.debug("password: {}", password);
        User user = new User();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(FIND_USER_BY_EMAIL_AND_PASSWORD);
            log.debug("preparedStatement: {}", preparedStatement);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            log.debug("resultSet: {}", resultSet);
            resultSet.next();
            user.setId(resultSet.getInt(1));
            user.setFirstName(resultSet.getString(2));
            user.setLastName(resultSet.getString(3));
            user.setEmail(resultSet.getString(4));
            user.setPassword(resultSet.getString(5));
            if (resultSet.getString(7) != null) {
                user.setDegree(resultSet.getString((7)));
            }
            if (resultSet.getString(6) != null) {
                user.setRole(User.Role.valueOf(resultSet.getString((6))));
            }
            log.debug("user: {}", user);
            return user;
        } catch (SQLException e) {
            throw new DaoException("SQL FIND_USER_BY_EMAIL_AND_PASSWORD error.", e);
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
    public List<User> findAllSeniors() throws DaoException {
        log.debug("findAllSeniors()...");
        List<User> seniors = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(FIND_ALL_SENIORS);
            log.debug("before resultSet");
            preparedStatement.setString(1, "SENIOR");
            ResultSet resultSet = preparedStatement.executeQuery();
            log.debug("resultSet is null: {}", resultSet == null);
            while (resultSet.next()) {
                User senior = new User();
                senior.setFirstName(resultSet.getString("firstName"));
                senior.setId(resultSet.getInt("id"));
                senior.setLastName(resultSet.getString("lastName"));
                senior.setEmail(resultSet.getString("email"));
                senior.setPassword(resultSet.getString("password"));
                if (resultSet.getString("degree") != null) {
                    senior.setDegree(resultSet.getString("degree"));
                }
                if (resultSet.getString("role") != null) {
                    senior.setRole(User.Role.valueOf(resultSet.getString("role")));
                }
                seniors.add(senior);
            }
        } catch (SQLException e) {
            throw new DaoException("SQL FIND_ALL_SENIORS error.", e);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DaoException("Failed to close PreparedStatement", e);
                }
            }
        }
        return seniors;
    }
}
