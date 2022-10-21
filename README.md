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
- UI controllers of the app which are in the folder [travelu/fxui/src/main/java/travelu/fxui](travelu/fxui/src/main/java/travelu/fxui)
- FXML files connected to these controllers which are in the folder [resources/travelu/fxui](travelu/fxui/src/main/resources/travelu/fxui)
- The application's domain logic which is in the folder [travelu/core/src/main/java/travelu/core](travelu/core/src/main/java/travelu/core)
- The application's persistence which is in the folder [travelu/fxutil/src/main/java/travelu/fxutil](travelu/fxutil/src/main/java/travelu/fxutil)
- Tests for the application's domain logic which are in the folder [travelu/core/src/test/java/travelu/core](travelu/core/src/test/java/travelu/core)
- Tests for the application's persistence which are in the folder [travelu/fxutil/src/test/java/travelu/fxutil](travelu/fxutil/src/test/java/travelu/fxutil)

- [Package diagram](docs/packageDiagram.plantuml) of the project's architecture 

## Building & running the project

The project is built with maven.
To run the project, in terminal:

- `cd travelu` to change into project-folder
- `mvn compile`
- `cd fxui` to enter correct module
- `mvn javafx:run` to run

This will run the project and open the app.

Alternatively:

- `cd travelu` to change into project-folder
- `mvn install` in root folder
  - **Note:** this also runs tests and installs package
- `cd fxui` to enter correct module
- `mvn javafx:run` to run

**Notice:** To build project, delete [fxui/module-info](travelu/fxui/src/main/java/module-info.java)

## Testing & coverage

After building project and cd-ing into right folder,  
in terminal:

- Run `mvn test` to get test results
- Run `mvn verify` to get test-coverage and quality report
- Report found in [travelu/core/target/site/jacoco/index.html](travelu/core/target/site/jacoco/index.html)

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
