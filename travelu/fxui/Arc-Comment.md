# Comment on frontend architecture

### Correct implementation

The correct way to build a multi-module project is to assign well defined functions to each module and build it in such a way that replacing modules is easy.
In other words: there should be as little dependencies and direct connections to other modules as possible.

This means that for the ui, to be optimal, all communication should be done with the client, which in turn get information through the RESTful server.

### Our implementation

Due to some fundamental faults when writing the project, a total (or partly) separation of core from ui has turned out to be difficult. We recognize that having a dependency to core is bad, because this means that changes in core also result in ui needing to be remade. This comes in addition to all the other dependencies core normally has.

### Further work

A dependency between core and client is better, but removing it altogether is best. This is something that should be done on further development.

### Reflections

The authors of this project have learned a great deal from this issue. Specifically on how a rest-api should be implemented and integrated with ui. More generally we learned how important it is to do things correct and properly from the start, and build a project with good separation of modules.


