# plang
High performant and advanced localisation and multilingual lexical analysis Java library to modify, cache, use and replace global or local placeholders, variables and parameters in preset or dynamic messages.

**This library is in beta, thorough documentations and wiki is coming with the first release.**

[![javadocs](https://javadoc.io/badge2/io.github.sauranbone/plang/javadoc.svg)](https://javadoc.io/doc/io.github.sauranbone/plang)

## Documentation
More information about this project is listed below. Javadocs can be browsed here.

## Get plang
You can download plang using automated build tools like **Maven** or **Gradle** by adding the following source.

Maven:

```xml
<dependency>
  <groupId>io.github.sauranbone</groupId>
  <artifactId>plang</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
```

Gradle:

```gradle
require 'io.github.sauranbone:plang:1.0-SNAPSHOT';
```

Or by simply downloading the latest released JAR-File.

## Examples and basic explanations
Below are some examples that showcase good use-cases for when to use Plang.

### Dynamic placeholder and basic explanation
```java
LanguageFactory factory = Plang.getLanguageFactory();
Language english = factory.getOrCreate("English", "en");
MessageRegistry content = english.getRegistry();
content.set("public-announce", "Welcome {Name} to our chat!");

//Display the message
Message message = content.get("public-announce");
String formatted = message.transform(DataBindMap.index("Vinzent"));
System.out.println(formatted);
```

Output:
```
Welcome Vinzent to our chat!
```

There is a lot going on, beginning with the `LanguageFactory`. <br>
The factory is essentially a cache and allocation system for languages,
so that you do not have to worry about creating one or multiple languages and 
saving them into globally accessible variables. The factory cache will handle
everything automatically and does safe-proof things to avoid errors or sensitive
issues to happen.<br>
<br>
The `MessageRegistry` is just a container for messages that are only bindable and bound
to the language the registry is assigned to. The registry is allocated automatically
within the default language constructor, that is done, whenever "English" is yet not
cached and thus created.<br><br>
In the lower paragraph we basically get the message `public-announce` out of the message
content of the english language.<br>
Then we transform the message using a `DataBinder` named `DataBindMap` that is created
using `DataBindMap#index(Object[])`, which is replacing the message's placeholders based on their
position in relation to the index positions of the arguments we pass into allocation method.<br>

### Direct placeholder binding
```java
LanguageFactory factory = Plang.getLanguageFactory();
Language english = factory.getOrCreate("English", "en");
MessageRegistry content = english.getRegistry();
content.set("foo bar", "{Name} is {Age} years old!");

//Display the message
Message message = content.get("foo bar");
DataBinder binder = new DataBindMap();
binder.bind("Name", "Alexander");
binder.bind("Age", 38);
String formatted = message.transform(binder);
System.out.println(formatted);
```
Output:
```
Alexander is 38 years old!
```
In this example, that is pretty equal to the last example - that explains the basics, we create
a message template and create a `DataBinder` which binds certain placeholder names.
This means, that the literal placeholders within the given message `Name` and `Age` are replaced
by the values we associate them to within the data binder.

### Global placeholder binding
In the examples above only a fraction is showcased what plang is capable of, another strong capability
of plang is the global placeholder binding, so you do not need to worry about binding placeholders
dynamically when you transform a message every time.<br>
When we talk about global placeholder binding, we mean a placeholder `Lexicon`, that can be assigned to
multiple languages at the same time. When using the Language Factory, we can simply append the lexicon
to the end of `LanguageFactory#getOrCreate(String, String, Lexicon)`:<br>
```java
Lexicon lexicon = new Lexicon();
LanguageFactory factory = Plang.getLanguageFactory();
Language english = factory.getOrCreate("English", "en", lexicon);
//Bind dynamic placeholders into the global lexicon
lexicon.set(Placeholder.of("user.name", User.class, user -> user.name));
lexicon.set(Placeholder.of("user.age", User.class, user -> user.age));

//Bind a static placeholder into the global lexicon
lexicon.set(Placeholder.of("worldLanguage", "English"));

//Bind a type to the global lexicon which #toString() is used
lexicon.set(Placeholder.of("userToString", User.class));

MessageRegistry content = english.getRegistry();
content.set("age", "{user.name} is {user.age} years old!");
content.set("lang", "The language of the world is: {worldLanguage}");
content.set("user", "User instance #toString() is: {userToString}");

//Display the message
Message message = content.get("age");
User exampleUser = new User("oceanHuntr3", 43);
DataBinder data = DataBindMap.types(exampleUser);
System.out.println(message.transform(data));
System.out.println(content.get("lang").transform());
System.out.println(content.get("user").transform(data));
```
Output:
```
oceanHuntr3 is 43 years old!
The language of the world is: English
User instance #toString() is: examples.WelcomeUser$User@723279cf
```

In the example above we use the basics that were explained further above and implement a new way
of binding placeholders, which is now global. This means that every language that is sharing the
same lexicon reference will have the same globally accessible placeholders.<br>
There are different types of global placeholders:
- **Dynamic**<br>The dynamic placeholder is accepting a certain class type (here `User.class`), which is determining whenever the placeholder's transformer's (our lambda `user -> user.name` or `user -> user.age`) method should be invoked. The invocation of the transformer is done, whenever a bound type within a given `DataBinder` is a superclass or an equal class of the determined class type (here `User.class`). An example would be the binding through `DataBinder.bindType(...)` or `DataBindMap.types(...)`, which's values are scanned and their top superclass, that is *not* of type *Object*, is used as the comparison type to check if the class is matching any of the dynamic placeholder types.
- **Static**<br>The static placeholder is simply put the placeholder literal bound to a fixed and static value content that cannot be modified over the course, unless the bound value is an object.
- **Class**<br>The class placeholder is essentially just saying, that whenever a bound type - with given class (or extending class) - is set, the bound's value object `Object#toString()` is used as the content of the placeholder, that is then replacing any literal pointer to that placeholder on parsing.
