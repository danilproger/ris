package ru.nsu.vaulin.ris3.service.xmlparser;

import generated.Node;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.utils.FileNameUtils;
import org.springframework.stereotype.Service;
import ru.nsu.vaulin.ris3.model.ModelMapper;
import ru.nsu.vaulin.ris3.service.node.NodeService;
import ru.nsu.vaulin.ris3.service.tag.TagService;

import javax.transaction.Transactional;
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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@AllArgsConstructor
@Service
@Slf4j
public class DbSaverOsmXmlParser implements OsmXmlParser {
    private static final String TAG_NODE = "node";

    private final XMLInputFactory inputFactory = XMLInputFactory.newInstance();
    private final Set<String> parsedFiles = new HashSet<>();
    private final TagService tagService;
    private final NodeService nodeService;
    private final ModelMapper mapper;

    @Override
    @SneakyThrows
    public void parse(String resources) {
        if (parsedFiles.contains(getFileName(resources))) {
            log.info("File {} already parsed, pass", resources);
            return;
        }
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
                        saveNode(node);
                        continue;
                    }
                }
                xmlEventReader.nextEvent();
            }

        } catch (FileNotFoundException ex) {
            log.error("File {} was not found", resources, ex);
        } catch (IOException ex) {
            log.error("Input exception with file: {}", resources, ex);
        } catch (JAXBException ex) {
            log.error("Error with JAXB", ex);
        } catch (XMLStreamException ex) {
            log.error("Error while parsing xml", ex);
        }

        parsedFiles.add(resources);
    }

    private void saveNode(Node node) {
        nodeService.create(mapper.map(node));
        node.getTag().forEach(
                it -> tagService.create(mapper.map(it, node))
        );
    }

    private String getFileName(String path) {
        File f = new File(path);
        return f.getName();
    }
}
