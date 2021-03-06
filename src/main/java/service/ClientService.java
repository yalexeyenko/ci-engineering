package service;

import dao.ClientDao;
import dao.DaoException;
import dao.DaoFactory;
import entity.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ClientService implements AutoCloseable {
    private static final Logger log = LoggerFactory.getLogger(ClientService.class);

    private DaoFactory jdbcDaoFactory;
    private ClientDao clientDao;

    public ClientService() {
        jdbcDaoFactory = DaoFactory.newInstance(DaoFactory.JDBC);
        clientDao = (ClientDao) jdbcDaoFactory.createDao(Client.class);
    }

    public Client createClient(Client client) {
        Client clientWithId;
        try {
            clientWithId = clientDao.insert(client);
            return clientWithId;
        } catch (DaoException e) {
            return null;
        }
    }

    public void updateClient(Client client) {
        clientDao.update(client);
    }

    public Client findClientById(int id) {
        return clientDao.findById(id);
    }

    public boolean deleteClientById(int id) {
        return clientDao.delete(id);
    }

    public List<Client> findAllClients() {
        return clientDao.findAll();
    }

    @Override
    public void close() throws Exception {
        if (jdbcDaoFactory != null) {
            jdbcDaoFactory.close();
        }
    }
}
