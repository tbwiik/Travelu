# Client

The client is responsible for handling requests to the server for frontend.

## Client class

The client class is the only class in the module.
When instantiated server-url and port must be defined, but filepaths are preconfigured.
This means that the server can be hosted and accessed by the client from everywhere as long as filepath match.

The class contain some boilerplate code for the different types of generic requests. These are mainly written through the associated javadoc, but also with inspiration from [another IT1901-project](https://github.com/arrangabriel/IT1901-project-gr23/blob/master/get-fit/client/src/main/java/client/LogClient.java) to be able to understand what was going on.

## Request Handling
There are mainly two ways to run data through the server:
1. Big chunks of data in and out, but less requests
2. Small chunks of data in and out, but more requests

We have chosen to go for a solution 2. with more smaller requests, because this reduce redundant traffic and is also safer from a cyber-security point of view.