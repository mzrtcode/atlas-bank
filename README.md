# 🏦 Atlas Bank

Sistema de bancario usando arquitectura hexagonal + DDD.

## 🚀 Tech Stack

- **Backend:** Spring Boot 4.0.6 | Java 25 | Gradle 9.5.1
- **Base de datos:** H2 in-memory (`jdbc:h2:mem:atlasbank`)
- **IA:** Spring AI + OpenAI (agentes integrados)
- **ORM:** Spring Data JPA | Lombok | MapStruct 1.6.3
- **Testing:** JUnit 5 | ArchUnit (validación arquitectónica)
- **Auth:** OAuth2 Resource Server (deshabilitado por defecto)

## 📁 Estructura del Proyecto

```
📂 src/main/java/com/mzrt/atlas_bank/
├── 📂 domain/             # Lógica de negocio pura (sin Spring)
│   ├── 📂 model/          # Entidades y Value Objects
│   ├── 📂 service/        # Domain Services
│   ├── 📂 validation/     # Validadores
│   └── 📂 event/          # Domain Events
├── 📂 application/        # Casos de uso
│   ├── 📂 command/        # Comandos (CreateAccount, Transfer, CloseAccount)
│   ├── 📂 query/          # Queries
│   ├── 📂 port/           # Interfaces (in/out)
│   └── 📂 facade/         # Orquestadores
└── 📂 infrastructure/     # Framework specifics
    ├── 📂 adapter/        # Controllers, JPA entities, config
    ├── 📂 mapper/         # Mappers Domain ↔ Persistence
    └── 📂 listener/       # Event Listeners
```

## 🏗️ Arquitectura

**Hexagonal (Ports & Adapters) + Domain-Driven Design**

Reglas (validadas con ArchUnit):
- 🚫 `domain` NO importa Spring, `application`, `infrastructure`
- 🚫 `application` NO importa `infrastructure`
- Controllers → `infrastructure.adapter.in.rest`
- Puertos → siempre interfaces

Patrones implementados:
- **Template Method:** `TransactionProcessor` (base para nuevos tipos de transacciones)
- **Strategy:** `TransactionState` (máquina de estados para transacciones)
- **Decorator:** `CachedAccountService` (cacheo transparente)
- **Event-Driven:** `TransactionExecutedEvent` → listeners síncronos

## 🎯 Features

| Feature | Endpoint | Status |
|---------|----------|--------|
| Crear cuenta | `POST /api/v1/accounts` | ✅ |
| Listar cuentas | `GET /api/v1/accounts` | ✅ |
| Ver cuenta | `GET /api/v1/accounts/{id}` | ✅ |
| Dashboard de cuenta | `GET /api/v1/accounts/{id}/dashboard` | ✅ |
| Transferencias | `POST /api/v1/transactions/transfer` | ✅ |
| Historial de transacciones | `GET /api/v1/transactions/{id}/transactions` | ✅ |
| Cerrar cuenta | `POST /api/v1/accounts/{id}/close` | ✅ |
| Chat con agente IA | `/api/v1/ai/chat` | ✅ |

## 🚀 Cómo Correrlo

### Prerequisites
- Java 25+
- Gradle 9.5.1+

### Startup

```bash
# Build completo
./gradlew build

# Tests
./gradlew test

# Run (por defecto en :8080)
./gradlew bootRun
```

### Con Docker (incluye Keycloak para auth)

```bash
docker compose -f docker/docker-compose.yml up -d
./gradlew bootRun
```

### Acceso

- **API:** http://localhost:8080
- **H2 Console:** http://localhost:8080/h2-console (sin auth)
  - JDBC URL: `jdbc:h2:mem:atlasbank`
  - User: `sa` | Password: (vacío)

## 🔒 Security

OAuth2 actualmente **deshabilitado** (ver `SecurityConfig`). Para habilitarlo:
1. Descomentar bean `securityFilterChain`
2. Comentar `disableSecurityFilterChain`
3. Keycloak debe estar en http://localhost:8181/realms/atlas_bank

## 🧪 Testing

```bash
./gradlew test
```

Tests organizados por capas:
- `archtest/` — Validación de arquitectura (ArchUnit)
- `domain/model/` — Unit tests sin Spring
- `infrastructure/adapter/` — Integration tests con Spring

## 📚 Key Design Notes

### Value Objects
Domain tiene `Money` y `Email` propios. Persistence tiene entidades JPA separadas mapeadas vía `*PersistenceMapper` (manual, no MapStruct).

### DTOs y Mapeos
- API DTOs mapeados con **MapStruct** (`@Mapper(componentModel = "spring")`)
- Domain events publicados síncronamente → `AuditListener`, `NotificationListener`

### Convenciones
- Lombok: `@Getter @Setter @Builder @RequiredArgsConstructor @Slf4j`
- Sin linting/formatting configurados (propósito educativo)
