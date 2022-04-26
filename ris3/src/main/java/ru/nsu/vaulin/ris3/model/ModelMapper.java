package ru.nsu.vaulin.ris3.model;

import generated.Node;
import generated.Tag;

import org.springframework.stereotype.Service;

import ru.nsu.vaulin.ris3.model.dto.NodeDto;
import ru.nsu.vaulin.ris3.model.dto.TagDto;
import ru.nsu.vaulin.ris3.model.entity.NodeEntity;
import ru.nsu.vaulin.ris3.model.entity.TagEntity;

import java.util.stream.Collectors;

@Service
public class ModelMapper {
    public NodeEntity map(NodeDto dto) {
        return dto == null ? null : NodeEntity.builder()
                .id(dto.getId())
                .userName(dto.getUserName())
                .longitude(dto.getLongitude())
                .latitude(dto.getLatitude())
                .tags(dto.getTags() == null ? null : dto.getTags().stream().map(this::map).collect(Collectors.toList()))
                .build();
    }

    public TagEntity map(TagDto dto) {
        return dto == null ? null : TagEntity.builder()
                .id(dto.getId())
                .node(dto.getNode())
                .key(dto.getKey())
                .value(dto.getValue())
                .build();
    }

    public NodeDto map(NodeEntity node) {
        return node == null ? null : NodeDto.builder()
                .id(node.getId())
                .userName(node.getUserName())
                .longitude(node.getLongitude())
                .latitude(node.getLatitude())
                .tags(node.getTags() == null ? null : node.getTags().stream().map(this::map).collect(Collectors.toList()))
                .build();
    }

    public TagDto map(TagEntity tag) {
        return tag == null ? null : TagDto.builder()
                .id(tag.getId())
                .node(tag.getNode())
                .key(tag.getKey())
                .value(tag.getValue())
                .build();
    }

    public TagDto map(Tag tag, Node node) {
        return tag == null || node == null ? null : TagDto.builder()
                .node(node.getId().longValue())
                .key(tag.getK())
                .value(tag.getV())
                .build();
    }

    public NodeDto map(Node node) {
        return node == null ? null : NodeDto.builder()
                .id(node.getId().longValue())
                .userName(node.getUser())
                .longitude(node.getLon())
                .latitude(node.getLat())
                .build();
    }
}
