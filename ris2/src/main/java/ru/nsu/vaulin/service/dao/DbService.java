package ru.nsu.vaulin.service.dao;

import generated.Node;
import lombok.SneakyThrows;
import ru.nsu.vaulin.service.dao.nodedao.BatchNodeDao;
import ru.nsu.vaulin.service.dao.nodedao.PrStNodeDao;
import ru.nsu.vaulin.service.dao.osm.OsmDao;
import ru.nsu.vaulin.service.dao.osm.OsmDaoImpl;
import ru.nsu.vaulin.service.dao.tagdao.BatchTagDao;
import ru.nsu.vaulin.service.dao.tagdao.InsertTagDao;
import ru.nsu.vaulin.service.dao.tagdao.PrStTagDao;
import ru.nsu.vaulin.service.dao.nodedao.InsertNodeDao;

import java.sql.SQLException;
import java.util.Map;

import static ru.nsu.vaulin.model.ModelMapper.toEntity;

public class DbService {
    private static final DbService instance = new DbService();
    public static final int BATCH_SIZE = 1024;
    private final Map<SavingMode, OsmDao> modeToSaveStrategy;

    public void save(SavingMode mode, Node node) throws SQLException {
        modeToSaveStrategy.get(mode).save(toEntity(node));
    }

    public void complete(SavingMode mode) throws SQLException {
        modeToSaveStrategy.get(mode).complete();
    }

    @SneakyThrows
    private DbService() {
        DbConnection connection = DbConnection.instance();
        modeToSaveStrategy = Map.of(
                SavingMode.INSERT,              new OsmDaoImpl(new InsertNodeDao(connection.getConnection().createStatement()), new InsertTagDao(connection.getConnection().createStatement())),
                SavingMode.PREPARED_STATEMENT,  new OsmDaoImpl(new PrStNodeDao(), new PrStTagDao()),
                SavingMode.BATCH,               new OsmDaoImpl(new BatchNodeDao(), new BatchTagDao())
        );
    }

    public static DbService instance() {
        return instance;
    }

    public enum SavingMode {
        INSERT,
        PREPARED_STATEMENT,
        BATCH
    }
}
