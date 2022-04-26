package ru.nsu.vaulin.ris3.controller;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.nsu.vaulin.ris3.model.dto.NodeDto;
import ru.nsu.vaulin.ris3.service.node.NodeService;

import java.util.List;

@RestController
@RequestMapping("/node")
@AllArgsConstructor
public class NodeController {
    private final NodeService nodeService;

    @GetMapping("/{nodeId}")
    public ResponseEntity<NodeDto> get(@PathVariable Long nodeId) {
        return ResponseEntity.ok(nodeService.read(nodeId));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody NodeDto nodeDto) {
        nodeService.delete(nodeDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody NodeDto nodeDto) {
        nodeService.create(nodeDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity<Void> update(@RequestBody NodeDto nodeDto) {
        nodeService.update(nodeDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/find")
    public ResponseEntity<List<NodeDto>> findInArea(
            @RequestParam("rad") double radius,
            @RequestParam("lon") double lon,
            @RequestParam("lat") double lat
    ) {
        return ResponseEntity.ok(nodeService.findInArea(radius, lon, lat));
    }
}
