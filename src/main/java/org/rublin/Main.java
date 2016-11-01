package org.rublin;

import org.rublin.repository.JdbcRepository;
import org.rublin.repository.Repository;

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


    private static final Repository repository = JdbcRepository.getRepository();

    public static void main(String[] args) {
        repository.getAllRecords(TABLE).forEach(System.out::println);
    }
}
