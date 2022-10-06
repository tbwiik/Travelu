# Documentation of code quality testing

To ensure that the code uploaded has high enough standards, several plugins are added to check general quality and for of bugs.

## Spotbugs
Spotbugs is a program which search for bugs in your code not picked up by the compilator. Bugs found result in build failing.  
Spotbugs search for over 400 bug patterns such as lack of incapsulation.

Source: [Spotbugs](https://spotbugs.github.io)

## Checkstyle
Checkstyle is an integration used for checking that Java code follows a given standard.
While the type of checks implemented in this project is are the default ones, the program is very customizable and can be made to work with "almost any coding standard".

Source: [Checkstyle](https://checkstyle.org)