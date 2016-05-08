package dao;

import entity.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDaoImpl implements ClientDao {
    private static final Logger log = LoggerFactory.getLogger(ClientDaoImpl.class);

    private static final String INSERT_CLIENT = "INSERT INTO client (country, city, address, telephone, email, bankAccountNumber, " +
            "firstName, lastName, einSsn, clientType) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_CLIENT = "UPDATE client SET country = ?, city = ?, address = ?, telephone = ?, " +
            "email = ?, bankAccountNumber = ?, firstName = ?, lastName = ?, einSsn = ?, clientType = ? WHERE id = ?";
    private static final String FIND_CLIENT_BY_ID = "SELECT id, country, city, address, telephone, email, " +
            "bankAccountNumber, einSsn, firstName, lastName, clientType FROM client WHERE id = ?";
    private static final String DELETE_BY_ID = "DELETE FROM client WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM client";

    private final Connection connection;

    public ClientDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Client findById(int id) {
        log.debug("findById...");
        log.debug("id: {}", id);
        Client client = new Client();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(FIND_CLIENT_BY_ID);
            log.debug("preparedStatement is null: {}", preparedStatement == null);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            log.debug("resultSet is null: {}", resultSet == null);
            resultSet.next();
            log.debug("resultSet.next()");
            client.setAddress(resultSet.getString("address"));
            client.setId(resultSet.getInt("id"));
            client.setBankAccountNumber(resultSet.getString("bankAccountNumber"));
            client.setCity(resultSet.getString("city"));
            client.setCountry(resultSet.getString("country"));
            client.setEmail(resultSet.getString("email"));
            client.setTelephone(resultSet.getString("telephone"));
            client.setFirstName(resultSet.getString("firstName"));
            client.setLastName(resultSet.getString("lastName"));
            client.setEinSsn(resultSet.getString("einSsn"));
            client.setClientType(Client.ClientType.valueOf(resultSet.getString("clientType")));
            log.debug("client is null: {}", client == null);
            return client;
        } catch (SQLException e) {
            throw new DaoException("SQL FIND_CLIENT_BY_ID error.", e);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DaoException("Failed to close PreparedStatement", e);
                }
            }
        }

    }

    @Override
    public Client insert(Client client) {
        log.debug("insert...");
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(INSERT_CLIENT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, client.getCountry());
            preparedStatement.setString(2, client.getCity());
            preparedStatement.setString(3, client.getAddress());
            preparedStatement.setString(4, client.getTelephone());
            preparedStatement.setString(5, client.getEmail());
            preparedStatement.setString(6, client.getBankAccountNumber());
            preparedStatement.setString(7, client.getFirstName());
            preparedStatement.setString(8, client.getLastName());
            preparedStatement.setString(9, client.getEinSsn());
            preparedStatement.setString(10, client.getClientType().name());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            client.setId(resultSet.getInt(1));
            return client;
        } catch (SQLException e) {
            throw new DaoException("SQL INSERT_CLIENT error.", e);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DaoException("Failed to close PreparedStatement", e);
                }
            }
        }
    }

    @Override
    public void update(Client client) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(UPDATE_CLIENT);
            preparedStatement.setString(1, client.getCountry());
            preparedStatement.setString(2, client.getCity());
            preparedStatement.setString(3, client.getAddress());
            preparedStatement.setString(4, client.getTelephone());
            preparedStatement.setString(5, client.getEmail());
            preparedStatement.setString(6, client.getBankAccountNumber());
            preparedStatement.setString(7, client.getFirstName());
            preparedStatement.setString(8, client.getLastName());
            preparedStatement.setString(9, client.getEinSsn());
            preparedStatement.setString(10, client.getClientType().name());
            preparedStatement.setInt(11, client.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("SQL UPDATE_CLIENT error.", e);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DaoException("Failed to close PreparedStatement", e);
                }
            }
        }
    }

    @Override
    public boolean delete(int id) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(DELETE_BY_ID);
            preparedStatement.setInt(1, id);
            return (preparedStatement.executeUpdate() != 0);
        } catch (SQLException e) {
            throw new DaoException("SQL DELETE_BY_ID error.", e);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DaoException("Failed to close PreparedStatement", e);
                }
            }
        }
    }

    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL);
            Client client = new Client();
            while (resultSet.next()) {
                client.setId(resultSet.getInt("id"));
                client.setAddress(resultSet.getString("address"));
                client.setBankAccountNumber(resultSet.getString("bankAccountNumber"));
                client.setCity(resultSet.getString("city"));
                client.setCountry(resultSet.getString("country"));
                client.setEmail(resultSet.getString("email"));
                client.setTelephone(resultSet.getString("telephone"));
                client.setFirstName(resultSet.getString("firstName"));
                client.setLastName(resultSet.getString("lastName"));
                client.setEinSsn(resultSet.getString("einSsn"));
                client.setClientType(Client.ClientType.valueOf(resultSet.getString("clientType")));
                clients.add(client);
            }
        } catch (SQLException e) {
            throw new DaoException("SQL FIND_ALL error.", e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw new DaoException("Failed to close Statement", e);
                }
            }
        }
        return clients;
    }
}
