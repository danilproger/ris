package service.xmlparser;

import model.UserAndKeyAttribute;

public interface OsmXmlParser {
    UserAndKeyAttribute parse(String resources);
}
