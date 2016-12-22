# Words counter
Command-line Java program that counts unique words from a text file and lists the top N occurrences (Space is words divider)

## Build application
### Required
 - [Java 8](http://www.oracle.com/technetwork/java/javase/overview/java8-2100321.html)
 - [Apache Maven](https://maven.apache.org)

To build application use command:

```bash
mvn package
```

Now You can see generated JAR file in target folder

```bash
target/wordscounter-1.0-SNAPSHOT.jar
```

## Run application

To run application two parameters are required:
 -  -i,--input <arg>   input file path
 -  -n,--top <arg>     the top 'N' occurrences

For example:

```bash
java -jar target/wordscounter-1.0-SNAPSHOT.jar -n 10 -i demo.txt
```

Output:

```
Top 10 occurrences:
        the : 8
        of : 8
        in : 6
        from : 6
        a : 5
        Lorem : 5
        by : 4
        Ipsum : 4
        and : 4
        is : 3
```
