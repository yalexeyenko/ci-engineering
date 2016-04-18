package dao;

import entity.Client;

import java.sql.Connection;
import java.util.List;

public class ClientDaoImpl implements ClientDao {
    private static final String INSERT_CLIENT = "INSERT INTO client (email, clientType) values (?, ?)";
    private static final String UPDATE_CLIENT = "UPDATE client SET country = ?, city = ?, address = ?, telephone = ?, " +
            "email = ?, bankAccountNumber = ?";

    private final Connection connection;

    public ClientDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Client findById(int id) throws DaoException {
        return null;
    }

    @Override
    public Client insert(Client client) throws DaoException {
        return null;
    }

    @Override
    public void update(Client client) throws DaoException {

    }

    @Override
    public boolean delete(int id) throws DaoException {
        return false;
    }

    @Override
    public List<Client> findAll() throws DaoException {
        return null;
    }
}
