package dao;

import entity.BaseEntity;

public abstract class DaoFactory implements AutoCloseable {
    public static final int JDBC = 1;

    public static DaoFactory newInstance(int whichFactory) {
        switch (whichFactory) {
            case JDBC:
                return new JdbcDaoFactory();
            default:
                return null;
        }
    }

    public abstract <T extends BaseEntity> Dao<T> createDao(Class<T> clazz);

    @Override
    public abstract void close() throws Exception;

    public abstract void startTransaction() throws DaoException;

    public abstract void finishTransaction() throws DaoException;

    public abstract void rollback() throws DaoException;

}
