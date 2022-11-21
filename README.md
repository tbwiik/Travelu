[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2022/gr2219/gr2219.git)

# Group gr2219 repository

## Travelu

We are making an application called Travelu. We have written about its functionality in the [README.md](travelu/README.md) in the travelu folder.

### Group members

- Torbjørn Wiik
- Ken Van Nguyen
- Bendik Norli
- John Hausberg Anfindsen

# Project contents

This project contains:

- A [README.md](travelu/README.md) explaining the functionality of the app
- [User scenarios](docs/user-scenarios) for the different use cases of the app
- Documentation about [quality assurance](docs/quality-assurance) of the project
- Documentation of all [releases](docs/releases)
- [Diagrams](docs/diagrams) of the project's architecture and how the application works
- Documentation about our [development](docs/development) process

<br>

- The application's user interface controllers ([travelu/fxui/src/main/java/travelu/fxui](travelu/fxui/src/main/java/travelu/fxui))
- FXML files connected to these controllers ([resources/travelu/fxui](travelu/fxui/src/main/resources/travelu/fxui))
- A [README.md](/travelu/fxui/README.md) explaining how we implemented the user interface.
- The application's domain logic  ([travelu/core/src/main/java/travelu/core](travelu/core/src/main/java/travelu/core))
- A [README.md](/travelu/core/README.md) explaining the responsibilities of the domain logic.
- The application's persistence  ([travelu/localpersistence/src/main/java/travelu/localpersistence](travelu/localpersistence/src/main/java/travelu/localpersistence))
- A [README.md](travelu/localpersistence/README.md) about what we used for persistence and how we implemented it
- The application's REST server API setup  ([travelu/restserver/src/main/java/travelu/restserver](travelu/restserver/src/main/java/travelu/restserver))
- A [README.md](travelu/restserver/README.md) explaining how the server is set up and how to run it
- The application's REST client  ([travelu/client/src/main/java/travelu/client](travelu/client/src/main/java/travelu/client))
- A [README.md](travelu/client/README.md) explaining how the client works

<br>

- Tests for the application's UI controllers ([travelu/fxui/src/test/java/travelu/fxui](travelu/fxui/src/test/java/travelu/fxui))
- Tests for the application's domain logic ([travelu/core/src/test/java/travelu/core](travelu/core/src/test/java/travelu/core))
- Tests for the application's persistence ([travelu/localpersistence/src/test/java/travelu/localpersistence](travelu/localpersistence/src/test/java/travelu/localpersistence))
- An ingegration test for the application  ([travelu/integrationtests/src/test/java/travelu/integrationtests](travelu/integrationtests/src/test/java/travelu/integrationtests))
- A [README.md](travelu/integrationtests/README.md) explaining how we implemented and how to run the integration tests

## Building & running the project

The project is built with maven.
To run the project, in terminal:

- `cd travelu` to change into project-folder
- `mvn install` in root folder
  - **Note:** this also runs tests and installs package
- `mvn -pl restserver spring-boot:run` to start the server handling the rest-api

In a new terminal:
- `mvn -pl fxui javafx:run` to start the application

**Notice:**   
[fxui/module-info](travelu/fxui/src/main/java/) is deleted for enabling run through Maven.  
See [module-info](docs/development/module-info.md) for explanation

## Installing the project

The project can be installed with JLink and JPackage. This involves creating a binary executable for the application which can then be run by your computer.
To install the project, in terminal:

- `cd fxui` to change into ui-folder
- `mvn clean compile javafx:jlink` to generate a zip file
- `cd ..` to change into project-folder
- `mvn jpackage:jpackage -f ./fxui/pom.xml` to create a custom runtime image

It is now possible to run the app by unzipping the zip file and running the executable file.

This process is only functional locally (on mac). Teaching assistant has clarified on Piazza that this is sufficient.

**Note:** The server must be running for the app to work.

## Testing & coverage

After building project, run the following commands in terminal to run tests and generate coverage reports:

- `cd travelu` to change into project-folder
- Run `mvn test` to get test results
- Run `mvn verify` to get test-coverage and quality report
- Report found in [travelu/core/target/site/jacoco/index.html](travelu/core/target/site/jacoco/index.html)

See documentaion about [test coverage](docs/quality-assurance/testCoverage.md) for more information.

**NOTE:** The UI tests will fail if you have a spring-boot server running when you run the tests. You can either stop the server or run the tests with the `-DskipUITests=true` flag.

## Tech-stack

- Backend: _Java_
- Frontend: _JavaFX_
- Persistence: _JSON_
- Testing backend: _JUnit_
- Testing frontend: _TestFX_
- Project management: _Maven_

## Boilerplate and Gitpod

Integrated functions in VSCode was used to create a boilerplate for the project. This ensured that everything was added correctly.
We then followed up with changing **pom.xml** with the correct version for running on silicon macs and adding dependencies for testing.

Gitpod-tag got added to the README.md in root to enable remote work and easy access for the course-team
