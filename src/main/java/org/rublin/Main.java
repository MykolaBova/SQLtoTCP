package org.rublin;

import org.rublin.repository.JdbcRepository;
import org.rublin.repository.Repository;
import org.rublin.tcp.Sender;
import org.rublin.tcp.TcpSender;
import org.slf4j.Logger;

import java.util.List;
import java.util.ResourceBundle;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * ???
 *
 * @author Ruslan Sheremet
 * @see
 * @since 1.0
 */
public class Main {

    private static final Logger LOG = getLogger(Main.class);

    public static final ResourceBundle MYSQL = ResourceBundle.getBundle("db.mysql");
    public static final String DATABASE_NAME = MYSQL.getString("database.dbName");
    public static final String LOGIN = MYSQL.getString("database.username");
    public static final String PASSWORD = MYSQL.getString("database.password");
    public static final String TABLE = DATABASE_NAME + "." + MYSQL.getString("database.table");

    public static final ResourceBundle TCP = ResourceBundle.getBundle("tcp.server");
    public static final String SERVER_IP = TCP.getString("server.ip");
    public static final int SERVER_PORT = Integer.valueOf(TCP.getString("server.port"));


    private static final Repository repository = JdbcRepository.getRepository();
    private static final Sender sender = TcpSender.getSender();

    public static void main(String[] args) {
        List<String> list = repository.getAllRecords(TABLE);
        list.forEach(s -> sender.sendMessage(s));
        int lastId = getLastId(list);
        LOG.info("{} messages send successful. LastID: {}", list.size(), lastId);
        while (true) {
            list = repository.getLastRecords(lastId, TABLE);
            if (list.size() > 0) {
                list.forEach(s -> sender.sendMessage(s));
                lastId = getLastId(list);
                LOG.info("{} messages send successful. LastID: {}", list.size(), lastId);
            }
        }

//        repository.closeRepository();
    }

    private static int getLastId(List<String> list) {
        String s = list.get(list.size() - 1);
        return Integer.valueOf(
                s.substring(0, s.indexOf(","))
        );
    }
}
