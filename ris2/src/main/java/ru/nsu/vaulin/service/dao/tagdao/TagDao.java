package ru.nsu.vaulin.service.dao.tagdao;

import ru.nsu.vaulin.model.entity.TagEntity;

import java.sql.SQLException;

public interface TagDao {
    void save(TagEntity tag);
    default void complete(){}
}
