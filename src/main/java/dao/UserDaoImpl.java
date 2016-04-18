package dao;

import entity.Image;
import entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao{
    private static final Logger log = LoggerFactory.getLogger(UserDaoImpl.class);

    private static final String FIND_USER_BY_ID = "SELECT FROM user WHERE id = ?";
    private static final String INSERT_USER = "INSERT INTO user (firstName, lastName, email, password) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_USER = "UPDATE user SET firstName = ?, lastName = ?, email = ?, password = ?, " +
            "degree = ?, imageId = ? WHERE id = ?";
    private static final String UPDATE_USER_FULL = "UPDATE user SET firstName = ?, lastName = ?, email = ?, password = ?, " +
            "degree = ?, role = ?, imageId = ? WHERE id = ?";
    private static final String DELETE_USER_BY_ID = "DELETE FROM user WHERE id = ?";
    private static final String FIND_ALL_USERS = "SELECT * FROM user";
    private static final String FIND_USER_BY_EMAIL_AND_PASSWORD = "SELECT id, firstName, lastName, email, password FROM user WHERE email= ? AND password= ?";

    private final Connection connection;

    public UserDaoImpl(Connection connection) {
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
        User user = new User();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(FIND_USER_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            user.setId(resultSet.getInt("id"));
            user.setFirstName(resultSet.getString("firstName"));
            user.setLastName(resultSet.getString("lastName"));
            user.setEmail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));
            user.setDegree(resultSet.getString("degree"));
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
            preparedStatement.setInt(6, user.getImage().getId());
            preparedStatement.setInt(7, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("SQL UPDATE_USER error.", e);
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
        Dao imageDao = new ImageDao(connection);
        List<User> users = new ArrayList<>();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL_USERS);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setFirstName(resultSet.getString("firstName"));
                user.setLastName(resultSet.getString("lastName"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setDegree(resultSet.getString("degree"));
                user.setRole(User.Role.valueOf(resultSet.getString("role")));
                user.setImage((Image) imageDao.findById(resultSet.getInt("imageId")));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException("SQL FIND_ALL_USERS error.", e);
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
    public void updateUserFull(User user) throws DaoException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(UPDATE_USER_FULL);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getDegree());
            preparedStatement.setString(6, user.getRole().name());
            preparedStatement.setInt(7, user.getImage().getId());
            preparedStatement.setInt(8, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("SQL UPDATE_USER error.", e);
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
    public User findUserByEmailAndPassword(String email, String password) throws DaoException {
        log.debug("findUserByEmailAndPassword()...");
        log.debug("email: {}", email);
        log.debug("password: {}", password);
        log.debug("findUserByEmailAndPassword()...");
        User user = new User();
        PreparedStatement preparedStatement = null;
        try {
            log.debug("preparedStatement == null: {}", preparedStatement == null);
            log.debug("connection == null: {}", connection == null);
            preparedStatement = connection.prepareStatement(FIND_USER_BY_EMAIL_AND_PASSWORD);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            log.debug("After preparedStatement");
            ResultSet resultSet = preparedStatement.executeQuery();
            log.debug("resultSet == null: {}", resultSet == null);
            resultSet.next();
            user.setId(resultSet.getInt(1));
            user.setFirstName(resultSet.getString(2));
            user.setLastName(resultSet.getString(3));
            user.setEmail(resultSet.getString(4));
            user.setPassword(resultSet.getString(5));
            log.debug("user == null: {}", user == null);
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
}
