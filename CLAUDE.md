# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run Commands

```bash
./mvnw spring-boot:run          # Start the application (port 8091)
./mvnw test                     # Run all tests
./mvnw clean package            # Build deployable JAR
./mvnw test -Dtest=ClassName    # Run a single test class
```

**Prerequisites:** PostgreSQL running on `localhost:5432` with database `valtxarq`, user `postgres`, password `admin` (overrideable via env vars `POSTGRESQL_URL`, `POSTGRESQL_USERNAME`, `POSTGRESQL_PASSWORD`).

Swagger UI available at `http://localhost:8091/swagger-ui.html` once running (disabled in production via `springdoc.swagger-ui.enabled=false`).

## Architecture

This project follows **Hexagonal Architecture (Ports & Adapters)** with four layers:

```
infraestructure/controller  →  application/usecase  →  domain  ←  infraestructure/repository
```

- **`domain/`** — Pure POJO models (`Categoria`, `Producto`) and repository port interfaces (`ICategoriaRepository`, `IProductoRepository`). No framework dependencies.
- **`application/`** — Use case ports (interfaces under `ports/`) and their implementations (under `usecase/`). Contains DTOs and MapStruct mappers for Domain ↔ DTO conversion.
- **`infraestructure/`** — REST controllers, JPA entities, Spring Data JPA repositories (`jpa/`), adapter implementations of domain repository interfaces, and MapStruct mappers for Entity ↔ Domain conversion.
- **`shared/`** — Cross-cutting concerns: `GlobalExceptionHandler` (`@RestControllerAdvice`), custom exceptions, pagination (`PageRequest`/`PageResponse`), and `OpenAPIConfig`.

## Key Design Decisions

- **Two-level mapping:** App mappers handle Domain ↔ DTO (application layer); Infra mappers handle Entity ↔ Domain (infrastructure layer). All mappers use MapStruct.
- **Use case per operation:** Each CRUD operation is a separate class implementing its own interface (e.g., `GetCategoriaUseCase` → `GetCategoriaUseCaseImpl`).
- **Repository adapters:** `*RepositoryImpl` classes implement domain interfaces delegating to Spring Data JPA repositories in `jpa/`. Keeps domain free of JPA.
- **Pagination:** Custom `PageRequest`/`PageResponse` wrappers with JPA `Specification` for dynamic filtering. Filter DTOs extend `PageRequest`. Paginated endpoints at `GET /api/{entity}/paginated`.
- **`ddl-auto=none`:** Schema managed manually — Hibernate does not auto-create or migrate tables.
- **`@EntityGraph`** on JPA queries to avoid N+1 problems when loading relationships.
- **`CreateCategoriaUseCaseImpl` uses `IProductoRepository` directly** (not via `CreateProductoUseCase`) — intentional decision for performance: allows `saveAll()` batch insert when creating a category with products. Violates strict aggregate separation but accepted consciously.

## DTO Conventions

| DTO | Purpose |
|---|---|
| `*Dto` | Read/response — includes `id` |
| `*SaveDto` | Create — no `id`, full fields |
| `*UpdateDto` | Update — no `id`, only updatable fields |
| `*SaveSimpleDto` | Create nested inside parent (e.g. productos inside CategoriaSaveDto) |
| `*SimpleDto` | Read nested inside parent response — includes `id` |
| `*FilterDto` | Paginated search — extends `PageRequest` |

All input DTOs (`SaveDto`, `UpdateDto`, `SaveSimpleDto`) have Bean Validation annotations (`@NotBlank`, `@NotNull`, `@Positive`, etc.). Controllers use `@Valid` on `@RequestBody`.

## Exception Hierarchy

```
CustomException (abstract, HttpStatus)
├── ResourceNotFoundException   → 404
└── BusinessRuleException       → 409
```

- `GlobalExceptionHandler` handles `CustomException`, `MethodArgumentNotValidException` (400), and generic `Exception` (500 with safe message, real error logged via @Slf4j).
- Use `ResourceNotFoundException` for missing records.
- Use `BusinessRuleException` for business rule violations (e.g. deleting a category that has products).

## Domain Entities

- `Categoria`: `id`, `nombre`, `descripcion`
- `Producto`: `id`, `nombre`, `descripcion`, `precio`, `stock`, FK → `Categoria` (LAZY fetch)
- Relationship: One `Categoria` → Many `Producto`

## Pending

- **A05 OWASP:** `spring.jpa.show-sql=true` and Swagger enabled in `application.properties` — should be moved to a `dev` profile before production deploy. For now handled manually before building the JAR.
- **sortBy whitelist:** `sortBy` query param accepts any string — no validation against allowed field names.
