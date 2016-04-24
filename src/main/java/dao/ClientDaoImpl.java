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
            "clientName, ein, clientType) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String INSERT_INDIV_CLIENT = "INSERT INTO client (country, city, address, telephone, email, " +
            "bankAccountNumber, firstName, lastName, ssn, clientType) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_CLIENT = "UPDATE client SET country = ?, city = ?, address = ?, telephone = ?, " +
            "email = ?, bankAccountNumber = ?, clientName = ?, ein = ?, clientType = ? WHERE id = ?";
    private static final String UPDATE_INDIV_CLIENT = "UPDATE client SET country = ?, city = ?, address = ?, telephone = ?, " +
            "email = ?, bankAccountNumber = ?, firstName = ?, lastName = ?, ssn = ?, clientType = ? WHERE id = ?";
    private static final String FIND_CLIENT_BY_ID = "SELECT FROM client WHERE id = ?";
    private static final String DELETE_BY_ID = "DELETE FROM client WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM client";
    private static final String FIND_ALL_LIMIT = "SELECT * FROM client LIMIT ? OFFSET ?";

    private final Connection connection;

    public ClientDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Client findById(int id) throws DaoException {
        Client client = new Client();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(FIND_CLIENT_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            client.setId(resultSet.getInt("id"));
            client. setAddress(resultSet.getString("address"));
            client.setBankAccountNumber(resultSet.getString("bankAccountNumber"));
            client.setCity(resultSet.getString("city"));
            client.setCountry(resultSet.getString("country"));
            client.setEmail(resultSet.getString("email"));
            client.setTelephone(resultSet.getString("telephone"));
            client.setClientName(resultSet.getString("clientName"));
            client.setFirstName(resultSet.getString("firstName"));
            client.setLastName(resultSet.getString("lastName"));
            client.setEin(resultSet.getString("ein"));
            client.setSsn(resultSet.getString("ssn"));
            client.setClientType(Client.ClientType.valueOf(resultSet.getString("clientType")));
            return client;
        }catch (SQLException e) {
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
    public Client insert(Client client) throws DaoException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(INSERT_CLIENT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, client.getCountry());
            preparedStatement.setString(2, client.getCity());
            preparedStatement.setString(3, client.getAddress());
            preparedStatement.setString(4, client.getTelephone());
            preparedStatement.setString(5, client. getEmail());
            preparedStatement.setString(6, client. getBankAccountNumber());
            preparedStatement.setString(7, client.getClientName());
            preparedStatement.setString(8, client.getEin());
            preparedStatement.setString(9, client.getClientType().name());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            client.setId(resultSet.getInt(1));
            return client;
        }catch (SQLException e) {
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
    public void update(Client client) throws DaoException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(UPDATE_CLIENT);
            preparedStatement.setString(1, client.getCountry());
            preparedStatement.setString(2, client.getCity());
            preparedStatement.setString(3, client.getAddress());
            preparedStatement.setString(4, client.getTelephone());
            preparedStatement.setString(5, client.getEmail());
            preparedStatement.setString(6, client.getBankAccountNumber());
            preparedStatement.setString(7, client.getClientName());
            preparedStatement.setString(8, client.getEin());
            preparedStatement.setString(9, client.getClientType().name());
            preparedStatement.setInt(10, client.getId());
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
    public boolean delete(int id) throws DaoException {
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
    public List<Client> findAll() throws DaoException {
        List<Client> clients = new ArrayList<>();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL);
            Client client = new Client();
            while(resultSet.next()) {
                if (resultSet.getString("clientType").equals("LEGAL")) {
                    client.setId(resultSet.getInt("id"));
                    client. setAddress(resultSet.getString("address"));
                    client.setBankAccountNumber(resultSet.getString("bankAccountNumber"));
                    client.setCity(resultSet.getString("city"));
                    client.setCountry(resultSet.getString("country"));
                    client.setEmail(resultSet.getString("email"));
                    client.setTelephone(resultSet.getString("telephone"));
                    client.setClientName(resultSet.getString("clientName"));
                    client.setEin(resultSet.getString("ein"));
                    client.setClientType(Client.ClientType.valueOf(resultSet.getString("clientType")));
                } else if (resultSet.getString("clientType").equals("INDIVIDUAL")) {
                    client.setId(resultSet.getInt("id"));
                    client. setAddress(resultSet.getString("address"));
                    client.setBankAccountNumber(resultSet.getString("bankAccountNumber"));
                    client.setCity(resultSet.getString("city"));
                    client.setCountry(resultSet.getString("country"));
                    client.setEmail(resultSet.getString("email"));
                    client.setTelephone(resultSet.getString("telephone"));
                    client.setFirstName(resultSet.getString("firstName"));
                    client.setLastName(resultSet.getString("lastName"));
                    client.setEin(resultSet.getString("ein"));
                    client.setClientType(Client.ClientType.valueOf(resultSet.getString("clientType")));
                }
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

    @Override
    public List<Client> findAll(int start, int count) throws DaoException {
        return null;
    }


    @Override
    public Client insertIndivid(Client individualClient) throws DaoException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(INSERT_INDIV_CLIENT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, individualClient.getCountry());
            preparedStatement.setString(2, individualClient.getCity());
            preparedStatement.setString(3, individualClient.getAddress());
            preparedStatement.setString(4, individualClient.getTelephone());
            preparedStatement.setString(5, individualClient. getEmail());
            preparedStatement.setString(6, individualClient. getBankAccountNumber());
            preparedStatement.setString(7, individualClient.getFirstName());
            preparedStatement.setString(8, individualClient.getLastName());
            preparedStatement.setString(9, individualClient.getSsn());
            preparedStatement.setString(10, individualClient.getClientType().name());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            individualClient.setId(resultSet.getInt(1));
            return individualClient;
        }catch (SQLException e) {
            throw new DaoException("SQL INSERT_INDIV_CLIENT error.", e);
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
    public void updateIndivid(Client individualClient) throws DaoException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(UPDATE_INDIV_CLIENT);
            preparedStatement.setString(1, individualClient.getCountry());
            preparedStatement.setString(2, individualClient.getCity());
            preparedStatement.setString(3, individualClient.getAddress());
            preparedStatement.setString(4, individualClient.getTelephone());
            preparedStatement.setString(5, individualClient.getEmail());
            preparedStatement.setString(6, individualClient.getBankAccountNumber());
            preparedStatement.setString(7, individualClient.getFirstName());
            preparedStatement.setString(8, individualClient.getLastName());
            preparedStatement.setString(9, individualClient.getSsn());
            preparedStatement.setString(10, individualClient.getClientType().name());
            preparedStatement.setInt(11, individualClient.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("SQL UPDATE_INDIV_CLIENT error.", e);
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
}
