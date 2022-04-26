package ru.nsu.vaulin.service.dao.osm;

import ru.nsu.vaulin.model.entity.NodeEntity;
import ru.nsu.vaulin.service.dao.DbConnection;
import ru.nsu.vaulin.service.dao.nodedao.NodeDao;
import ru.nsu.vaulin.service.dao.tagdao.TagDao;

import java.sql.SQLException;

import static ru.nsu.vaulin.model.ModelMapper.toEntity;

public class OsmDaoImpl implements OsmDao {
    private final NodeDao nodeDao;
    private final TagDao tagDao;

    public OsmDaoImpl(NodeDao nodeDao, TagDao tagDao) {
        this.nodeDao = nodeDao;
        this.tagDao = tagDao;
    }

    @Override

    public void save(NodeEntity nodeEntity) throws SQLException {
        nodeDao.save(nodeEntity);
        nodeEntity.getTags().forEach(tagDao::save);
    }

    @Override
    public void complete() throws SQLException {
        try {
            nodeDao.complete();
            tagDao.complete();
            DbConnection.instance().getConnection().commit();
        } catch (SQLException ex) {
            DbConnection.instance().getConnection().rollback();
        }
    }
}
