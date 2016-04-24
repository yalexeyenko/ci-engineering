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

    public Client createLegalClient(Client legalClient) {
        Client clientWithId;
        try {
            clientWithId = clientDao.insert(legalClient);
            return clientWithId;
        } catch (DaoException e) {
            return null;
        }
    }

    public Client createIndividualClient(Client individualClient) {
        Client clientWithId;
        try {
            clientWithId = clientDao.insertIndivid(individualClient);
            return clientWithId;
        } catch (DaoException e) {
            return null;
        }
    }

    public void updateClient(Client client) throws DaoException {
        clientDao.update(client);
    }

    public void updateIndividualClient(Client client) throws DaoException {
        clientDao.updateIndivid(client);
    }

    public Client findClientById(int id) throws DaoException {
        return clientDao.findById(id);
    }

    public boolean deleteClientById(int id) throws DaoException {
        return clientDao.delete(id);
    }

    public List<Client> findAllClients() throws DaoException {
        return clientDao.findAll();
    }

    public List<Client> findAllClientsLimited(int start, int count) throws DaoException {
        return clientDao.findAll(start, count);
    }

    @Override
    public void close() throws Exception {
        if (jdbcDaoFactory != null) {
            jdbcDaoFactory.close();
        }
    }
}
