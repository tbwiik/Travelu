# Integration tests

This project contains testing of each individual module which is written about in [testing approach](/docs/quality-assurance/testingapproach.md). However, testing if each module works together is also important. This is what integration tests are for. Integration tests check if the communication between the modules work together as expected by sending requests between them through the REST API. This is done by initializing a client on a Springboot server and then checking if the server responds as expected on the different requests.

The integration test does not check if the logic in each individual module is correct since this is done in each seperate module. Rather it tests the communication between the modules.

## Running the integration tests

To run the tests, in terminal:

- `cd travelu` to change into project-folder
- `mvn install` to run the tests

To run only the integration tests, in terminal:
- `mvn -pl integrationtests test` to run the integration tests

**Note:** A server cannot be running when running the integration tests.
