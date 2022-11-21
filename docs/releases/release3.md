# Documentation release 3

## Current functionality

For the third and last release, we implemented a REST-API with springboot and expanded the functionality of the application from the previous releases. The UI had to be changed to be compatible with the REST-API, and we also wrote tests for the new implementation.

We expanded the functionality, where you can now rate the destinations between 1 and 5, sort the list of destinations by rating or by alphabetic order, and have the option to remove added activities.

### Screenshots of UI for release 3:

|        Front page with sorting feature and rating display         |      Destination page with rating and remove button      |
| :---------------------------------------------------------------: | :------------------------------------------------------: |
| ![Destination-List View](../pictures/destinationListRelease3.png) | ![Destination View](../pictures/destinationRelease3.png) |

## Sprint 5 – Preparation for the final release

### Meeting 1 - Sprint review and sprint planning

We started sprint 5 by reviewing what we have done well for release 2 and what we should do next to finish the final product in release 3. The group was overall satisfied with the result of release 2, and the feedback was great. Since this was the last release for the project, we decided to use the feedback we got to solve the remaining bugs and improve the existing functionality, before starting on release 3. Therefore, this sprint was dedicated to fix-up.

As the delivery for release 2 was pretty tight, we decided to make some changes regarding our workflow. For the next release, we chose to work more together for a more efficient workflow and made earlier deadlines to finish appointed issues. We wanted to be finished earlier, so we can use the remaining time to clean up. We also decided to be even more consistent with branching by creating fewer issues and combining them into one if necessary. Thus, getting a clear view of what issues must be resolved and what branches are related to those issues. An example is that we combined code, Javadoc, and tests in one issue.

Even though some difficulties occurred, we have also done great in other parts. We give each other continuous feedback which is quite fitting when a problem gets complicated. And when a problem gets complicated, we have resorted to pair programming, which has worked well. Although we have decided to make changes to our workflow, the general workflow was great, and branching and committing were also getting better.

### Meeting 2 – Fix-up release 2

We discussed the fixes we have done from the previous meeting for this meeting. Most of the fixes got done, but a problem related to dates needed prioritization. However, we had to start discussing what we wanted the end product to be like. After a brief discussion, we have decided to keep using JavaFX and add new features to meet the requirements. We used this meeting to try to fix the remaining problems, and we settled to start the planning for release 3 in the next sprint.

## Sprint 6 - Planning the final result for the project

### Meeting 1 – Sprint review and planning

We started this sprint by discussing what we have done, and what needed to be done for the next sprint. We had fixed most of the problems by now, but the problem related to dates had not been resolved yet. However, this meeting was dedicated to start our plan for release 3. We started by creating 10 issues based on the description of the assignment. By following our decision for release 3, we have reduced the number of issues. For this release, our main goal is to add new functionality, implement REST-API, write tests and create diagrams.

### Meeting 2 – Working on the assigned issues

This meeting was dedicated to working on the assigned issues, where we carefully started on this release's main functionality. We also started to work on the documentation earlier so it can be more detailed and specific, in contrast to working on it near the deadline.

The date problem was almost solved, but a specific part of the validation got changed after a brief discussion. Beforehand you could not set arrival and departure dates after the present day. After working on it for a while, we realized there are cases where people want to plan ahead of time and thus set arrival and departure dates before they depart. We decided to therefore make it possible for people to plan ahead of their travel.

## Sprint 7 – Working on the main issues

### Meeting 1 – Sprint review and planning

We started this meeting by reviewing what we had done since the last sprint. We have worked on documentation, and problems regarding module-info and other bugs, and made a working pipeline. The date problem is still intact, but we are coming closer to solving it. The main problem that is remaining, is understanding REST-API and springboot. Since it is part of the main requirements for this assignment, we will try pair programming to solve this problem.

Despite problems and setbacks, we have also improved in different aspects. We have used pair programming actively and it has given results. We have communicated well and have had good initiative, as a result of agile development. We have also tried different study methods to improve our efficiency, concluding that taking more pauses is beneficial.

