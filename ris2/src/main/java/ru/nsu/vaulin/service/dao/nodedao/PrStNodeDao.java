package ru.nsu.vaulin.service.dao.nodedao;

import lombok.SneakyThrows;
import ru.nsu.vaulin.model.entity.NodeEntity;
import ru.nsu.vaulin.service.dao.DbConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PrStNodeDao implements NodeDao {
    private static final DbConnection dbConnection = DbConnection.instance();
    private final PreparedStatement statement;

    public PrStNodeDao() {
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
    public void save(NodeEntity node) {
        try {
            statement.setLong(1, node.getId());
            statement.setString(2, node.getUser());
            statement.setDouble(3, node.getLongitude());
            statement.setDouble(4, node.getLatitude());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    @SneakyThrows
    public void complete() {
        statement.close();
    }
}
