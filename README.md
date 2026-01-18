# Bank Transaction API

API for scheduling bank transactions.

## Stack

- Maven
- Java 17
- Spring Boot 3
- Spring Security
- H2 Database

## How to install

Run `mvn package` and then run `java -jar target/bank-transaction-api-0.0.1-SNAPSHOT.jar`

## API

The API is available at http://localhost:8080/bank-transaction/swagger-ui/index.html

## Database

The application uses a H2 database to store transactions:
http://localhost:8080/bank-transaction/h2-console/

## References

- [Architecture](https://guia.dev/pt/pillars/software-architecture/layers-and-architecture-patterns.html)
- [Conventional commits](https://dev.to/renatoadorno/padroes-de-commits-commit-patterns-41co)