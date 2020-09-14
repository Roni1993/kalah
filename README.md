# Kalah

### General Backend
 - Game logic is build located in 'logic'
 - Rest API & Websocket API are located in 'api'
 - DTO's are build with lombok and reside within 'dto'
 - The game logic relevant parts are secured with JWT within 'security'

 - Dockerfile can be created by `./gradlew bootBuildImage`
 - Unit-Test were build with Cucumber & Mockito to ensure the Game rules apply 

### General Frontend
 - Build with Angular & BootStrap
 - API client was generated from the Swagger UI definition

# Instruction to start

## Backend
`cd backend` & `./gradlew bootRun` Starts the Spring Boot Backend. ReST API is at `http://localhost:8080`

## Frontend
Run `cd frontend` && `ng serve` to start the Frontent in dev mode. Navigate to `http://localhost:4200/`.

# Dockerized build & run

- `cd backend` & `./gradlew bootBuildImage`
- `cd frontend` & `docker build -t kalah-fe .`
- to run go to repo root & execute `docker stack deploy kalah --compose-file docker-compose.yml`
