/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       0001_init.sql
 * Module:     Encountr
 * Author:     Tim Anhalt (BitTim)
 * Modified:   18.07.25, 20:24
 */

CREATE TABLE "user"
(
    id            UUID NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),
    email         TEXT NOT NULL UNIQUE,
    name          TEXT NOT NULL,
    pwd           TEXT,
    salt          UUID NOT NULL             DEFAULT gen_random_uuid(),
    refresh_token TEXT
);

CREATE TABLE save
(
    id      UUID NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),
    "user"  UUID NOT NULL REFERENCES "user" (id) ON DELETE CASCADE ON UPDATE CASCADE,
    game_id TEXT NOT NULL
);

CREATE TABLE pokemon
(
    id         UUID    NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),
    save       UUID    NOT NULL REFERENCES save (id) ON DELETE CASCADE ON UPDATE CASCADE,
    pokemon_id TEXT    NOT NULL,
    caught     Boolean NOT NULL             DEFAULT false
);

CREATE TABLE team
(
    id   UUID NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),
    save UUID NOT NULL REFERENCES save (id) ON DELETE CASCADE ON UPDATE CASCADE,
    name TEXT NOT NULL
);

CREATE TABLE pt_rel
(
    pokemon UUID NOT NULL REFERENCES pokemon (id) ON DELETE CASCADE ON UPDATE CASCADE,
    team    UUID NOT NULL REFERENCES team (id) ON DELETE CASCADE ON UPDATE CASCADE,

    PRIMARY KEY (pokemon, team)
);