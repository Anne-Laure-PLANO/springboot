# AGENTS.md

## Structure

Two independent Spring Boot Maven modules (NOT a multi-module POM):

| Module | Artifact | Port | Entrypoint | Group |
|--------|----------|------|------------|-------|
| `square_game/` | `square_game` | 8081 | `com.mon_projet.demo.DemoApplication` | `com.mon_projet.demo` |
| `users/` | `users` | 8082 | `com.example.users.UsersApplication` | `com.example.users` |

No root `pom.xml` — build/test each module from its own directory.

## Prerequisites

- Java 21, Docker (for PostgreSQL containers via `compose.yml`)
- `.env` files: `square_game/.env` and `users/.env` both need `POSTGRES_PASSWORD=demo`
- `.env` files use Docker Compose colon syntax (`POSTGRES_PASSWORD:demo`) — **not** `KEY=VALUE`
- External engine dependency: `fr.le-campus-numerique.square-games:engine:1.0-SNAPSHOT` — must be in local `.m2` or a configured remote. Without it `square_game` won't compile.
- Repository for snapshots: `https://repo.spring.io/snapshot`

## Commands

```bash
# Start databases
docker compose up -d

# Build & test each module (run from its own directory)
./mvnw clean verify   # from square_game/ or users/
```

## Architecture — square_game

- Plugin-based game system: `GamePlugin` interface → `TicTacToePlugin`, `ConnectFourPlugin`, `TaquinPlugin` (all `@Component`)
- Three DAO implementations coexist:
  - `InMemoryGameDao` (`@Service`) — complete HashMap implementation
  - `JdbcGameDao` (`@Repository`) — partial: `upsert()` works; `findAll()`, `findById()`, `delete()` are stubs
  - `JpaGameDao` (`@Repository`, `@Primary`) — partial: `findById()` and `upsert()` work; `findAll()` and `delete()` are stubs
- `GameController` has **no** `@RequestMapping` — endpoints at root: `POST /games`, `GET /games/{id}`, `GET /games/{id}/status`, `GET /games/{id}/tokens`, `POST /games/{id}/moves`
- `CatalogController`: `GET /catalog` (i18n via `Accept-Language`)
- `HeartbeatController`: `GET /heartbeat`
- `spring.main.allow-bean-definition-overriding=true` in `application.properties`
- JPA: `spring.jpa.hibernate.ddl-auto=update` — tables auto-created; `schema.sql` is for JDBC only and contains **invalid SQL** (`VAR` not a PG type)
- I18n: `messages.properties` / `messages_fr.properties` with `spring.messages.basename=messages`. **Bug**: actual file is named `message_fr.properties` (missing `s`) — French translations won't load
- `GameCreationParameters` record: `(String factoryId, Integer playerCount, Integer boardSize)`
- Plugin config: `@Value("${game.<name>.default-player-count}")` and `@Value("${game.<name>.default-board-size}")`

## Architecture — users (scaffolding, broken)

- `@Controller("/users")` on `UsersController` is **wrong** — needs `@RestController` + `@RequestMapping("/users")`
- All controller methods use `@RequestParam` but map via `/{id}` — should be `@PathVariable`; all return `null`
- **Database name mismatch**: `compose.yml` creates database `demo` on port 5433, but `application.properties` points to `jdbc:postgresql://localhost:5433/users` — startup will fail
- `spring-boot-starter-data-jpa` is **missing** from `pom.xml`; `JpaUserRepository` and `spring.jpa.hibernate.ddl-auto=update` config won't work
- `UserService` / `UserRepository` interfaces are empty; `UserServiceImpl` is a stub
- `.env` is empty (0 bytes)

## Tests

- **Only one test**: `users/src/test/.../UsersApplicationTests.java` — basic `@SpringBootTest` context load
- `square_game` has **no test directory at all**
- Run all: `./mvnw test` (or `verify`)
