package ru.nsu.vaulin.service.dao.tagdao;

import lombok.SneakyThrows;
import ru.nsu.vaulin.model.entity.TagEntity;
import ru.nsu.vaulin.service.dao.DbConnection;
import ru.nsu.vaulin.service.dao.DbService;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BatchTagDao implements TagDao {
    private static final DbConnection dbConnection = DbConnection.instance();
    private final PreparedStatement statement;
    private int batchCapacity = 0;

    public BatchTagDao() {
        try {
            String pattern = "INSERT INTO TAG (NODE, KEY, VALUE) " +
                    "VALUES (?, ?, ?);";
            this.statement = dbConnection.getConnection().prepareStatement(pattern);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    @SneakyThrows
    public void save(TagEntity tag) {
        saveBatch(tag);
        if (batchCapacity >= DbService.BATCH_SIZE) {
            statement.executeBatch();
            batchCapacity = 0;
        }
    }

    @Override
    @SneakyThrows
    public void complete() {
        statement.executeBatch();
        statement.close();
    }

    private void saveBatch(TagEntity tag) {
        try {
            statement.setLong(1, tag.getNodeId());
            statement.setString(2, tag.getKey());
            statement.setString(3, tag.getValue());
            statement.addBatch();
            batchCapacity++;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
