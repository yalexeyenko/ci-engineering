package dao;

import entity.BaseEntity;
import entity.Image;

import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ImageDao implements Dao<Image> {
    private static final String INSERT_IMAGE = "INSERT INTO image (imageFile) values (?)";
    private static final String FIND_IMAGE_BY_ID = "SELECT FROM image WHERE id = ?";
    private static final String DELETE_IMAGE_BY_ID = "DELETE FROM image WHERE id = ?";
    private static final String UPDATE_IMAGE = "UPDATE image SET imageFile = ? WHERE id = ?";
    private static final String FIND_ALL_IMAGES = "SELECT * FROM image";

    Connection connection;

    public ImageDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Image findById(int id) throws DaoException {
        Image img = new Image();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(FIND_IMAGE_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            img.setId(resultSet.getInt("id"));
            img.setImageFile((FileInputStream) resultSet.getBlob("imageFile"));
            return img;
        } catch (SQLException e) {
            throw new DaoException("SQL FIND_IMAGE_BY_ID error.", e);
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
    public Image insert(Image image) throws DaoException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(INSERT_IMAGE);
            preparedStatement.setBlob(1, image.getImageFile());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            image.setId(resultSet.getInt("id"));
            return image;
        } catch (SQLException e) {
            throw new DaoException("SQL INSERT_IMAGE error.", e);
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
    public void update(Image image) throws DaoException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(UPDATE_IMAGE);
            preparedStatement.setBlob(1, image.getImageFile());
            preparedStatement.setInt(2, image.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("SQL UPDATE_IMAGE error.", e);
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
            preparedStatement = connection.prepareStatement(DELETE_IMAGE_BY_ID);
            preparedStatement.setInt(1, id);
            return (preparedStatement.executeUpdate() != 0);
        } catch (SQLException e) {
            throw new DaoException("SQL DELETE_IMAGE_BY_ID error.", e);
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
    public List<Image> findAll() throws DaoException {
        List<Image> images = new ArrayList<>();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL_IMAGES);
            while (resultSet.next()) {
                Image image = new Image();
                image.setId(resultSet.getInt("id"));
                image.setImageFile((FileInputStream) resultSet.getBlob("imageFile"));
                images.add(image);
            }
        } catch (SQLException e) {
            throw new DaoException("SQL FIND_ALL_IMAGES error.", e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw new DaoException("Failed to close Statement", e);
                }
            }
        }
        return images;
    }
}
