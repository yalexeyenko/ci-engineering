package connection;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnectionPool {

    public static Connection getConnection() {
        try {
            return InstanceHolder.getInstance().getConnection();
        } catch (SQLException e) {
            throw new DBConnectionPoolException("Failed to get connection", e);
        }
    }

    private static class InstanceHolder {
        private static final DataSource DATASOURCE = create();

        private static DataSource create() {
            InitialContext initialContext;
            DataSource dataSource;
            try {
                initialContext = new InitialContext();
                dataSource = (DataSource) initialContext.lookup("java:/comp/env/jdbc/ci-engineering");
            } catch (NamingException e) {
                throw new DBConnectionPoolException("InitialContext error", e);
            }
            return dataSource;
        }

        public static DataSource getInstance() {
            return DATASOURCE;
        }
    }
}
