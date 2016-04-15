package service;

import dao.*;
import entity.Image;
import entity.TechSkill;
import entity.User;

public class UserService {
    private DaoFactory jdbcDaoFactory;
    private Dao<User> userDao;
    private Dao<Image> imageDao;
    private Dao<TechSkill> techSkillDao;

    public UserService() {
        jdbcDaoFactory = DaoFactory.newInstance(DaoFactory.JDBC);
        userDao = jdbcDaoFactory.createDao(User.class);
        imageDao = jdbcDaoFactory.createDao(Image.class);
        techSkillDao = jdbcDaoFactory.createDao(TechSkill.class);
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
