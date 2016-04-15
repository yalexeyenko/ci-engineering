package dao;

import entity.BaseEntity;

public abstract class DaoFactory {
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

    public abstract void close() throws DaoException;

}
