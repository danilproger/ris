package ru.nsu.vaulin.service.dao.tagdao;

import lombok.SneakyThrows;
import ru.nsu.vaulin.model.entity.TagEntity;
import ru.nsu.vaulin.service.dao.DbConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PrStTagDao implements TagDao {
    private static final DbConnection dbConnection = DbConnection.instance();
    private final PreparedStatement statement;

    public PrStTagDao() {
        try {
            String pattern = "INSERT INTO TAG (NODE, KEY, VALUE) " +
                    "VALUES (?, ?, ?);";
            this.statement = dbConnection.getConnection().prepareStatement(pattern);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void save(TagEntity tag) {
        try {
            statement.setLong(1, tag.getNodeId());
            statement.setString(2, tag.getKey());
            statement.setString(3, tag.getValue());
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    @SneakyThrows
    public void complete() {
        statement.close();
    }
}
