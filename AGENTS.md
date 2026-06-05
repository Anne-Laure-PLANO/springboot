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
- **Root `.env`** (next to `compose.yml`) must have `POSTGRES_PASSWORD:demo` — Docker Compose reads `.env` from the project root, **not** from module subdirectories.
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
- I18n: `messages.properties` / `messages_fr.properties`
- `GameCreationParameters` record: `(String factoryId, Integer playerCount, Integer boardSize)`
- Plugin config: `@Value("${game.<name>.default-player-count}")` and `@Value("${game.<name>.default-board-size}")`
- Tests: `GameControllerTest` uses pure Mockito (no `@WebMvcTest`, no Jackson serialization), `CatalogControllerTest` & `HeartbeatControllerTest` use `@WebMvcTest`

## Architecture — users

- `UsersController`: `@RestController` + `@RequestMapping("/users")` with `@PathVariable` and full CRUD via `UserServiceImpl`
- `UserService` interface: `createUser`, `getUserById`, `deleteUser`, `isUserExist`
- `UserServiceImpl`: backed by `JpaUserRepository` (JPA) with entity↔domain conversion
- `JpaUserRepository` extends `JpaRepository<UserEntity, UUID>` — Hibernate auto-creates `users` table
- `UserEntity`: JPA `@Entity` with `@Id`, `@Column(nullable=false)`, no-arg constructor
- **Database name mismatch**: `compose.yml` creates database `demo` on port 5433, but `application.properties` points to `jdbc:postgresql://localhost:5433/users` — startup will fail unless you create the `users` DB manually or fix the compose.yml

## Tests

- **users**: `UsersApplicationTests.java` — basic `@SpringBootTest` context load (requires Docker DB running)
- **square_game**: 35 tests total (12 `GameServiceTest`, 8 `InMemoryGameDaoTest`, 9 `GameControllerTest`, 3 `GameCatalogImplTest`, 2 `CatalogControllerTest`, 1 `HeartbeatControllerTest`)
- Run all for a module: `./mvnw test` (or `verify`)
