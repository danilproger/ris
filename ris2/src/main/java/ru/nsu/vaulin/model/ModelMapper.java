package ru.nsu.vaulin.model;

import generated.Node;
import generated.Tag;
import ru.nsu.vaulin.model.entity.NodeEntity;
import ru.nsu.vaulin.model.entity.TagEntity;

import java.util.stream.Collectors;

public class ModelMapper {
    public static NodeEntity toEntity(Node node) {
        return new NodeEntity(
                node.getId().longValue(),
                node.getLat(),
                node.getLon(),
                node.getUser(),
                node.getTag().stream().map(it -> toEntity(it, node.getId().longValue())).collect(Collectors.toList()));
    }

    public static TagEntity toEntity(Tag tag, long outerNodeId) {
        return new TagEntity(outerNodeId, tag.getK(), tag.getV());
    }
}
