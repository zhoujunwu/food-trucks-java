

# food-trucks web app minimum version


## 1. the /api folder to run backend restful API, to parase food trucks info from csv, and provide search feature
Java 17 + Spingboot 3.1.8

### way 1: run backend api in CLI
go to /api/foodsearch folder, and run command at CLI

```mvn clean package```

then run

```java -jar target/foodsearch-0.0.1-SNAPSHOT.jar ```

default port is 8080 to listen request

### way 2: run backend api with Intellij IDEA, run below command to start app
```.\mvnw spring-boot:run```

### test api with swagger, open 

```host-addr/swagger-ui/index.html```  to do debug in swagger page


## 2. the /ui folder to run a single page at browser, to call backend api to get search result of foods
change the ```API_URL``` in index.html file according to backend api endpoint 


## 3. TODO
- secuity check
- search pattern
- auto deploy
- to show map of address
- others
