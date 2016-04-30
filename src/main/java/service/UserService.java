package service;

import dao.*;
import entity.Image;
import entity.TechSkill;
import entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UserService implements AutoCloseable {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private DaoFactory jdbcDaoFactory;
    private UserDao userDao;
    private Dao<Image> imageDao;
    private Dao<TechSkill> techSkillDao;

    public UserService() {
        jdbcDaoFactory = DaoFactory.newInstance(DaoFactory.JDBC);
        userDao = (UserDao) jdbcDaoFactory.createDao(User.class);
        imageDao = jdbcDaoFactory.createDao(Image.class);
        techSkillDao = jdbcDaoFactory.createDao(TechSkill.class);
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
        try {
            return userDao.findUserByEmailAndPassword(email, password);
        } catch (DaoException e) {
            return null;
        }
    }

    public void changePassword(User user) throws DaoException {
        userDao.updatePassword(user);
    }

    public void updateUser(User user) throws DaoException {
        userDao.update(user);
    }

    public void changeUserRole(User user) throws DaoException {
        log.debug("changeUserRole()...");
        userDao.updateRole(user);
    }

    public void updateMainProfileInfo(User user) throws DaoException {
        userDao.updateMainProfileInfo(user);
    }

    public User findUserById(int id) throws DaoException {
        log.debug("findUserById()...");
        return userDao.findById(id);
    }

    public List<User> findAllUsers() throws DaoException {
        return userDao.findAll();
    }

    public User addImage(Image image, User user) throws DaoException {
        Image img = imageDao.insert(image);
        user.setImage(img);
        userDao.update(user);
        return user;
    }

    public User changeImage(Image image, User user) throws DaoException {
        return addImage(image, user);
    }

    public Image getImage(User user) throws DaoException {
        return imageDao.findById(user.getImage().getId());
    }

    public boolean removeImage(User user) throws DaoException {
        return imageDao.delete(user.getImage().getId());
    }


    public List<User> findAllSeniors() throws DaoException {
        log.debug("findAllSeniors()...");
        return userDao.findAllSeniors();
    }

    @Override
    public void close() throws Exception {
        if (jdbcDaoFactory != null) {
            jdbcDaoFactory.close();
        }
    }
}
