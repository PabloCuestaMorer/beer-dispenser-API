<img align="left"  width="150" height="150" src=".github/rviewer_logo--dark.png" />

## Rviewer skeleton: Java, Spring & PostgreSQL

[![Twitter](https://img.shields.io/badge/rviewer__-%231DA1F2.svg?style=for-the-badge&logo=Twitter&logoColor=white)](https://twitter.com/Rviewer_/)

[![Rviewer Discord](https://badgen.net/discord/members/VVN4ur8FaQ)](https://discord.gg/VVN4ur8FaQ)

<br/>

This repository is a Java skeleton with Spring & PostgreSQL designed for quickly getting started developing an API.
Check the [Getting Started](#getting-started) for full details.

## Technologies

* [Java 18](https://openjdk.java.net/projects/jdk/18/)
* [Gradle 7](https://docs.gradle.org/7.0/release-notes.html)
* [Spring boot](https://spring.io/projects/spring-boot)
* [Lombok](https://projectlombok.org/)
* [Junit](https://junit.org/junit5/)
* [JaCoCo](https://docs.gradle.org/current/userguide/jacoco_plugin.html)
* [Docker](https://www.docker.com/)
* [Make](https://www.gnu.org/software/make/manual/make.html)

## Getting Started

Within the [Makefile](Makefile) you can handle the entire flow to get everything up & running:

1. Install `make` on your computer, if you do not already have it.
2. Start the application: `make up`
3. Run the application tests: `make test`

As you could see on the [Makefile](Makefile) script and the [Docker-Compose File](docker-compose.yml), the whole API
is containerized with Docker and the API is using the internal DNS to connect with the PostgreSQL instance.

Go to `http://127.0.0.1:8080/ping` to see that everything is up & running!

## Overview

This skeleton is based on
a [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) approach, so you
could find the first basic layers:

> You could find here two amazing articles ([here](https://www.educative.io/blog/clean-architecture-tutorial)
> and [here](https://www.freecodecamp.org/news/modern-clean-architecture/)) explaining the Clean Architecture with Java!
> (credits to [@bertilMuth](https://twitter.com/BertilMuth) and [@ryanthelin](https://dev.to/ryanthelin)).

### Infrastructure

Here you will find the different files to interact with the outside. In this folder you there are two different folders:

* `controllers`: Here you will have the classes that handle the REST endpoints and the Request/Response
* `persistence`: Here it is the persistence layer, which interact with the PostgreSQL database, decoupling the rest of
  the application

You can use this as a starting point to continue with this architecture, or adapt it to your preferences.

### Domain

Any of your domain Entities, or Services, that models your business logic. These classes should be completely isolated
of any external dependency or framework, but interact with them. This layer should follow the Dependency Inversion
principle.

## Support

If you are having problems or need anything else, please let us know by
[raising a new issue](https://github.com/Rviewer-Challenges/skeleton-java-spring-rest/issues/new/choose).

## License

This project is licensed with the [MIT license](LICENSE).

--- 

<p align="center">
  Made with ❤️ by <a href="https://rviewer.io">Rviewer</a>
</p>
