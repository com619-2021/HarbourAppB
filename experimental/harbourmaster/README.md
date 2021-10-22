# Harbour Master Prototype Application
A very early demonstration as to how REST is going to be handled.
Any REST endpoints in this project are subject to change.

To run the program, enter:

`mvn spring-boot:run`

in this directory.

Current endpoints for testing:

1. /api/test (HTTP GET): returns a simple message.

2. /api/bookPilot (HTTP POST): books a pilot. Requires a JSON POST request containing 'id', 'length', 'width', 'weight'.

Example:
```	   {
	   "id": "60",
	   "length": "250",
	   "width": "60",
	   "weight": "5000"
	   }```

TODO: include instructions regarding docker packaging.
