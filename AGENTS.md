# atlas-bank

## Stack
- Spring Boot 4.0.6 / Java 25 / Gradle 9.5.1
- Spring Data JPA, WebMVC, Security (OAuth2 RS), Cache
- Lombok, MapStruct 1.6.3, H2 in-memory, ArchUnit

## Architecture
Hexagonal (ports & adapters) + DDD:

```
domain/          → pure Java, zero framework deps (no Spring imports)
application/     → use cases, commands, queries, port interfaces
infrastructure/  → controllers, JPA entities, config, listeners
```

Rules (enforced by ArchUnit tests):
- `domain` MUST NOT depend on `infrastructure`, `application`, or `org.springframework`
- `application` MUST NOT depend on `infrastructure`
- Controllers → `..infrastructure.adapter.in.rest..`
- UseCase interfaces → `..application.port.in..`
- Port in/out → always interfaces

Do NOT import domain classes from infrastructure into application or domain layers.

## Commands
```sh
./gradlew build        # full build
./gradlew test         # all tests (JUnit Platform)
./gradlew bootRun      # start app on :8080
```

No lint/format/typecheck configuration present.

## Key design choices
- `application/service/TransactionProcessor.java` is a template-method base class — new transaction types extend it
- `CachedAccountService` wraps `AccountService` — use `@Cacheable("accounts")` directly via the decorated service
- Event-driven: `TransactionExecutedEvent` (Java record) published synchronously, consumed by `AuditListener` and `NotificationListener`
- Domain has its own `Money` and `Email` value objects; persistence has separate JPA `Money`/`Email` — mapping done in `*PersistenceMapper` classes
- `*PersistenceMapper` classes are manual (not MapStruct). API DTO mappers (`AccountMapper`, `TransactionMapper`) use MapStruct with `componentModel = "spring"`
- Lombok everywhere: `@Getter @Setter @Builder @RequiredArgsConstructor @Slf4j`
- `Transaction` has a state machine via `TransactionState` strategy; state transitions: Pending → Validated → Executed (or Rejected/Reversed)

## Security
Currently **disabled** — `disableSecurityFilterChain` `@Bean` is active, the real chain is commented out (`//@Bean`).
Full OAuth2 config references Keycloak at `http://localhost:8181/realms/atlas_bank`.
Start auth infra:
```sh
docker compose -f docker/docker-compose.yml up -d
```

## Database
- H2 in-memory (`jdbc:h2:mem:atlasbank`), `ddl-auto: create-drop`
- Console at `/h2-console`

## Testing
- `src/test/java/com/mzrt/atlas_bank/archtest/` — HexagonalArchitectureTest, NamingConventionTest, SecurityIsolationTest, NoCyclicDependenciesTest
- Domain tests in `domain/model/` — pure unit tests (no Spring context)
- `AtlasBankApplicationTests` — `@SpringBootTest` context load
- Single-module Gradle; no test tags or categories

## API
| Method | Path | Description |
|--------|------|-------------|
| POST   | `/api/v1/accounts` | Create account |
| GET    | `/api/v1/accounts` | List accounts |
| GET    | `/api/v1/accounts/{id}` | Get account |
| GET    | `/api/v1/accounts/{id}/dashboard` | Account dashboard |
| POST   | `/api/v1/transactions/transfer` | Transfer money |
| GET    | `/api/v1/transactions/{id}/transactions` | Transactions by account |
