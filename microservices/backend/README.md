# TaskManager Backend

This repository contains the backend for the TaskManager application, used to manage tasks and events. It's built using Java and Spring Boot.


# Local installation and setup

1. **Start PostgreSQL in Docker:**

To do it, PostgreSQL docker image is needed
```
docker pull postgres
```
```bash
 cd ./postgresql
 docker-compose -f docker-compose.yml up -d
```
You may need to change this part of /postgresql/docker-compose-postgresql.yml file:
```
image: bitnami/postgresql
```
to your PostgreSQL docker image name.

2. **Build and run the project:**
```bash
 cd ..
./gradlew build
./gradlew bootRun
```

# Additional resources:
**Gradle installation**
```
https://gradle.org/install/
```

**Getting started with Docker**
```
https://www.docker.com/get-started/
```

**DockerHub PostgreSQL**
```
https://hub.docker.com/_/postgres
```