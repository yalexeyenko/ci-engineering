package dao;

import entity.BaseEntity;

import java.util.List;

public interface Dao<T extends BaseEntity> {
    T insert(T t) throws DaoException;
    T findById(int id) throws DaoException;
    List<T> findAll() throws DaoException;
    List<T> findAll(int start, int count) throws DaoException;
    void update(T t) throws DaoException;
    boolean delete(int id) throws DaoException;


}
