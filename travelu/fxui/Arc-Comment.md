# Comment on frontend architecture

### Correct implementation

The correct way to build a multi-module project is to assign well defined functions to each module and build it in such a way that replacing modules is easy.
In other word: there should be as little dependencies and direct connections to other modules as possible.

This means that for the ui, to be optimal, the only communication should be done with client, which in turn get information through the RESTful server.

### Our implementation

Due to some fundamental faults when writing the project, a total (or partly) separation of core from ui has turned out being difficult. We recognize that having a dependency to core is bad, because this means that changes in core also result in ui needing tobe remade. This comes in addition to all the other dependencies core normally has.

### Further work

A dependency between core and client is better, but removing it altogether is best. This is something that should be done on further development.

### Reflections

The authors of this project have learned a great deal from this, both specifically on how a rest-api should be implemented, but also how important it is to create a good separation of modules and do things properly from the start.

