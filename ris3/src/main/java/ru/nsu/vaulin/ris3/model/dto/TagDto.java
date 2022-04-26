package ru.nsu.vaulin.ris3.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagDto {
    private Integer id;
    private Long node;
    private String key;
    private String value;
}
