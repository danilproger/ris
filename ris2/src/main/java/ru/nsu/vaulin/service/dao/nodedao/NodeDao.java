package ru.nsu.vaulin.service.dao.nodedao;

import ru.nsu.vaulin.model.entity.NodeEntity;

import java.sql.SQLException;

public interface NodeDao {
    void save(NodeEntity node);
    default void complete() {}
}
