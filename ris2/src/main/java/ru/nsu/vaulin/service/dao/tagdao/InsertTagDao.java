package ru.nsu.vaulin.service.dao.tagdao;

import lombok.SneakyThrows;
import ru.nsu.vaulin.model.entity.TagEntity;
import ru.nsu.vaulin.service.dao.DbConnection;

import java.sql.SQLException;
import java.sql.Statement;

public class InsertTagDao implements TagDao {
    private static final String INSERT_PATTERN =
            "INSERT INTO TAG (NODE, KEY, VALUE) " +
                    "VALUES (%s, '%s', '%s');";

    private final Statement statement;

    public InsertTagDao(Statement statement) {
        this.statement = statement;
    }

    @Override
    @SneakyThrows
    public void save(TagEntity tag) {
        String sql = String.format(
                INSERT_PATTERN,
                tag.getNodeId(),
                tag.getKey().replace("'", "''"),
                tag.getValue().replace("'", "''")
        );
        statement.execute(sql);
    }

    @Override
    @SneakyThrows
    public void complete() {
        statement.close();
    }
}
