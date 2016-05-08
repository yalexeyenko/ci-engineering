package service;

import dao.*;
import entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UserService implements AutoCloseable {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private DaoFactory jdbcDaoFactory;
    private UserDao userDao;

    public UserService() {
        jdbcDaoFactory = DaoFactory.newInstance(DaoFactory.JDBC);
        userDao = (UserDao) jdbcDaoFactory.createDao(User.class);
    }

    public User createUser(User user) {
        User userCreated;
        log.debug("createUser()...");
        log.debug("user.getFirstName(): {}", user.getFirstName());
        log.debug("user.getLastName(): {}", user.getLastName());
        log.debug("user.getEmail(): {}", user.getEmail());
        log.debug("user.getPassword(): {}", user.getPassword());
        try {
            userCreated = userDao.insert(user);
            log.debug("userCreated == null: {}", userCreated == null);
            return userCreated;
        } catch (DaoException e) {
            log.debug("Caught null");
            return null;
        }
    }

    public User findUserByCredentials(String email, String password) {
        log.debug("findUserByCredentials()...");
        try {
            return userDao.findUserByEmailAndPassword(email, password);
        } catch (DaoException e) {
            return null;
        }
    }

    public void changePassword(User user){
        userDao.updatePassword(user);
    }

    public void updateUser(User user){
        userDao.update(user);
    }

    public void changeUserRole(User user){
        log.debug("changeUserRole()...");
        userDao.updateRole(user);
    }

    public void updateMainProfileInfo(User user){
        userDao.updateMainProfileInfo(user);
    }

    public User findUserById(int id) {
        log.debug("findUserById()...");
        return userDao.findById(id);
    }

    public List<User> findAllUsers() {
        return userDao.findAll();
    }

    public List<User> findAllSeniors() {
        log.debug("findAllSeniors()...");
        return userDao.findAllSeniors();
    }

    @Override
    public void close() throws Exception {
        log.debug("close()...");
        if (jdbcDaoFactory != null) {
            jdbcDaoFactory.close();
        }
    }
}
