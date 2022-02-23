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


