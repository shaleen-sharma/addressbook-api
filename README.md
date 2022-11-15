# addressbook-api
The api saves the addressbook entries (addressBookId, personName, phoneNumber) for different users. The user name needs to be passed as a path variable.
The phone numbers will be saved in international format for consistency, ex. +61411111111. 

 
 #### Usage:

1. git clone the project into your workspace.
2. cd into the directory and perform in the following order of modules:
3. Maven wrapper is used for convenience so you dont have to install maven.
```
./mvnw clean install
```
3. Run Spring boot app using mvn wrapper command
```
./mvnw spring-boot:run
```
4. After the above command is finished and app is started use below postman collection to hit the endpoints.\
Postman collection:
https://www.getpostman.com/collections/d3f65bd63edf567e8553

To get a view of the database use below url after the app is running \
http://localhost:8080/addressbook/h2-console
```
jdbc url: jdbc:h2:mem:test
user:sa
pwd:sa
```
Tech Stack:\
Java11 \
H2 Database\
Springboot \
Spring Data JPA

Further improvements:
1. Add Spring Security for token validation
2. Better error handling and input validations
3. Missing tests

Things to know
1. set endpoint (setEntries() in Controller) can be used to save addressbook entries for different users.
2. To view the seed data checkout the data.sql file
