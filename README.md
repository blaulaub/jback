# About

This is a server backend.

# The modules

(alphabetically)

* **[jback-api](jback-api)** defines and implements the API using Spring Web
* **[jback-core](jback-core)** provides the business logic
* **[jback-jpa](jback-jpa)** implements persistence using JPA and Spring Data
* **[jback-main](jback-main)** ties everything together to a runnable jar
* **[jback-sec-base](jback-sec-base)** declares fundamental security things shared between `jback-core` and `jback-security`
* **[jback-sec-model](jback-sec-model)** declares a simple, generic security model
* **[jback-security](jback-security)** implements security using Spring Security

# Compile

`mvn compile` should do.

# Usage 

Undefined.

# Deployment

Undefined.

# Requirements

See [here](jback-main/src/test).