package ru.nsu.vaulin.service.dao.nodedao;

import lombok.SneakyThrows;
import ru.nsu.vaulin.model.entity.NodeEntity;
import ru.nsu.vaulin.service.dao.DbConnection;
import ru.nsu.vaulin.service.dao.DbService;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BatchNodeDao implements NodeDao {
    private static final DbConnection dbConnection = DbConnection.instance();
    private final PreparedStatement statement;
    private int batchCapacity = 0;

    public BatchNodeDao() {
        try {
            String pattern =
                    "INSERT INTO NODE (ID, USER_NAME, LONGITUDE, LATITUDE) " +
                            "VALUES (?, ?, ?, ?);";
            this.statement = dbConnection.getConnection().prepareStatement(pattern);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    @SneakyThrows
    public void save(NodeEntity node) {
        saveBatch(node);
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

    private void saveBatch(NodeEntity node) {
        try {
            statement.setLong(1, node.getId());
            statement.setString(2, node.getUser());
            statement.setDouble(3, node.getLongitude());
            statement.setDouble(4, node.getLatitude());
            statement.addBatch();
            batchCapacity++;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
