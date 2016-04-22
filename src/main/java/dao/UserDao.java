package dao;

import entity.User;

public interface UserDao extends Dao<User> {
    User findUserByEmailAndPassword(String email, String password) throws DaoException;
}
