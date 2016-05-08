package dao;

import entity.BaseEntity;

import java.util.List;

public interface Dao<T extends BaseEntity> {
    T insert(T t);
    T findById(int id);
    List<T> findAll();
    void update(T t);
    boolean delete(int id);
}
