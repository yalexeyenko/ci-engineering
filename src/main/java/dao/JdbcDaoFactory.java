package dao;

import connection.DBConnectionPool;
import entity.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class JdbcDaoFactory extends DaoFactory {
    private static final Logger log = LoggerFactory.getLogger(JdbcDaoFactory.class);
    private final Connection connection;

    public JdbcDaoFactory() {
        connection = DBConnectionPool.getConnection();
    }

    @Override
    public <T extends BaseEntity> Dao<T> createDao(Class<T> clazz) {
        switch (clazz.getSimpleName()) {
            case "User":
                return (Dao<T>) new UserDaoImpl(connection);
            case "Client":
                return (Dao<T>) new ClientDaoImpl(connection);
            case "Project":
                return (Dao<T>) new ProjectDaoImpl(connection);
            default:
                return null;
        }
    }

    @Override
    public void close() throws DaoException {
        log.debug("close()...");
        if (connection != null) {
            try {
                connection.close();
                log.debug("connection.isClosed(): {}", connection.isClosed());
            } catch (SQLException e) {
                throw new DaoException("Failed to close factory", e);
            }
        }
    }

    @Override
    public void startTransaction() throws DaoException {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DaoException("Failed to setAutoCommit to false", e);
        }
    }

    @Override
    public void finishTransaction() throws DaoException {
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new DaoException("Failed to commit transaction", e);
        }
    }

    @Override
    public void rollback() throws DaoException {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new DaoException("Failed to rollback", e);
        }
    }
}
