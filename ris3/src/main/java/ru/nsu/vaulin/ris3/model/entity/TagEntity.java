package ru.nsu.vaulin.ris3.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name=TagEntity.TAG_TABLE_NAME)
public class TagEntity {
    public static final String TAG_TABLE_NAME = "TAG";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NODE", nullable = false)
    private Long node;

    @Column(name = "KEY", nullable = false)
    private String key;

    @Column(name = "VALUE", nullable = false)
    private String value;
}
