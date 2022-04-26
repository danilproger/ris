package ru.nsu.vaulin.service.xmlparser;

import ru.nsu.vaulin.model.UserAndKeyAttribute;

public interface OsmXmlParser {
    UserAndKeyAttribute parse(String resources);
}
