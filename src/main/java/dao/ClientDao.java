package dao;

import entity.Client;

public interface ClientDao extends Dao<Client> {
    Client insertIndivid(Client individualClient) throws DaoException;
    void updateIndivid(Client individualClient) throws DaoException;
}
