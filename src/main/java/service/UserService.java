package service;

import dao.*;
import entity.Image;
import entity.TechSkill;
import entity.User;

import java.util.List;

public class UserService {
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

    public User createUser(User user) throws DaoException {
        return userDao.insert(user);
    }

    public void updateUser(User user) throws DaoException {
        Image imageWithId = imageDao.insert(user.getImage());
        user.setImage(imageWithId);

        List<TechSkill> techSkills = user.
    }

    public void updateUserFull(User user) throws DaoException {
        userDao.updateUserFull(user);
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
}
