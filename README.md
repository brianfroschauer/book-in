# Book In

Backend of the Book In application.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

All you need to run the application is [install docker](https://www.docker.com/get-started).

### Running the application

This is a step by step that tell you how to get a development env running.

1. Clone the repository

```bash
git clone https://github.com/brianfroschauer/book-in.git
```

2. Go to the repository root

```bash
cd book-in
```

3. Setup the database with docker compose

```bash
docker-compose up
```

4. Run the application

```bash
./mvnw spring-boot:run
```

## Running the tests

To run the automated tests for this system, execute the follow command:

```
./mvnw clean test -Dspring.profiles.active=test
```

## Built With

* [Spring Boot](https://spring.io/projects/spring-boot) - The backend framework used
* [Maven](https://maven.apache.org/) - Dependency management

## Contributing

Please read [CONTRIBUTING.md](https://github.com/brianfroschauer/book-in/blob/develop/CONTRIBUTING.md) for details on the process for submitting pull requests.
