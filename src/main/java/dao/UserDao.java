package dao;

import entity.User;

import java.util.List;

public interface UserDao extends Dao<User> {
    User findUserByEmailAndPassword(String email, String password) throws DaoException;
    List<User> findAllSeniors() throws DaoException;
}
