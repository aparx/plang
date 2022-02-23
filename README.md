# plang
High performant and advanced localisation and multilingual lexical analysis Java library to modify, cache, use and replace global or local placeholders, variables and parameters in preset or dynamic messages.

## Documentation
More information about this project is listed below. Javadocs can be browsed here.

## Get plang
You can download plang using automated build tools like **Maven** or **Gradle** by adding the following source.

Maven:

```xml
<dependency>
  <groupId>io.github.sauranbone.plang</groupId>
  <artifactId>plang-core</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
```

Gradle:

```gradle
require 'io.github.sauranbone.plang:plang-core:1.0-SNAPSHOT';
```

Or by simply downloading the latest released JAR-File.

## Examples and simple documentations
Below are some examples that showcase good use-cases for when to use Plang.

### Explanation of a simple example
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
```console
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
position in relation to the index positions of the arguments we pass into allocation method.


## Simple Wikis
A more advanced tutorial and explanation can be found in this wikis.

### Basics
The Plang library is essentially parted into different pieces, each describing a certain task
or container that contains utilities and attributes that describe that object.

#### Placeholder
A placeholder essentially is like a variable name, that is replaced with the variable content
whenever a message is parsed using given data input (DataBinder).
A placeholder can be identified and used in many ways, but the most popular is within a message
content and a specific syntax that indicates a piece of literal text to be a placeholder, that is 
set within the corresponding message lexer.
The placeholder class is **not** representing a literal token within literal text as you might think,
so a placeholder instance is not representing placeholder name token, but rather the direct content
for a later translation from a literal placeholder to content.


