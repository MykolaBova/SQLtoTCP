# SQL to TCP
### Version is *1.0-alfa*

This util allow to read a SQL database and send every row into TCP port.

## Usage

1. Create [properties file](#DataBase-connection-file) to connect to DB
2. Build jar package using maven: **mvn clean compile assembly:single**
3. Run jar package: 

```cmd
java -jar sql-to-tcp-jar-with-dependencies.jar <path-to-properies-file>
```

### DataBase connection file

```properties
database.username=gis
database.password=P@ssw0rd
database.driverClassName=com.mysql.jdbc.Driver
database.dbName=test
database.table=cars
database.url=jdbc:mysql://192.168.1.121:3306/test
database.lastId=300
```