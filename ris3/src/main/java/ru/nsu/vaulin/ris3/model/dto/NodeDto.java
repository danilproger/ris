package ru.nsu.vaulin.ris3.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NodeDto {
    private Long id;
    private String userName;
    private Double longitude;
    private Double latitude;
    private List<TagDto> tags;
}
