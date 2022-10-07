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

- [User scenarios](UserScenarios.md)
- Documentation of all [releases](docs)
- A [README.md](travelu/README.md) explaining the functionality of the app
- UI controllers of the app which are in the folder [travelu/src/main/resources/app](travelu/src/main/java/app)
- FXML files connected to these controllers which are in the folder [resources/app](travelu/src/main/resources/app)
- The application's domain logic and persistence which are in the folder [travelu/src/main/java/app/core](travelu/src/main/java/app/core)
- Tests which are in the folder [travelu/src/test/java/app/core](travelu/src/test/java/app/core)

**Package diagram of the project's architecture:**

![image info](/pictures/PackageDiagram.png)

## Building & running the project

The project is built with maven.
To run the project, in terminal:

- `cd travelu` to change into project-folder
- `mvn compile`
- `mvn javafx:run` to run

This will run the project and open the app.

Alternatively:

- `cd travelu` to change into project-folder
- `mvn install` in root folder
  - **Note:** this also runs tests and installs package
- `mvn javafx:run`to run

**Notice:** To run the project locally, the javafx version has to be changed from version `16` to `17` in [pom.xml](travelu/pom.xml).

## Testing & coverage

After building project and cd-ing into right folder,  
in terminal:

- Run `mvn test` to get test results
- Run `mvn verify` to get test-coverage and quality report
  - Report found in [target/../index.html](travelu/target/site/jacoco/index.html)

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
