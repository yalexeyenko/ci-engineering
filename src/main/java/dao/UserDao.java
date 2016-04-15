package dao;

import entity.User;

public interface UserDao extends Dao<User> {
    void updateUserFull(User user) throws DaoException;
}
