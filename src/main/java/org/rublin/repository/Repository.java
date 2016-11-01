package org.rublin.repository;

import java.util.List;

/**
 * Repository provides connections to SQL database to the database
 *
 * @author Ruslan Sheremet
 * @see
 * @since 1.0
 */
public interface Repository {



    /**
     * Returns the list of all records from required table
     *
     * @param table the path to table in format databaseName.tableName
     * @return the list of all records
     *
     */
    List<String> getAllRecords(String table);

    /**
     * Returns the list of all records after id from required table
     *
     * @param id the id of last entry (not include)
     * @return the list of new records (when id is bigger)
     */
    List<String> getLastRecords(int id, String table);

    /**
     * Close statement and connection
     */
    void closeRepository();
}
