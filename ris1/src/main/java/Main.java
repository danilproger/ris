import model.UserAndKeyAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.statwriter.ConsoleStatisticWriter;
import service.statwriter.StatisticWriter;
import service.xmlparser.FileOsmXmlParser;
import service.xmlparser.OsmXmlParser;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        checkInputArguments(args);
        startApplication(args[0]);
    }

    private static void checkInputArguments(String[] args) {
        logger.info("Checking the number of input arguments...");

        if (args.length != 1) {
            throw new IllegalArgumentException("The number of input arguments must be 1");
        }

        logger.info("The number of input arguments is 1 - OK");
    }

    private static void startApplication(String resources) {
        OsmXmlParser parser = new FileOsmXmlParser();
        StatisticWriter writer = new ConsoleStatisticWriter();

        UserAndKeyAttribute result = parser.parse(resources);
        if (result != null) {
            writer.write(result);
        }
    }
}
