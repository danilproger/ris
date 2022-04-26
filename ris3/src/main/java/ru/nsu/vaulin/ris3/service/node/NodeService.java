package ru.nsu.vaulin.ris3.service.node;


import ru.nsu.vaulin.ris3.model.dto.NodeDto;

import java.util.List;

public interface NodeService {
    void create(NodeDto node);
    void delete(NodeDto node);
    void update(NodeDto node);
    NodeDto read(Long nodeId);

    List<NodeDto> findInArea(
            double radius,
            double lon,
            double lat);
}
