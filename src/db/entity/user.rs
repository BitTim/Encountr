/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       user.rs
 * Module:     Encountr
 * Author:     Tim Anhalt (BitTim)
 * Modified:   28.07.25, 21:49
 */

use crate::{Error, Result};
use sqlx::{FromRow, PgPool};
use uuid::Uuid;

#[derive(FromRow)]
pub struct UserFull {
    pub id: Uuid,
    pub email: String,
    pub name: String,
    pub role: String,
    pub pwd: Option<String>,
    pub pwd_salt: Uuid,
    pub token_salt: Uuid,
    pub refresh_token: Option<String>,
}

pub struct UserForInsert {
    pub email: String,
    pub name: String,
}

pub struct UserForCreate {
    pub email: String,
    pub name: String,
    pub pwd: Option<String>,
    pub refresh_token: Option<String>,
}

pub(crate) async fn exists_email(email: &str, pool: &PgPool) -> Result<bool> {
    let query = "SELECT EXISTS (SELECT 1 FROM \"user\" WHERE id = $1 AND email = $2)";
    let exists = sqlx::query_scalar(query)
        .bind(&email)
        .fetch_one(pool)
        .await
        .map_err(|e| Error::Generic { msg: e.to_string() })?;

    Ok(exists)
}

pub(crate) async fn create(user_fi: &UserForInsert, pool: &PgPool) -> Result<UserFull> {
    let query = "";
    let user: UserFull = sqlx::query_as(query)
        .bind(&user_fi.email)
        .bind(&user_fi.name)
        .fetch_one(pool)
        .await
        .map_err(|e| Error::Generic { msg: e.to_string() })?;

    Ok(user)
}

pub(crate) async fn read(id: &Uuid, pool: &PgPool) -> Result<UserFull> {
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

pub(crate) async fn update_pwd(id: &Uuid, pwd: &str, pool: &PgPool) -> Result<()> {
    let query = "UPDATE \"user\" SET pwd = $2 WHERE id = $1";
    sqlx::query(query)
        .bind(&id)
        .bind(&pwd)
        .execute(pool)
        .await
        .map_err(|e| Error::Generic { msg: e.to_string() })?;

    Ok(())
}
