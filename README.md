# UserProcessApi


1) User Registration ( inculdes sending an email out with a token) and than verification after hitting the url in the email.
2) User Login (Using JWT that allows the user to be logged in for 15 minutes (it is configurable) ) 
3) User Password Reset

This User Process API needs to be deployed on wildfly server version 9 Final that is connected to a postgresql DB version -9.4
There is a script in the package that seeds the database, after that the api can be tested by hitting the api. 

To design the software architecture the  used design pattern is ECB (Entity Control Boundary Adapter).

The adapter contains the endpoints (GET, POST, PUT)

The control layer contains the CRUD operations. 

The Boundary layer contains the business logic.

The entity layer contains the JAVA objects for mapping the database.

I used JPA for the database modelling.
