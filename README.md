# SQL to TCP
### Version is *1.1-beta*

This util allow to read a SQL database and send every row into TCP port.

## Usage

1. [Download](https://github.com/rublin/SQLtoTCP/releases) or build jar package using maven: **mvn clean compile assembly:single**
2. Create [sql-to-tcp.properties](#sql-to-tcp.properties-file) to connect to DB and place it near the jar file
3. [Run](#Run-jar-package) jar package
4. See log file in log directory

### sql-to-tcp.properties file

```properties
# DataBase connection properties
database.username=gis
database.password=P@ssw0rd
database.driverClassName=com.mysql.jdbc.Driver
database.dbName=test
database.table=cars
database.url=jdbc:mysql://192.168.1.121:3306/test
# Last ID (the util will read rows after this value)
database.lastId=300
# TCP server properties
server.port=5569
server.ip=192.168.1.125
```

### Run jar package

```cmd
java -jar sql-to-tcp.jar <path-to-properies-file>
```

Where <path-to-properies-file> is full path to sql-to-tcp.properties file. For example, *java -jar sql-to-tcp.jar c:\prop\sql-to-tcp.properties*
If you place the properties file near the jar file, you can run just:

```cmd
java -jar sql-to-tcp.jar
```