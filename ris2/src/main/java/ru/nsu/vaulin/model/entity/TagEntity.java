package ru.nsu.vaulin.model.entity;

public class TagEntity {
    private Long nodeId;
    private String key;
    private String value;

    public TagEntity(Long nodeId, String key, String value) {
        this.nodeId = nodeId;
        this.key = key;
        this.value = value;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
