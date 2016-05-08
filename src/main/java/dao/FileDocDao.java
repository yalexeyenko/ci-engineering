package dao;

import entity.FileDoc;

import java.util.List;

public interface FileDocDao extends Dao<FileDoc> {
    List<FileDoc> findAllFileDocsByProjectId(int projectId);
}
