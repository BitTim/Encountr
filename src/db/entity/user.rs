/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       user.rs
 * Module:     Encountr
 * Author:     Tim Anhalt (BitTim)
 * Modified:   18.07.25, 20:24
 */

use crate::{Error, Result};
use sqlx::{FromRow, PgPool};
use uuid::Uuid;

#[derive(FromRow)]
pub struct UserFull {
    pub id: Uuid,
    pub email: String,
    pub name: String,
    pub pwd: Option<String>,
    pub salt: Uuid,
    pub refresh_token: Option<String>,
}

pub struct UserForCreate {
    pub email: String,
    pub name: String,
    pub pwd: Option<String>,
    pub refresh_token: Option<String>,
}

async fn create(user_fc: &UserForCreate, pool: &PgPool) -> Result<()> {
    let query = "INSERT INTO \"user\" (email, name) VALUES ($1, $2)";
    sqlx::query(query)
        .bind(&user_fc.email)
        .bind(&user_fc.name)
        .execute(pool)
        .await
        .map_err(|e| Error::Generic { msg: e.to_string() })?;

    Ok(())
}

async fn read(id: &Uuid, pool: &PgPool) -> Result<UserFull> {
    let query = "SELECT id, email, name, pwd, salt, refresh_token FROM \"user\" WHERE id = $1";
    let user: UserFull = sqlx::query_as(query)
        .bind(&id)
        .fetch_one(pool)
        .await
        .map_err(|e| Error::Generic { msg: e.to_string() })?;

    Ok(user)
}

pub(crate) async fn read_by_email(email: &str, pool: &PgPool) -> Result<UserFull> {
    let query = "SELECT id, email, name, pwd, salt, refresh_token FROM \"user\" WHERE email = $1";
    let user: UserFull = sqlx::query_as(query)
        .bind(&email)
        .fetch_one(pool)
        .await
        .map_err(|e| Error::Generic { msg: e.to_string() })?;

    Ok(user)
}

pub(crate) async fn update(id: &Uuid, user_fc: &UserForCreate, pool: &PgPool) -> Result<()> {
    let query =
        "UPDATE \"user\" SET email = $2, name = $3, pwd = $4, refresh_token = $5 WHERE id = $1";
    sqlx::query(query)
        .bind(&id)
        .bind(&user_fc.email)
        .bind(&user_fc.name)
        .bind(&user_fc.pwd)
        .bind(&user_fc.refresh_token)
        .execute(pool)
        .await
        .map_err(|e| Error::Generic { msg: e.to_string() })?;

    Ok(())
}
