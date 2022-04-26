package ru.nsu.vaulin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.nsu.vaulin.model.UserAndKeyAttribute;
import ru.nsu.vaulin.service.dao.DbConnection;
import ru.nsu.vaulin.service.dao.DbService;
import ru.nsu.vaulin.service.xmlparser.FileJaxbOsmXmlParser;
import ru.nsu.vaulin.service.xmlparser.OsmXmlParser;

import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        checkInputArguments(args);
        startApplication(args[0]);
    }

    private static void checkInputArguments(String[] args) {
        logger.info("Checking the number of input arguments...");

        if (args.length != 1) {
            throw new IllegalArgumentException("The number of input arguments must be 1");
        }

        logger.info("The number of input arguments is 2 - OK");
    }

    private static void runTest(String resources, DbService.SavingMode savingMode) throws Exception {
        System.out.println("Test with saving mode: " + savingMode);
        System.out.println("Staring...");

        long start = System.currentTimeMillis();

        DbConnection.instance().initDatabase();

        OsmXmlParser parser = new FileJaxbOsmXmlParser(savingMode);
        UserAndKeyAttribute result = parser.parse(resources);

        long end = System.currentTimeMillis();

        System.out.println("Test ended, it takes: " + (end-start) + " millis");
        System.out.println("Amount of nodes: " + getTotal("NODE"));
        System.out.println("Amount of tags: " + getTotal("TAG"));
        System.out.println();
    }

    private static int getTotal(String table) throws Exception {
        Statement statement = DbConnection.instance().getConnection().createStatement();
        ResultSet rs = statement.executeQuery("SELECT COUNT(*) AS total FROM " + table);
         rs.next();
         return rs.getInt("total");
    }

    private static void startApplication(String resources) throws Exception {
        DbConnection.instance().connect();
        for (var it: DbService.SavingMode.values()) {
            if (DbService.SavingMode.BATCH == it) {
                runTest(resources, it);
            }
        }
        DbConnection.instance().close();
    }
}
