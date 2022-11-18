# Documentation of code quality testing

To ensure that the code uploaded has high enough standards, several plugins are added to check for bugs and for general quality.

## Spotbugs

Spotbugs is a program which search for bugs in your code not picked up by the compilator. Building the project fails if any bugs are found. 

Spotbugs searches for over 400 bug patterns such as lack of incapsulation.

Implementation of SpotBugs is done to find problems or weaknesses not picked up by the programmer, reviewer or compilator. It enforces safer code.

### To run:

- `cd travelu` to change into project-folder
- `mvn verify` to run appropiate lifecycle for `check` goal
  - SpotBugs will run here
- `mvn install` will also run `verify` phase

### Report:

To afterwards show a graphical interface of the report, run:

- `mvn spotbugs:gui`

**Source:** [Spotbugs](https://spotbugs.github.io)

## Checkstyle

Checkstyle is an integration used for checking that Java code follows a given standard.
While the type of checks implemented in this project are the default ones, the program is very customizable and can be made to work with "almost any coding standard".

Implementation of Checkstyle is done to get feedback on code cleanliness. When followed, this plugin enforce pretty coding which makes the code eaiser to read.

### To run:

- `cd travelu` to change into project-folder

Then run either:
- `mvn verify` run appropriate lifecycle for checkstyle
- `mvn install` will also run `verify` phase

Source: [Checkstyle](https://checkstyle.org)
