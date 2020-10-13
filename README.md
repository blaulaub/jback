# About

This is a server backend.

# The modules

(alphabetically)

* **[jback-angular](jback-angular)** contains the angular frontend
* **[jback-api](jback-api)** defines and implements the API using Spring Web
* **[jback-core](jback-core)** provides the business logic
* **[jback-jpa](jback-jpa)** implements persistence using JPA and Spring Data
* **[jback-main](jback-main)** ties everything together to a runnable jar
* **[jback-sec-model](jback-sec-model)** declares a simple, generic security model
* **[jback-security](jback-security)** implements security using Spring Security
* **[jback-util](jback-util)** contains basic shared code

![module-dependencies.svg](graphviz/module-dependencies.svg)

# Compile

`mvn compile` should do (but [jback-angular](jback-angular) has some special
prerequisites). 

# Usage 

Undefined. Or self-explanatory.

# Deployment

Undefined (but [jback-main](jback-main) is currently configured to use Derby
for DB, so it will run stand-alone.)

# Requirements

Not so clear (but maybe check [here](jback-main/src/test)).

# Compile Duration

The values are actually seconds on my current machine (not the fastest),
but for relative comparison I have dropped the physical unit.

| Module                    | Clean-Compile | Clean-Install |
|---------------------------|---------------|---------------|
| .                         |               | *200*         |
| jback-angular             |  55           |   88          |
| jback-main                |               |   52          |
| jback-api                 |  28           |   45          |
| jback-jpa-entities-spring |               |   31          |
| jback-jpa-wrapper         |               |   29          |
| jback-presentation        |               |   23          |
| jback-core-entities       |               |   21          |
| jback-core                |               |   19          |
| jback-jpa-entities        |               |   19          |
| jback-sec-base            |               |   15          |
| jback-security            |               |   15          |
| jback-core-spring         |               |   13          |
| jback-security-entities   |               |   12          |
| jback-sec-model           |               |    7          |
| jback-security-spring     |               |    8          |
| jback-security-util       |               |    7          |
