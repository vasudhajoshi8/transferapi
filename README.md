# Transfer API

This project is a simple RESTful API for performing money transfers between accounts. It is built with Java, Spring Boot, and PostgreSQL, and packaged as a standalone executable JAR using Maven. The application is containerized with Docker.

## Setup

1. Ensure you have Docker and Docker Compose installed.
2. Build the project using Maven:
    ```sh
    mvn clean package
    ```
3. Start the application using Docker Compose:
    ```sh
    docker-compose up -d --build
    ```
4. Run tests using the provided bash script:
    ```sh
    ./test.sh
    ```

## API Endpoints

- `POST /api/accounts` - Create a new account
- `POST /api/transfer` - Transfer money between accounts
- `GET /api/accounts/{id}` - Retrieve account details

## Decisions and Focus

- **Simplicity**: Focused on implementing core functionality without additional features such as authentication.
- **Technology Stack**: Chose Spring Boot for rapid development and ease of creating RESTful APIs.
- **Database**: Used PostgreSQL for reliable data storage and transactions.
- **Containerization**: Dockerized the application for easy deployment and testing.

## Areas Not Focused On

- **Authentication and Authorization**: Assumed internal usage only.
- **Advanced Error Handling**: Basic error handling for demonstration purposes.
- **Scalability**: Focused on a simple, standalone application.