The plan for the next sprint is to finish the date problem, continue working on the documentation, and enhance the application by adding new features. The features that we had in mind were removing added activities, sorting destinations, and ranking the activities. We also had another function in mind, where you could add images to the destination but it was quite difficult to test it and it was not a feature that we felt fit in. In addition, we needed to prioritize the previously mentioned features. However, the main priority right now is to understand REST-API and springboot, so that we can apply it to our application.

### Meeting 2 – Finishing the existing problems

We used this meeting to program together and try to work on this sprint’s goals. Luckily, the date problem has finally been resolved, and it seems like we are back on a steady track.

## Sprint 8 – Increasing the workload

### Meeting 1 – Sprint review and planning

In this meeting, we reviewed what we had done since the last sprint and our next plan to finalize the project. Generally, everyone in the group was satisfied with the workflow and communication. We were especially contented with the previous meeting where we focused on getting as much work done as possible. Since the last sprint, we have implemented new functionalities, where you can remove activity from the activity list in the chosen destination and also rate the destination between 1 and 5.

Although spending a decent amount of time on REST-API and springboot, the progress of understanding it and applying it to our project is still challenging. We came further from last time, but there is still a decent amount remaining. That is why we decided to prioritize this problem by getting two people to mainly focus on this matter.

Since the deadline is coming closer, we have decided to increase our workload to be more efficient. We need to implement REST-API with springboot so that we can start working on the remaining issues related to them. We must also continue to write documentation and clean up necessary code. During this time, we also realized that we each sprint should have used a development branch that acted like a master branch until the end of release. The reason for this is that the master branch gets messy when we merge a lot of small changes into it. A lot of issues are related and merging them one by one into master makes the master branch have temprorarily unnecessary code. Therefore, we could instead merge all our changes into the development branch first and then merge the development branch into master when we are ready to release. This would also add an additional check before merging into master and therefore a second layer of security. It would also be easier to see what changes had been made from release to release. However, since we are so close to the finalized product it was too late to create this type of branch.

### Meeting 2 – Creating more meetings to work together

We increased meetings for the remaining days so that we can use them to develop together, thus getting more progress done. We had to finish writing tests, implement REST-API with springboot and create tests for REST and integration tests. On top of that, we have to write documentation and also create a configuration to make it a shippable product using jlink and jpackage.

## Sprint 9 – Last sprint before delivering

### Meeting 1 - Sprint review and planning

We started our last sprint meeting, by reviewing our current process and what problems needed priority. Luckily, we were able to get the REST server up and going, but we understood that we needed to improve the code. After reviewing, we needed to prioritize implementing the integration tests, as it has been challenging to implement. While we set aside people to work on the problem, the rest of us had to get the existing problems resolved, where we worked together if needed.

### Meeting 2 – Getting the project finalized

We used our last meeting, before delivering, to finish the remaining issues. We managed to implement the integration tests after working together since the last meeting. Most of the features, tests, and documentation were also finalized. The remaining issues included fixing UI tests for REST, cleaning up code, and finishing all of the documentation.

## Late delivery

At the end of the final days before delivering, we realized our project could not be finished before the deadline. During the third release, we had problems regarding illness within our group, causing the process to slow down. Since we were divided, we thought the best way of handling this, was to fix the remaining bugs from release 2 and work on the new features we wanted to implement. Therefore, we delayed implementing REST-API, which resulted in delaying more issues regarding REST. As a consequence of this, we tried to have more meetings, as previously mentioned, to develop together. Despite having more meetings the lack of documentation about REST-API, tests for REST-API, UI integration tests, and UI tests with a mocking server, made it difficult to proceed with the project. We tried to get help from study assistants, but with many groups also having problems, we never found success in getting help. Therefore, we tried to solve those problems by trying and failing, and as a result, it took too much time in the end. We realized we could not deliver the project by the original deadline, and needed expanded delivery time to get the necessary features done to finalize the project, since we felt it was more important to deliver a project with all the necessary features.
