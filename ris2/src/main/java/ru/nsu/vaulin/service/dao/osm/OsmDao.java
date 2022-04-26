package ru.nsu.vaulin.service.dao.osm;

import ru.nsu.vaulin.model.entity.NodeEntity;

import java.sql.SQLException;

public interface OsmDao {
    void save(NodeEntity nodeEntity) throws SQLException;
    default void complete() throws SQLException {}
}
