# complaints-kata

Create a REST service for managing complaints that allows for:
 - adding new complaints
 - editing complaint content
 - retrieving previously saved complaints

A complaint should include: 
 - product ID 
 - content 
 - creation date 
 - complainant 
 - country 
 - submission count

The "country" field should contain the country from which the client added the complaint,
based on their IP address (you can use any free service for this). 

Complaints should be unique by product ID and complainant. 

If a duplicate is attempted to be added, the "submission count" field should be incremented without editing other data.

Data should be saved in a database. 

The service should be implemented in Java or Kotlin.

The project should be buildable using Maven or Gradle. 

You can use any readily available technologies (database engines, libraries, frameworks). 

Make sure to follow good programming practices.