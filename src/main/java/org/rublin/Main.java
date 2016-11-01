package org.rublin;

import org.rublin.repository.JdbcRepository;
import org.rublin.repository.Repository;
import org.rublin.tcp.Sender;
import org.rublin.tcp.TcpSender;

import java.util.ResourceBundle;

/**
 * ???
 *
 * @author Ruslan Sheremet
 * @see
 * @since 1.0
 */
public class Main {

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
//        repository.getAllRecords(TABLE).forEach(System.out::println);
        repository.getLastRecords(200, TABLE).forEach(s -> sender.sendMessage(s));
        repository.closeRepository();
    }
}
