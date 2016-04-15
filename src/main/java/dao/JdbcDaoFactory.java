package dao;

import connection.DBConnection;
import entity.BaseEntity;

import java.sql.Connection;
import java.sql.SQLException;

public class JdbcDaoFactory extends DaoFactory {
    Connection connection;

    public JdbcDaoFactory() {
        connection = DBConnection.getConnection();
    }

    @Override
    public <T extends BaseEntity> Dao<T> createDao(Class<T> clazz) {
        switch (clazz.getSimpleName()) {
            case "User":
                return (Dao<T>) new UserDaoImpl(connection);
            case "Image":
                return (Dao<T>) new ImageDao(connection);
            case "TechSkill":
                return (Dao<T>) new TechSkillDao(connection);
            default:
                return null;
        }
    }

    @Override
    public void close() throws DaoException {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DaoException("Cannot close connection.", e);
            }
        }
    }
}
