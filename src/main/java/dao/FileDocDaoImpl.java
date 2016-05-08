package dao;

import entity.FileDoc;
import entity.Project;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FileDocDaoImpl implements FileDocDao {
    private static final Logger log = LoggerFactory.getLogger(FileDocDaoImpl.class);

    private static final String INSERT_FILEDOC = "INSERT INTO filedoc (description, lastModified, status, staffId," +
            "  projectId, fileContent, fileName) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String FIND_FILEDOC_BY_ID = "SELECT description, lastModified, status, staffId, projectId, fileContent, fileName" +
            " FROM filedoc WHERE id = ?";
    private static final String FIND_FILEDOCS_BY_PROJECT_ID = "SELECT id, description, lastModified, status, staffId, fileContent, fileName" +
            " FROM filedoc WHERE projectId = ?";
    private static final String FIND_ALL_FILEDOCS = "SELECT * FROM filedoc";
    private static final String UPDATE_FILEDOC = "UPDATE filedoc SET description = ?, lastModified = ?, status = ?, staffId = ?," +
            " projectId = ?, fileContent = ?, fileName = ? WHERE id = ?";
    private static final String DELETE_FILEDOC_BY_ID = "DELETE FROM filedoc WHERE id = ?";

    private final Connection connection;

    public FileDocDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public FileDoc insert(FileDoc fileDoc) {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(INSERT_FILEDOC, Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            preparedStatement.setString(1, fileDoc.getDescription());
            preparedStatement.setDate(2, new Date(fileDoc.getLastModified().toDate().getTime()));
            preparedStatement.setString(3, fileDoc.getStatus().name());
            preparedStatement.setInt(4, fileDoc.getUser().getId());
            preparedStatement.setInt(5, fileDoc.getProject().getId());
            preparedStatement.setBinaryStream(6, fileDoc.getFileContent());
            preparedStatement.setString(7, fileDoc.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            preparedStatement.executeUpdate();// todo edit try-catch
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet resultSet = null;
        try {
            resultSet = preparedStatement.getGeneratedKeys();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            fileDoc.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fileDoc;

    }

    @Override
    public FileDoc findById(int id) {
        UserDao userDao = new UserDaoImpl(connection);
        ProjectDao projectDao = new ProjectDaoImpl(connection);
        FileDoc fileDoc = new FileDoc();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(FIND_FILEDOC_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            fileDoc.setId(resultSet.getInt("id"));
            fileDoc.setDescription(resultSet.getString("description"));
            fileDoc.setLastModified(new LocalDate(resultSet.getDate("lastModified")));
            fileDoc.setStatus(FileDoc.Status.valueOf(resultSet.getString("status")));
            fileDoc.setUser(userDao.findById(Integer.valueOf(resultSet.getString("staffId"))));
            fileDoc.setProject(projectDao.findById(Integer.valueOf(resultSet.getString("projectId"))));
            fileDoc.setFileContent(resultSet.getBinaryStream("fileContent"));
            fileDoc.setName(resultSet.getString("fileName"));
            return fileDoc;
        } catch (SQLException e) {
            throw new DaoException("SQL FIND_FILEDOC_BY_ID error.", e);
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
    public List<FileDoc> findAllFileDocsByProjectId(int projectId) {
        UserDaoImpl userDao = new UserDaoImpl(connection);
        ProjectDaoImpl projectDao = new ProjectDaoImpl(connection);
        List<FileDoc> fileDocs = new ArrayList<>();
        Project project = projectDao.findById(projectId);
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(FIND_FILEDOCS_BY_PROJECT_ID);
            log.debug("preparedStatement: {}", preparedStatement);
            preparedStatement.setInt(1, projectId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                FileDoc fileDoc = new FileDoc();
                fileDoc.setId(resultSet.getInt("id"));
                fileDoc.setDescription(resultSet.getString("description"));
                fileDoc.setLastModified(new LocalDate(resultSet.getDate("lastModified")));
                fileDoc.setStatus(FileDoc.Status.valueOf(resultSet.getString("status")));
                fileDoc.setUser(userDao.findById(resultSet.getInt("staffId")));
                fileDoc.setFileContent(resultSet.getBinaryStream("fileContent"));
                fileDoc.setName(resultSet.getString("fileName"));
                fileDoc.setProject(project);
                fileDocs.add(fileDoc);
            }
        }catch (SQLException e) {
            throw new DaoException("SQL FIND_FILEDOCS_BY_PROJECT_ID error.", e);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DaoException("Failed to close PreparedStatement", e);
                }
            }
        }
        return fileDocs;
    }

    @Override
    public List<FileDoc> findAll() {
        UserDao userDao = new UserDaoImpl(connection);
        ProjectDao projectDao = new ProjectDaoImpl(connection);
        List<FileDoc> fileDocs = new ArrayList<>();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL_FILEDOCS);
            while (resultSet.next()) {
                FileDoc fileDoc = new FileDoc();
                fileDoc.setId(resultSet.getInt("id"));
                fileDoc.setDescription(resultSet.getString("description"));
                fileDoc.setLastModified(new LocalDate(resultSet.getDate("lastModified")));
                fileDoc.setStatus(FileDoc.Status.valueOf(resultSet.getString("status")));
                fileDoc.setUser(userDao.findById(Integer.valueOf(resultSet.getString("staffId"))));
                fileDoc.setProject(projectDao.findById(Integer.valueOf(resultSet.getString("projectId"))));
                fileDoc.setFileContent(resultSet.getBinaryStream("fileContent"));
                fileDocs.add(fileDoc);
            }
        } catch (SQLException e) {
            throw new DaoException("SQL FIND_ALL_FILEDOCS error.", e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw new DaoException("Failed to close Statement", e);
                }
            }
        }
        return fileDocs;
    }

    @Override
    public void update(FileDoc fileDoc) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(UPDATE_FILEDOC);
            preparedStatement.setString(1, fileDoc.getDescription());
            preparedStatement.setDate(2, new java.sql.Date(fileDoc.getLastModified().toDate().getTime()));
            preparedStatement.setString(3, fileDoc.getStatus().name());
            preparedStatement.setInt(4, fileDoc.getUser().getId());
            preparedStatement.setInt(5, fileDoc.getProject().getId());
            preparedStatement.setBinaryStream(6, fileDoc.getFileContent());
            preparedStatement.setString(7, fileDoc.getName());
            preparedStatement.setInt(8, fileDoc.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("SQL UPDATE_FILEDOC error.", e);
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
            preparedStatement = connection.prepareStatement(DELETE_FILEDOC_BY_ID);
            preparedStatement.setInt(1, id);
            return (preparedStatement.executeUpdate() != 0);
        } catch (SQLException e) {
            throw new DaoException("SQL DELETE_FILEDOC_BY_ID error.", e);
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
