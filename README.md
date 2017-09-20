# Entity Manager
## Set up
To prepare app for usage you need
* Create database
* Prepare persistence.xml
```
cp src/main/resources/META-INF/persistence.xml.dist src/main/resources/META-INF/persistence.xml
```
Insert your values instead of YOUR_DB_NAME, YOUR_DB_USER, YOUR_DB_PASSWORD
* Import db structure from
```
src/main/resources/db/migration/test.sql
```
* Now you can launch app
```
 gradle run
```
Or run method *main* from class *com.andersenlab.entity_manager.ConsoleApplication*
## Available commands
Acceptable command's format is:
```
'action' 'entity' [params]
```
Available commands are: *create|remove|show*

Available entities are: *customer|product|purchase*
# Tests
To run tests your need to provide *src/test/resources/META-INF/persistence.xml*