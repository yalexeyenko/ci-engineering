package dao;

import entity.FileDoc;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ModuleService;
import service.ProjectService;
import service.UserService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FileDocDaoImpl implements FileDocDao {
    private static final Logger log = LoggerFactory.getLogger(FileDocDaoImpl.class);

    private static final String INSERT_FILEDOC = "INSERT INTO filedoc (description, lastModified, status, staffId," +
            " moduleId, projectId, fileContent) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String FIND_FILDOC_BY_ID = "SELECT description, lastModified, status, staffId, moduleId, projectId, fileContent" +
            " FROM filedoc WHERE id = ?";
    private static final String FIND_ALL_FILEDOCS = "SELECT * FROM filedoc";
    private static final String FIND_ALL_FILEDOCS_LIMIT = "SELECT * FROM filedoc LIMIT ? OFFSET ?";
    private static final String UPDATE_FILEDOC = "UPDATE filedoc SET description = ?, lastModified = ?, status = ?, staffId = ?," +
            " moduleId = ?, projectId = ?, fileContent = ? WHERE id = ?";
    private static final String DELETE_FILEDOC_BY_ID = "DELETE FROM filedoc WHERE id = ?";

    private final Connection connection;

    public FileDocDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public FileDoc insert(FileDoc fileDoc) throws DaoException {
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
            if (fileDoc.getModule() != null) {
                preparedStatement.setInt(5, fileDoc.getModule().getId());
            } else {
                preparedStatement.setObject(5, null);
            }
            preparedStatement.setInt(6, fileDoc.getProject().getId());
            preparedStatement.setBinaryStream(7, fileDoc.getFileContent());// change to byte array if necessary
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            preparedStatement.executeUpdate();
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
    public FileDoc findById(int id) throws DaoException {
        UserService userService = new UserService();
        ModuleService moduleService = new ModuleService();
        ProjectService projectService = new ProjectService();
        FileDoc fileDoc = new FileDoc();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(FIND_FILDOC_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            fileDoc.setId(resultSet.getInt("id"));
            fileDoc.setDescription(resultSet.getString("description"));
            fileDoc.setLastModified(new LocalDate(resultSet.getDate("lastModified")));
            fileDoc.setStatus(FileDoc.Status.valueOf(resultSet.getString("status")));
            fileDoc.setUser(userService.findUserById(Integer.valueOf(resultSet.getString("staffId"))));
            if (resultSet.getString("moduleId") != null) {
                fileDoc.setModule(moduleService.findModuleById(Integer.valueOf(resultSet.getString("moduleId"))));
            }
            fileDoc.setProject(projectService.findProjectById(Integer.valueOf(resultSet.getString("projectId"))));
            fileDoc.setFileContent(resultSet.getBinaryStream("fileContent"));
            return fileDoc;
        } catch (SQLException e) {
            log.debug("Failed to FIND_FILDOC_BY_ID");
            throw new DaoException("SQL FIND_FILDOC_BY_ID error.", e);
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
    public List<FileDoc> findAll() throws DaoException {
        UserService userService = new UserService();
        ModuleService moduleService = new ModuleService();
        ProjectService projectService = new ProjectService();
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
                fileDoc.setUser(userService.findUserById(Integer.valueOf(resultSet.getString("staffId"))));
                if (resultSet.getString("moduleId") != null) {
                    fileDoc.setModule(moduleService.findModuleById(Integer.valueOf(resultSet.getString("moduleId"))));
                }
                fileDoc.setProject(projectService.findProjectById(Integer.valueOf(resultSet.getString("projectId"))));
                fileDoc.setFileContent(resultSet.getBinaryStream("fileContent"));
                fileDocs.add(fileDoc);
            }
        } catch (SQLException e) {
            log.debug("Failed to FIND_ALL_FILEDOCS");
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
    public List<FileDoc> findAll(int start, int count) throws DaoException {
        return null;
    }

    @Override
    public void update(FileDoc fileDoc) throws DaoException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(UPDATE_FILEDOC);
            preparedStatement.setString(1, fileDoc.getDescription());
            preparedStatement.setDate(2, new java.sql.Date(fileDoc.getLastModified().toDate().getTime()));
            preparedStatement.setString(3, fileDoc.getStatus().name());
            preparedStatement.setInt(4, fileDoc.getUser().getId());
            if (fileDoc.getModule() != null) {
                preparedStatement.setInt(5, fileDoc.getModule().getId());
            } else {
                preparedStatement.setInt(5, new Integer(null));
            }
            preparedStatement.setInt(6, fileDoc.getProject().getId());
            preparedStatement.setBinaryStream(7, fileDoc.getFileContent());
            preparedStatement.setInt(8, fileDoc.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.debug("Failed to UPDATE_FILEDOC");
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
    public boolean delete(int id) throws DaoException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(DELETE_FILEDOC_BY_ID);
            preparedStatement.setInt(1, id);
            return (preparedStatement.executeUpdate() != 0);
        } catch (SQLException e) {
            log.debug("Failed to DELETE_FILEDOC_BY_ID");
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
