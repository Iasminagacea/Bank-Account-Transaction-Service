# Bank Account & Transaction API

A simple backend REST API I built with Java 17 and Spring Boot to manage basic bank accounts and transactions.

The main purpose of this project was to practice building a clean backend from scratch: splitting logic across controllers, services, and repositories, handling database operations with Spring Data JPA, and testing endpoints via Postman.

---

## Features

- **Bank Accounts:** Create an account (defaults to 0 balance) and query details by ID.
- **Transactions:** Deposit and withdraw money. It checks for insufficient funds before completing a withdrawal.
- **Transaction Logs:** Automatically saves a record of every deposit or withdrawal with its timestamp.
- **In-Memory Storage:** Configured with an embedded H2 database, so you don't need to install or configure an external database to run it.

---

## Built With

- Java 17
- Spring Boot 3 (Web, Data JPA)
- H2 In-Memory Database
- Maven
- Postman

---

## How It's Structured

The code is split into standard layers:

- `model`: JPA entities (`Account`, `Transaction`) that map directly to database tables.
- `repository`: Interfaces extending `JpaRepository` for basic CRUD operations.
- `service`: Business logic (balance checks, updates, creating transaction records).
- `controller`: REST endpoints receiving requests and returning JSON responses.

---

## Endpoints

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/accounts` | Open a new bank account |
| `GET` | `/api/accounts/{id}` | Get account details and balance |
| `POST` | `/api/accounts/{id}/transactions` | Deposit or withdraw money |
| `GET` | `/api/accounts/{id}/transactions` | Get all transactions for an account |

### Request Examples

**Create Account (`POST /api/accounts`):**
```json
{
  "ownerName": "Mihaela Ionescu",
  "iban": "RO98SG0001"
}
```

**Make a Deposit (`POST /api/accounts/1/transactions`):**
```json
{
  "amount": "250.00",
  "type": "DEPOSIT"
}
```

**Make a Withdrawal (`POST /api/accounts/1/transactions`):**
```json
{
  "amount": "50.00",
  "type": "WITHDRAW"
}
```

---

## Running Locally

1. Clone the repo:
   ```bash
   git clone [https://github.com/Iasminagacea/spring-boot-banking-api.git](https://github.com/Iasminagacea/spring-boot-banking-api.git)
   cd spring-boot-banking-api
   ```

2. Start the app:
   ```bash
   ./mvnw spring-boot:run
   ```
   *(Or just run `BankApiApplication.java` directly from IntelliJ)*

3. The server runs on port `8081`:
   ```text
   http://localhost:8081
   ```

4. You can check the H2 web console at `http://localhost:8081/h2-console` (JDBC URL: `jdbc:h2:mem:bankdb`, User: `sa`, Leave password blank).

---

## Testing

I also exported the Postman collection used for manual testing. You can find it inside the `/postman` folder (`Bank_API.postman_collection.json`) and import it right away.