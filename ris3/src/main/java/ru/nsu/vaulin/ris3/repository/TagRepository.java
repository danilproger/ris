package ru.nsu.vaulin.ris3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.vaulin.ris3.model.entity.TagEntity;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, Integer> {

}
