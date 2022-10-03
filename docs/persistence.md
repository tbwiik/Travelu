## Persistence
### Gson
Since the requirement of persistence in this project is JSON, we decided to use GSON as the library to store the data and access it. GSON is an open-source Java library, where you can use simple methods to serialize Java objects to JSON and also deserialize JSON to Java objects. The reasons why we used GSON instead of other libraries such as Jackson and JSON-simple, is because:

- It’s easy to use, where commonly used use-cases get simplified.
- No need to create mapping, since it provides default mapping for most of the objects.
- Does not require any other libraries other than JDK.
- GSON is one of the fastest JSON libraries and is also of low memory footprint.

### Implicit storage

We decided to use implicit persistence in our project because it was the most convenient. This means the data gets stored after each interaction within our application, and the data will remain the same if you reopen it. The advantage of implicit persistence is that you don’t need to remember to save your data since it automatically saves for you. And when our project consists of a small number of actions, unlike a game, we don’t gain any disadvantage using implicit persistence. 

### How we implemented it
We used the methods .fromJson() and .toJson() in our [TraveluHandler](travelu/src/main/java/app/core/TraveluHandler.java), which saves and loads from the [JSON-file](travelu/src/main/resources/app/core/data/persistence.json). It saves after each time the user adds or removes a destination, and the data from the JSON-file gets loaded when the application gets opened.