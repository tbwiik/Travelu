# Test Coverage with JaCoCo

[JaCoCo](https://www.jacoco.org/jacoco/), short for Java Code Coverage, is a tool used for analyzing code for test-coverage.

Note that JaCoCo check whether code is executed.  
This mean that even though there are 100% test-coverage it doesn't mean that everything is tested satisfactory.

## Running
- `cd travelu` if necessary
- `mvn verify` run appropriate lifecycle for checkstyle
- `mvn install` will also run `verify` phase

## The report
There are several ways to view the report:

### In-terminal
Run `mvn jacoco:report` to get an in-terminal shorthand run and report.

### In your browser
In the respective module find `target/site/jacoco/index.html` and open in browser

### The executable
JaCoCo generates an executable which can be used for viewing the report.  

Note that this functionality is not yet implemented and can therefore be ignored.


## Report Example
![JaCoCo-report-demo](../pictures/JaCoCoReportDemo.png)



## Sources
- [JaCoCO homepage](https://www.jacoco.org/jacoco/)  
- [Baeldung tutorial](https://www.baeldung.com/jacoco)
