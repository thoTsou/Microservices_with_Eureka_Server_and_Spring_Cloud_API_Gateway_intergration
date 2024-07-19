<h3>Example of microservices standing behind an API-Gateway which uses an Eureka discovery server in order
to route incoming client HTTP traffic to the proper microservice. API-Gateway
also has the responsibility to check if requests are authenticated before routing them.</h3>

<h4>Technologies used</h4>
- Spring Boot<br/>
- Spring Cloud API gateway<br/>
- Spring Cloud Netflix - Eureka Server & Client<br/>
- MySQL Database<br/>
- io.jsonwebtoken:jjwt<br/>

<p>
Details regarding how to run the project locally are provided at the bottom of this file.
Before that, please read the project description above.
</p>

![](/sample-images/1_43NgBoAW6h-vZTgyknM8xw.jpg)

<p>
In this example I set up an Eureka server and three Eureka clients.
One of those three acts as an API Gateway.
The above picture provides the architecture of this project.
</p>

<p>
In this project, API Gateway is not only responsible for routing client requests but it
also handles user authentication.
</p>

<p>
API gateway only allows un-authenticated requests to the auth-service microservice.
This microservice handles user registration and login.
A user has to register first providing an email-password combination (auth-service provides a register API)
and then user has to login with the same email-password combination (auth-service provides a login API)
in order to obtain an access JWT.
</p>

<p>
Client can then use the obtained access JWT (as request header) in order to make 
requests to all microservices behind the API Gateway. If a request is un-authenticated
or JWT is expired or corrupted then API Gateway will return a proper message
to the client and will not route client's request.
</p>

<p>
test-service-one provides a simple API (/quote/random-quote) which returns
random quotes.
</p>


In order to run and test the project you need to:
- Start discoveryserver app (this is the Eureka Server).
- Start auth-service microservice. In order to do that you first need 
to set-up an MySQL database by the name "microservicesDB"
and specify your own username and 
password in microservice's properties file.
- Start test-service-one app.
- Start api-gateway app (this is the API Gateway).
- Start the Angular client using ng serve --open.

<p>
Then, using the Angular client (https://github.com/thoTsou/Angular_Client_for_microservices_project) first register then login.
After login choose a quotes category from the dropdown menu and click Display Quote button.
</p>




