package ru.nsu.vaulin.service.xmlparser;

import generated.Node;
import ru.nsu.vaulin.model.UserAndKeyAttribute;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.nsu.vaulin.service.dao.DbService;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class FileJaxbOsmXmlParser implements OsmXmlParser {
    private static final Logger logger = LoggerFactory.getLogger(FileJaxbOsmXmlParser.class);
    private static final XMLInputFactory inputFactory = XMLInputFactory.newInstance();
    private static final String TAG_NODE = "node";
    private final DbService.SavingMode mode;

    public FileJaxbOsmXmlParser(DbService.SavingMode mode) {
        this.mode = mode;
    }

    @Override
    public UserAndKeyAttribute parse(String resources) {
        Map<String, Integer> usersMap = new HashMap<>();
        Map<String, Integer> keysMap = new HashMap<>();

        UserAndKeyAttribute result = new UserAndKeyAttribute(usersMap, keysMap);
        int counter = 0;

        try (InputStream bzIs = new BZip2CompressorInputStream(new BufferedInputStream(new FileInputStream(resources)))) {
            JAXBContext jaxbContext = JAXBContext.newInstance(Node.class);
            XMLEventReader xmlEventReader = inputFactory.createXMLEventReader(bzIs);
            while (xmlEventReader.hasNext()) {
                XMLEvent xmlEvent = xmlEventReader.peek();
                if (xmlEvent.isStartElement()) {
                    StartElement startElement = xmlEvent.asStartElement();
                    if (startElement.getName().getLocalPart().equals(TAG_NODE)) {
                        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                        Node node = (Node) unmarshaller.unmarshal(xmlEventReader);
                        getStatisticForUsersAndKeys(node, usersMap, keysMap);
                        try {
                            DbService.instance().save(mode, node);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        } finally {
                            counter++;
                        }
                        /*if (counter >= 100000) {
                            break;
                        }*/
                        continue;
                    }
                }
                xmlEventReader.nextEvent();
            }
            try {
                DbService.instance().complete(mode);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } catch (FileNotFoundException ex) {
            logger.error(String.format("File %s was not found", resources), ex);
        } catch (IOException ex) {
            logger.error(String.format("Input exception with file: %s", resources), ex);
        } catch (JAXBException ex) {
            logger.error("Error with JAXB", ex);
        } catch (XMLStreamException ex) {
            logger.error("Error while parsing xml", ex);
        }

        return result;
    }

    private void getStatisticForUsersAndKeys(Node node, Map<String, Integer> usersMap, Map<String, Integer> keysMap) {
        usersMap.merge(node.getUser(), 1, Integer::sum);
        node.getTag().forEach(it -> {
            keysMap.merge(it.getK(), 1, Integer::sum);
        });
    }
}
