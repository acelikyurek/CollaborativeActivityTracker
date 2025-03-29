# Collaborative Activity Tracker Application

An API built with Java Spring Boot framework. Key technologies used in this project include:

- REST endpoints for CRUD on authentication & activity-related operations

- JWT authentication

- Containerized via Docker & Docker Compose

- Unit & integration tests

## Prerequisites

- Install Docker Desktop or Docker Engine with Compose support.

- Create a file for environment variables, `.env`, in project root (example file for environment variables, `.env.example`, is also shared).

    ```bash
    SPRING_APPLICATION_NAME=CollaborativeActivityTracker
    SPRING_PROFILES_ACTIVE=debug
    SECURITY_JWT_SECRET_KEY=<...>
    SECURITY_JWT_EXPIRATION_TIME=<...>
    SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/collaborative_activity_tracker_db
    SPRING_DATASOURCE_USERNAME=<...>
    SPRING_DATASOURCE_PASSWORD=<...>
    SPRING_DATASOURCE_DRIVER_CLASS_NAME=org.postgresql.Driver
    SPRING_JPA_HIBERNATE_DDL_AUTO=update
    SPRING_JPA_OPEN_IN_VIEW=false
    SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT=org.hibernate.dialect.PostgreSQLDialect
    POSTGRES_HOST=db
    POSTGRES_PORT=5432
    POSTGRES_DB=collaborative_activity_tracker_db
    POSTGRES_USER=<...>
    POSTGRES_PASSWORD=<...>
    ```

- Note that `SPRING_DATASOURCE_USERNAME` and `POSTGRES_USER` as well as `SPRING_DATASOURCE_PASSWORD` and `POSTGRES_PASSWORD` must be the same for establishing database connection. 

- Also, the value `SPRING_DATASOURCE_URL` is derived from the values of `POSTGRES_HOST`, `POSTGRES_PORT` and `POSTGRES_DB`.

## Setup

- Build and start all services:

    ```bash
    docker compose up --build --detached
    ```

## API Endpoints

| Method | Endpoint                       | Description               |
|--------|--------------------------------|---------------------------|
| POST   | `/api/auth/login`              | Login an existing user    |
| POST   | `/api/auth/register`           | Register a new user       |
| GET    | `/api/activity`                | List activities           |
| POST   | `/api/activity`                | Create activity           |
| GET    | `/api/activity/{activityId}`   | Retrieve activity details |
| PUT    | `/api/activity/{activityId}`   | Update activity           |
| DELETE | `/api/activity/{activityId}`   | Delete activity           |
