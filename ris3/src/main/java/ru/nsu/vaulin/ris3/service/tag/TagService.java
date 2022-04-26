package ru.nsu.vaulin.ris3.service.tag;

import ru.nsu.vaulin.ris3.model.dto.TagDto;

public interface TagService {
    void create(TagDto tag);
    void delete(TagDto tag);
    void update(TagDto tag);
    TagDto read(Integer tagId);
}
