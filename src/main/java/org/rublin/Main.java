package org.rublin;

import org.rublin.repository.JdbcRepository;
import org.rublin.tcp.TcpSender;
import org.slf4j.Logger;

import java.io.*;
import java.util.List;
import java.util.Properties;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Read property file and run loop
 * reading sql database, using repository {@link JdbcRepository}
 * and send TCP messages to server using sender {@link TcpSender}
 *
 * @author Ruslan Sheremet
 * @see org.rublin.repository.Repository
 * @see org.rublin.tcp.Sender
 * @since 1.5
 */
public class Main {

    private static final Logger LOG = getLogger(Main.class);

    public static Properties properties = new Properties();

    public static void main(String[] args) {
        List<String> list;
        int lastId = 0;
        String filename = null;
        /**
         * Trying to read properties file using argument
         */
        if (args.length > 0) {
            if (readProperty(args[0])) {
                lastId = Integer.valueOf(properties.getProperty("id.lastId"));
                filename = args[0];
            }
        } else {
            /**
             * If argument not configured, trying to find default properties file
             */
            LOG.info("Properties file is not configured\n\t\t\tTrying to read default property");
            if (readProperty("sql-to-tcp.properties")) {
                lastId = Integer.valueOf(properties.getProperty("id.lastId"));
                filename = "sql-to-tcp.properties";
            } else {
                LOG.info("I can't work without properties. \nPlease, read README at https://github.com/rublin/SQLtoTCP");
            }
        }
        LOG.info("lastId set to {}", lastId);
        String table = properties.getProperty("db.dbName") + "." + properties.getProperty("db.table");
        while (true && filename != null) {
            list = JdbcRepository.getRepository().getLastRecords(lastId, table);
            if (list.size() > 0) {
                list.forEach(s -> TcpSender.getSender().sendMessage(s));
                lastId = getLastId(list);
                saveLastId(lastId, filename);
                LOG.info("{} messages send successful. LastID: {}", list.size(), lastId);
            }
        }
    }

    /**
     * Try to read property file and return result: true if success and false if not
     *
     * @param filename path to file
     * @return true if success and false if not
     */
    private static boolean readProperty(String filename) {
        try (FileInputStream input = new FileInputStream(filename)) {
            properties.load(input);
            LOG.info("Properties file {} load success", filename);
            return true;
        } catch (Exception e) {
            LOG.error("Properties file loading failed. Error: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Get last id from rows
     *
     * @param list List of rows (string {@link String}
     * @return int last id
     */
    private static int getLastId(List<String> list) {
        String s = list.get(list.size() - 1);
        return Integer.valueOf(
                s.substring(0, s.indexOf(","))
        );
    }

    /**
     * Store last id into properties file
     *
     * @param id last id
     * @param filename properties file
     */
    private static void saveLastId(int id, String filename) {
        try (FileOutputStream outputStream = new FileOutputStream(filename)) {
            properties.setProperty("id.lastId", String.valueOf(id));
            properties.store(outputStream, "Properties");
            outputStream.close();
        } catch (Exception e) {
            LOG.error("Write to store lastId to property file. Error is {}", e.getMessage());
        }
    }
}
