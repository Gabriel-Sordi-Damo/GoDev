# GoDev - Java

## Executing the project

 - [ ] Create a database named **godev** in the PostgreSQL server.<br> 
``
CREATE DATABASE godev; 
``
 - [ ] Ensure that 

> spring.datasource.username=postgres <br>
> spring.datasource.password=root
> 
Inside the 
> aplication.properties

Have the same values that are required to authenticate on Postgre

 
 - [ ] Compile and execute the 

> SpringbootGodevBackendApplication

 file inside 

> src\main\java\com\example\springbootgodevbackend

 - [ ] The server will run on port 8080. 
 
 The base url for the API calls is

> localhost:8080/api/

The calls follow the query parameters and body convention presented at the document **Teste prático Java Backend – GoDev Senior**

 Recommendation: Use **Postman** to execute que API calls, according to the document **Teste prático Java Backend – GoDev Senior**
