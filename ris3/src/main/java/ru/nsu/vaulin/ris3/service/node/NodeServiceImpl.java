package ru.nsu.vaulin.ris3.service.node;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.vaulin.ris3.model.ModelMapper;
import ru.nsu.vaulin.ris3.model.dto.NodeDto;
import ru.nsu.vaulin.ris3.model.entity.NodeEntity;
import ru.nsu.vaulin.ris3.repository.NodeRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NodeServiceImpl implements NodeService {
    private final NodeRepository nodeRepository;
    private final ModelMapper modelMapper;

    @Override
    public void create(NodeDto node) {
        nodeRepository.save(modelMapper.map(node));
    }

    @Override
    public void delete(NodeDto node) {
        nodeRepository.delete(modelMapper.map(node));
    }

    @Override
    public void update(NodeDto node) {
        Optional<NodeEntity> found = nodeRepository.findById(node.getId());
        if (found.isPresent()) {
            NodeEntity entity = found.get();
            entity.setUserName(node.getUserName());
            entity.setLatitude(node.getLatitude());
            entity.setLongitude(node.getLongitude());
            nodeRepository.save(entity);
        }
    }

    @Override
    public NodeDto read(Long nodeId) {
        Optional<NodeEntity> found = nodeRepository.findById(nodeId);
        return found.map(modelMapper::map).orElse(null);
    }

    @Override
    @Transactional
    public List<NodeDto> findInArea(double radius, double lon, double lat) {
        return nodeRepository.findInArea(radius, lon, lat)
                .stream().map(modelMapper::map).collect(Collectors.toList());
    }
}
