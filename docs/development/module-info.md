# Note about module-info

## Java Platform Module System
The Java Platforum Module System (JPMS) was introduced in Java 9.  
It is a system created for administrating modules and solve problems such as version conflict and security issues.  
This is done by adding module-info.java files in modules and explicitly stating accesses.

## TestFX and access error
The JavaFX tests utilize ApplicationTest to be able to run. However, ApplicationTest does not have access to certain functionalities in the javafx graphics module which results in access errors. This happens even though the module it is run from, [fxui](travelu/fxui), has the right accesses and requirements.

## Solution and deleting module-info
A solution here, which was proposed by teaching-assistants, is to remove module-info in this folder.   
This solution works, but removes the JPMS functionality and results in the fxui-module being viewed as an "unnamed module".  
An "Unnamed module" requires everything and exports to everything, which is not a safe way to handle a module.

Until a solution arises it is decided to delete module-info in [fxui](travelu/fxui) to be able to run unit tests with Maven and build application.

## Old module-info
The module-info.java used before deleting:
```java
module travelu.fxui {

    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;

    requires travelu.core;
    requires travelu.localpersistence;

    opens travelu.fxui to javafx.fxml, javafx.graphics;
}
```

## Sources
- [JPMS - GeeksForGeeks](https://www.geeksforgeeks.org/jpms-java-platform-module-system/)
- [Understanding Modules - Oracle](https://www.oracle.com/corporate/features/understanding-java-9-modules.html)
- [JPMS - Wikipedia](https://en.wikipedia.org/wiki/Java_Platform_Module_System)
