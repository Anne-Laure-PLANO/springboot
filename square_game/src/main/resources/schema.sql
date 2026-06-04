
/*
 Utilisé pour l'approche JBDC ; inutile pour une approche JBA car Hibernate créé les tables tout seul.
 */
       CREATE TABLE IF NOT EXISTS "game"
       (
           "id"         UUID  NOT NULL,
           "factory_id" VAR(50),
           "board_size" int,
           "player_a"   UUID,
           "player_b"   UUID,
           PRIMARY KEY ("id")
       );

       CREATE TABLE IF NOT EXISTS cell
       (
           "id" SERIAL PRIMARY KEY,
           "x" int,
           "y" int,
           "owner_id" UUID,
           "name" VARCHAR(50),
           "removed" boolean,
           "id_game" UUID,
           FOREIGN KEY ("id_game") REFERENCES game(id)
       );


