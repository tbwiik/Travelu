[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2022/gr2219/gr2219.git)

# Group gr2219 repository 

Group members:
- Torbjørn Wiik
- Ken Van Nguyen
- Bendik Norli
- John Hausberg Anfindsen

# Building & running the project

The project is built with maven.
To run the project:
- Run `mvn install` from the root of the project.
- Run `cd travelu`
- Run `mvn javafx:run`

This will run the project and open the app.


# Travelu

Travelu is an application made to let people have a simple and organized way of looking at places they have been and things they have done. The app provides an overview over the destinations you have visited. Additionally, it lets you add a description, rating, travel date, and list of activities you have done at each destination. The app is meant to be used by people who travel a lot and want to have a gallery of all their memories and experiences.

## Tech-stack
- Backend: _Java_
- Frontend: _JavFX_
- Persistence: _JSON_
- Testing backend: _JUnit_ 
- Testing frontend: _TestFX_
- Project management: _Maven_

### Boilerplate and Gitpod
Integrated functions in VSCode was used to create a boilerplate for the project. This ensured that everything was added correctly.
We then followed up with changing **pom.xml** with the correct version for running on silicon macs and adding dependencies for testing.

Gitpod-tag got added to the README.md in root to enable remote work and easy access for the course-team