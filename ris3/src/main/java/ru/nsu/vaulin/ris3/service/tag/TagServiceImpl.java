package ru.nsu.vaulin.ris3.service.tag;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.vaulin.ris3.model.ModelMapper;
import ru.nsu.vaulin.ris3.model.dto.TagDto;
import ru.nsu.vaulin.ris3.model.entity.TagEntity;
import ru.nsu.vaulin.ris3.repository.NodeRepository;
import ru.nsu.vaulin.ris3.repository.TagRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    @Override
    public void create(TagDto tag) {
        tagRepository.save(modelMapper.map(tag));
    }

    @Override
    public void delete(TagDto tag) {
        tagRepository.delete(modelMapper.map(tag));
    }

    @Override
    public void update(TagDto tag) {
        Optional<TagEntity> found = tagRepository.findById(tag.getId());
        if (found.isPresent()) {
            TagEntity entity = found.get();
            entity.setNode(tag.getNode());
            entity.setKey(tag.getKey());
            entity.setValue(tag.getValue());
            tagRepository.save(entity);
        }
    }

    @Override
    public TagDto read(Integer tagId) {
        Optional<TagEntity> found = tagRepository.findById(tagId);
        return found.map(modelMapper::map).orElse(null);
    }
}
