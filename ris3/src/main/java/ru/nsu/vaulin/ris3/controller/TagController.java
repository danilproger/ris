package ru.nsu.vaulin.ris3.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.vaulin.ris3.model.dto.TagDto;
import ru.nsu.vaulin.ris3.service.tag.TagService;

@RestController
@RequestMapping("/tag")
@AllArgsConstructor
public class TagController {
    private final TagService tagService;

    @GetMapping("/{tagId}")
    public ResponseEntity<TagDto> get(@PathVariable Integer tagId) {
        return ResponseEntity.ok(tagService.read(tagId));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody TagDto tagDto) {
        tagService.delete(tagDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody TagDto tagDto) {
        tagService.create(tagDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity<Void> update(@RequestBody TagDto tagDto) {
        tagService.update(tagDto);
        return ResponseEntity.ok().build();
    }
}
