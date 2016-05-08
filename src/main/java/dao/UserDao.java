package dao;

import entity.User;

import java.util.List;

public interface UserDao extends Dao<User> {
    User findUserByEmailAndPassword(String email, String password);
    List<User> findAllSeniors();
    void updateMainProfileInfo(User user);
    void updatePassword(User user);
    void updateRole(User user);
}
