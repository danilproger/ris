package ru.nsu.vaulin.service.dao.nodedao;

import lombok.SneakyThrows;
import ru.nsu.vaulin.model.entity.NodeEntity;

import java.sql.Statement;

public class InsertNodeDao implements NodeDao {
    private static final String INSERT_PATTERN =
            "INSERT INTO NODE (ID, USER_NAME, LONGITUDE, LATITUDE) " +
            "VALUES (%s, '%s', %s, %s);";

    private final Statement statement;

    public InsertNodeDao(Statement statement) {
        this.statement = statement;
    }

    @Override
    @SneakyThrows
    public void save(NodeEntity node) {
        String sql = String.format(
                INSERT_PATTERN,
                node.getId(),
                node.getUser().replace("'", "''"),
                node.getLongitude(),
                node.getLatitude()
        );
        statement.execute(sql);
    }

    @Override
    @SneakyThrows
    public void complete()  {
        statement.close();
    }
}
