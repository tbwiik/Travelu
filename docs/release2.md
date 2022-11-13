# Documentation release 2

## Current functionality of the project

In the second release, we mainly focused on further developing the backend side of the functionality from release 1. This is because developing a solid backend foundation makes implementing frontend features more straightforward later. In release 2 we restructured our entire project, implemented spotbugs, and wrote multiple tests for frontend and backend.

In addition to the functionality from release 1, you have now the option to describe your visit after double-clicking on each destination. If you double-click a preferred destination, a window pops up where you can add the activities you’ve done, along with comments, and choosing the date of your visit.

## Sprint 2 - Restructuring the project

### Meeting 1 - Sprint review and sprint planning

We started sprint 2 by reviewing what we did well and what we could improve on from sprint 1. Generally, everyone in the group was satisfied with the workflow and division of labor. However, the group members also agreed on some improvements that could be made. Examples were being more consistent on conventions for commit messages from https://www.conventionalcommits.org/en/v1.0.0/ and branch naming from https://codingsight.com/git-branching-naming-convention-best-practices/. This would make it easier to read and understand the changes we had made when looking back on them. We decided to make merge requests immediately when beginning to work on an issue. This makes for a clear and structured workflow, where GitLab automatically creates a new branch for the related issue.
We also agreed to write more documentation continuously to avoid having to write all the documentation at the end of the sprint when useful details about the development process may have been forgotten.

After reviewing sprint 1, we could then make a better plan for sprint 2. We started by creating 18 issues based on the description of the group assignment and assigned them across the group members. Our main goal for this sprint was to restructure our project by modularizing and placing backend, frontend, and persistence in separate folders. In sprint 1 we only assigned ourselves to issues one by one. However, we found this to be suboptimal where multiple members sometimes worked on similar issues, resulting in some of the work overlapping. To avoid this we now assigned ourselves to multiple issues at the same time if the issues were related. By continuing to do what worked well in sprint 1 and improving on the problems stated above, we were now ready to start development in sprint 2. And if problems emerged while we were working on the project, we decided we would create issues for the problems and fix each unrelated problem in separate branches to make it more clear what was getting done.

In addition, we decided to stop squashing when we merge branches to master. However, in our previous release, we used squashing to rewrite all commit messages in our working branch into one clean commit message. As a result of this, we would lose all of our previous commit messages and we couldn’t refer back to the previous changes. With the experience we acquired from the previous release, we decided to stop squashing so we can head back to previous changes. This decision requires that the commit messages must follow the conventions for commit messages.

### Meeting 2 - Pair programming

Previously, most of the work done by our members was done individually. However, our team wanted to try a new approach to development which was pair programming. By being two people working on the same problem at once, it is easier to spot potential bugs and find clever solutions while programming. Our main goal for this sprint was to make a logical structure for our project. Since this can be done in a lot of different ways and requires a lot of intuitive thinking, we figured this was greatly suited for and a good time to utilize the benefits of pair programming. We separated into two pairs where one pair worked on the structure of the project while the other pair worked on implementing tests which are also suited for pair programming by being highly logical work that also can be done in a lot of ways. When working in pairs, however, it is easy to let one person develop while the other person watches passively. To avoid this we alternated between which person was coding and who was watching. By doing this we made sure both were actively engaged in the code and actively developing.

## Sprint 3 - Making a solid foundation for future releases

### Meeting 1 - Sprint review and sprint planning

In this meeting, we reviewed what problems appeared and how we could resolve them. The problems were with modularization, and writing tests took more time than expected. To resolve this, we decided to use pair programming for modularization to fast-forward the process and distribute writing tests for the remaining team members.

Since our goal was to finish the assigned issues for the current sprint, we had to increase our workload by a minimum of one hour per day till the next meeting. We assigned the issues to each team member accordingly and worked together if needed.

### Meeting 2 – Getting most of the work done

We started this meeting by discussing what we had done, and what needed to be done before the next sprint. Modularization of the project started to get completed, and some of the tests were finished. The main focus of this meeting was to program together and get most of the heavy workload done. We cleaned up the code and optimized the project even further, creating new methods and classes, and making it more compatible with several use cases.

## Sprint 4 – Finishing release 2

### Meeting 1 – Sprint review and sprint planning

We started our last meeting before delivering release 2, by reviewing our current process. We had written most of the documentation, cleaned up and optimized the code, and created a new DateInterval class, in addition to the previous changes. During this process, we also noticed that we communicated and cooperated very well.

We used this meeting to specify what remaining issues we had to work on before delivering, and assigned to each member accordingly. The remaining issues included JavaFX tests, some emerging modularization problems, and finishing all of the documentation.

For our choice of diagram, we discussed the many options but concluded that [the package diagram](docs/packageDiagram.plantuml) was the most logical to document the project’s architecture. The assignment required us to use PlantUML, which takes a textual description and produces a diagram.
