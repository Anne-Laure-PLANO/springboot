# AGENTS.md

## Structure

Two independent Spring Boot Maven modules (NOT a multi-module POM):

| Module | Artifact | Port | Entrypoint | Group |
|--------|----------|------|------------|-------|
| `square_game/` | `square_game` | 8081 | `com.mon_projet.demo.DemoApplication` | `com.mon_projet.demo` |
| `users/` | `users` | 8082 | `com.example.users.UsersApplication` | `com.example.users` |

No root `pom.xml` — build/test each module from its own directory.

## Prerequisites

- Java 21
- Docker (for PostgreSQL containers via `compose.yml`)
- `.env` files: `square_game/.env` and `users/.env` both need `POSTGRES_PASSWORD` (set `demo` in dev)
- External engine dependency: `fr.le-campus-numerique.square-games:engine:1.0-SNAPSHOT` — must be available in local Maven repo or a configured remote (not in this repo). If missing, the `square_game` module will fail to compile.

## Commands

```bash
# Start databases
docker compose up -d

# Build & test square_game
./mvnw clean verify   # from square_game/

# Build & test users
./mvnw clean verify   # from users/

# Run square_game
./mvnw spring-boot:run   # from square_game/
```

## Architecture — square_game

- Plugin-based game system: `GamePlugin` interface → `TicTacToePlugin`, `ConnectFourPlugin`, `TaquinPlugin` (all `@Component`)
- Three DAO implementations coexist:
  - `InMemoryGameDao` (`@Service`) — in-memory HashMap
  - `JdbGameDao` (`@Repository`) — NamedParameterJdbcTemplate (PARTIAL — `findById`/`delete` are stubs)
  - `JpaGameDao` (`@Repository`, `@Primary`) — JPA with `GameEntity`/`GameTokenEntity`
- `spring.main.allow-bean-definition-overriding=true` (set in `square_game/src/main/resources/application.properties`)
- JPA uses `spring.jpa.hibernate.ddl-auto=update` — tables auto-created; `schema.sql` is only for JDBC approach
- I18n: `messages.properties` / `message_fr.properties` with `spring.messages.basename=messages`
- Game endpoints: `POST /games`, `GET /games/{id}`, `GET /games/{id}/status`, `GET /games/{id}/tokens`, `POST /games/{id}/moves`
- Catalog endpoint: `GET /catalog` (accepts `Locale` via `Accept-Language` header)
- Heartbeat: `GET /heartbeat`

## Architecture — users (INCOMPLETE / SCAFFOLDING)

- `@Controller("/users")` in `UsersController` is INCORRECT — use `@RestController` + `@RequestMapping("/users")` instead. `@Controller("/path")` does not map requests in Spring.
- `UserService` and `UserRepository` interfaces are empty; implementations are stubs
- No `spring-boot-starter-data-jpa` in `pom.xml`, though `JpaUserRepository` expects it
- `.env` file is empty — must be populated before Docker Compose will work

## Config conventions

- `@Value("${game.<name>.default-player-count}")` and `@Value("${game.<name>.default-board-size}")` in each plugin class
- `GameCreationParameters` is a `record`: `(String factoryId, Integer playerCount, Integer boardSize)`
- `compose.yml` maps PostgreSQL to ports 5432 (game) and 5433 (users)
- Repository XML for snapshots: `https://repo.spring.io/snapshot`

## Tests

- Only one test exists: `users/src/test/.../UsersApplicationTests.java` — basic `@SpringBootTest` context load
- No `square_game` tests yet
- Run all tests: `./mvnw test` or `./mvnw verify`
