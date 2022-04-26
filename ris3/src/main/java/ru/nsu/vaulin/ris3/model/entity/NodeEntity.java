package ru.nsu.vaulin.ris3.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name=NodeEntity.NODE_TABLE_NAME)
public class NodeEntity {
    public static final String NODE_TABLE_NAME = "NODE";

    @Id
    private Long id;

    @Column(name = "USER_NAME", nullable = false)
    private String userName;

    @Column(name = "LONGITUDE", nullable = false)
    private Double longitude;

    @Column(name = "LATITUDE", nullable = false)
    private Double latitude;

    @OneToMany
    @JoinColumn(name = "NODE", referencedColumnName = "ID")
    private List<TagEntity> tags;

}
