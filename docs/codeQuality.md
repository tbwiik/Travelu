# Documentation of code quality testing

To ensure that the code uploaded has high enough standards, several plugins are added to check for bugs and for general quality.

## Spotbugs
Spotbugs is a program which search for bugs in your code not picked up by the compilator. Bugs found result in build failing.  
Spotbugs search for over 400 bug patterns such as lack of incapsulation.

Implementation of SpotBugs is done to find problems or weaknesses not picked up by programmer, reviewer or compilator. It enforces safer code.

### To run:
- `cd travelu` if necessary
- `mvn verify` for running appropiate lifecycle for `check` goal
  - SpotBugs will run here
- `mvn install` will also run `verify` phase
- `mvn spotbugs:gui` for graphical interface

### Report:
In the correct folder:
- `mvn spotbugs:gui` for graphical interface
  
**Source:** [Spotbugs](https://spotbugs.github.io)

## Checkstyle
Checkstyle is an integration used for checking that Java code follows a given standard.
While the type of checks implemented in this project is are the default ones, the program is very customizable and can be made to work with "almost any coding standard".

Implementation of Checkstyle is done to get feedback on cleanness of code. When followed this plugin enforce pretty coding which ensures easier to read code.

### To run:
- `cd travelu` if necessary
- `mvn verify` run appropriate lifecycle for checkstyle
- `mvn install` will also run `verify` phase

Source: [Checkstyle](https://checkstyle.org)
