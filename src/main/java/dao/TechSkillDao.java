package dao;

import entity.BaseEntity;
import entity.TechSkill;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TechSkillDao implements Dao<TechSkill> {
    private static final String INSERT_TECHSKILL = "INSERT INTO techskill (name) values (?)";
    private static final String FIND_TECHSKILL_BY_ID = "SELECT from techskill WHERE id = ?";
    private static final String UPDATE_TECHSKILL = "UPDATE techskill SET name = ? WHERE id = ?";
    private static final String DELETE_TECHSKILL_BY_ID = "DELETE FROM techskill WHERE id = ?";
    private static final String FIND_ALL_TECHSKILLS = "SELECT * FROM techskill";

    Connection connection;

    public TechSkillDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public TechSkill findById(int id) throws DaoException {
        TechSkill techSkill = new TechSkill();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(FIND_TECHSKILL_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            techSkill.setId(resultSet.getInt("id"));
            techSkill.setName(resultSet.getString("name"));
            return techSkill;
        } catch (SQLException e) {
            throw new DaoException("SQL FIND_TECHSKILL_BY_ID error.", e);
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
    public TechSkill insert(TechSkill techSkill) throws DaoException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(INSERT_TECHSKILL);
            preparedStatement.setString(1, techSkill.getName());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            techSkill.setId(resultSet.getInt("id"));
            return techSkill;
        } catch (SQLException e) {
            throw new DaoException("SQL INSERT_TECHSKILL error.", e);
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
    public void update(TechSkill techSkill) throws DaoException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(UPDATE_TECHSKILL);
            preparedStatement.setString(1, techSkill.getName());
            preparedStatement.setInt(2, techSkill.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("SQL UPDATE_TECHSKILL error.", e);
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
            preparedStatement = connection.prepareStatement(DELETE_TECHSKILL_BY_ID);
            preparedStatement.setInt(1, id);
            return (preparedStatement.executeUpdate() != 0);
        } catch (SQLException e) {
            throw new DaoException("SQL DELETE_TECHSKILL_BY_ID error.", e);
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
    public List<TechSkill> findAll() throws DaoException {
        List<TechSkill> techSkills = new ArrayList<>();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL_TECHSKILLS);
            while (resultSet.next()) {
                TechSkill techSkill = new TechSkill();
                techSkill.setId(resultSet.getInt("id"));
                techSkill.setName(resultSet.getString("name"));
                techSkills.add(techSkill);
            }
        } catch (SQLException e) {
            throw new DaoException("SQL FIND_ALL_TECHSKILLS error.", e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw new DaoException("Failed to close Statement", e);
                }
            }
        }
        return techSkills;
    }
}
