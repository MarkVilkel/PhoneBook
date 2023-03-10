# PhoneBook

A microservice to determine the country by phone number.
```
To compile: mvn clean install
To run: mvn spring-boot:run
To launch tests: mvn test
To launch tests with reports generation: mvn surefire-report:report
```
After running go to the browser and browse to [address](http://localhost:8080/)

Test reports could be found at **/target/surefire-reports/surefire-report.html**

Uncomment **http.proxyHost** property in **application.properties** if launching under the Proxy

The user enters a phone number, the system validates it and displays the country or error message.
For country codes use the table on [the page](https://en.wikipedia.org/wiki/List_of_country_calling_codes), it is necessary to load data from it
every time the service starts.

## Non-functional requirements

###### Backend:
1. Java 11 or 17
2. Spring Boot
3. Maven
4. HTTP, REST-WS with JSON data format.

###### Frontend:
1. Html
2. JavaScript
3. Supporting libraries - at your discretion.

###### Notes:
1. The application must be built and run from the command line, on port 8080. It should also be possible to run tests and view reports on them.
2. All calls to the application are done using REST-WS with JSON as the data format.
3. The appearance of the interface is unimportant, plain HTML will be fine.
4. For requests, use any AJAX-capable framework, you can just JQuery.
5. Data validation, tests are required.
