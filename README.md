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
- Run `cd fxui`
- Run `mvn javafx:run`

This will run the project and open the app.


# Travelu

Travelu is an application made to let people have a simple and organized way of looking at places they have been and things they have done. The app lets you have an overview over the destinations you have visited. Additionally, it lets you add a description, rating, travel date, and list of activities you have done at each destination. The app is meant to be used by people who travel a lot and want to have a gallery of all their memories and experiences.

## Tech-stack
- Backend: _Java_
- Frontend: _JavFX_
- Persistence: _JSON_
- Testing backend: _JUnit_ 
- Testing frontend: _TestFX_
- Project management: _Maven_

## Release 1

### Planning the project

The very first meeting with the team was spent writing the coop-contract.  

In the second meeting we discussed several different projects to do and landed on a travel-journal application. Our argumentation for this choice is that this is both something we saw a need for, it is easy to come up with new features and it covers all the guidelines from the course-team.  
We also decided on guidelines for using git:
https://www.conventionalcommits.org/en/v1.0.0/

In the third meeting we created a backlog and defined the sprint for release 1. Here we divided the assignment given to us into smaller subtasks. Then we created an issue for each substask. This made it possible for us to have an organized and clearly defined plan of what we were going to do next. From the description of group assignment 1 we created 26 issues regarding frontend, backend, and documentation with a defined importance. We planned to at least complete all of the tasks marked with "high priority" by the end of the sprint (the deadline for the group assignment). To be able to complete these tasks we decided to assign the issues evenly across the members of the group. We also arranged three meetings before the end of the sprint where we were going to develop together and go through what each member had done individually since the last meeting. By clearly defining what we were going to do, how we were going to do it and distributing the tasks evenly across the members of the group, we were now ready to start development.


`TODO: Detailed plan of commits mabye`


 ref **Issues**.

### Boilerplate and Gitpod
Integrated functions in VSCode was used to create a boilerplate for the project. This ensured that everything was added correctly.
We then followed up with changing **pom.xml** with the correct version for running on silicon macs and adding dependencies for testing.

Gitpod-tag got added to the README.md in root to enable remote work and easy access for the course-team