package ru.nsu.vaulin.model.entity;

import java.util.List;

public class NodeEntity {
    private Long id;
    private Double latitude;
    private Double longitude;
    private String user;
    private List<TagEntity> tags;

    public NodeEntity(Long nodeId, Double latitude, Double longitude, String user, List<TagEntity> tags) {
        this.id = nodeId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.user = user;
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<TagEntity> getTags() {
        return tags;
    }

    public void setTags(List<TagEntity> tags) {
        this.tags = tags;
    }
}
