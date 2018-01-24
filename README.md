# Mailer API

A simple API which allows to send emails.

## Application functionality overview

Since I believe the main purpose of this exercise wasn't to implement a full blown email client, the delivered functionality is stripped down to required minimum.

The application exposes a REST/HTTP endpoint which expects:
- a recipient - a single email address,
- the subject of the email message,
- plaintext content of the email message.    

The endpoint performs validation on those inputs. All are required and recipient has to be a valid email address.

Service is integrated with two email API providers, [SendGrid](https://sendgrid.com/) and [Mailjet](https://www.mailjet.com/), fallback mechanism has been implemented as well using [Hystrix](https://github.com/Netflix/Hystrix). If both providers will fail to handle user's request a 503 message will be returned to the user.

Due to the limited amount of time I had I did not manage to implement a separate frontend service. However, the application uses [SpringFox](http://springfox.github.io/springfox/) to document exposed API endpoint. Apart from the documentation itself, SpringFox provides a sleek UI which allows to send requests to documented endpoints, so it is possible to easily send requests to the API.

### Demo instance

The application is deployed on Heroku:
- [https://rlewan-mailer-api.herokuapp.com/swagger-ui.html](https://rlewan-mailer-api.herokuapp.com/swagger-ui.html#!/emails45controller/sendEmailUsingPOST) - the API documentation page,
- [https://rlewan-mailer-api.herokuapp.com/emails](https://rlewan-mailer-api.herokuapp.com/emails) - the API endpoint itself.

Since Heroku free plan dynos are not exactly speed demons, I did experience some timeout failures from Hystrix, what effectively caused failure response being returned to the user even though the email was sent eventually. I increased default Hystrix timeouts from 500 to 2000 milliseconds to mitigate this, so hopefully that problem should go away.    

### If I had more time...

I would provide a separate frontend service, even though it would be quite simple. Probably a Node.js/Express application with a form and a confirmation page. I admit HTML/CSS are not among my top skills.

I would also provide health checks for downstream email API provider services. The application exposes a [`/health`](https://rlewan-mailer-api.herokuapp.com/health) endpoint (among other things) thanks to [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#production-ready), but it's nothing in there by default on this setup. Spring provides a [convenient way](https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-endpoints.html#production-ready-health) of doing this, would have to play around with API clients provided by SendGrid and Mailjet.

Another thing is that the application is open to the public at the moment, anyone can call the endpoint. I would add configure API key checking in the application. API key would have to be sent as an `Authorization` header on each call.
