# Harbour Master Application
A very early demonstration of our project.

This project uses Swagger. Once running, you can view API details on the Swagger page at http://localhost:8080/swagger-ui/.

## Running with Maven
To build and run the program using only Maven, run the following command in this directory:

`$ mvn spring-boot:run`

## Docker
If you wish to use docker to run this program, build the docker image with:

`$ mvn spring-boot:build-image`

If it's your first time building a docker image, it may take a few minutes to download dependencies. Once you have successfully built the image, simply run:

`$ docker run -p 8080:8080 -t harbourmaster:1.0-SNAPSHOT`

The program will then be available at http://localhost:8080/. You can substitue the ports in the above command with whichever ones you wish to use.
