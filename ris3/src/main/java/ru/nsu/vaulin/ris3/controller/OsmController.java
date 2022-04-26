package ru.nsu.vaulin.ris3.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.vaulin.ris3.service.xmlparser.OsmXmlParser;

@RestController
@RequestMapping("/osm")
@AllArgsConstructor
public class OsmController {
    private final OsmXmlParser parser;

    @PostMapping("/parse")
    ResponseEntity<?> parse(@RequestBody RequestPath path) {
        parser.parse(path.getPath());
        return ResponseEntity.ok("Parsed");
    }

    @Data
    private static class RequestPath {
        private String path;
    }
}
