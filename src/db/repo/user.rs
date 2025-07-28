/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       user.rs
 * Module:     Encountr
 * Author:     Tim Anhalt (BitTim)
 * Modified:   29.07.25, 01:10
 */
use crate::db::entity::user::UserFull;
use crate::{Error, Result};
use sqlx::PgPool;

pub(crate) enum UserQueries {
    Create,
    ExistsByEmail,
    GetByID,
    GetByEmail,
    UpdatePwd,
}

impl UserQueries {
    fn value(&self) -> &'static str {
        match self {
            UserQueries::Create => {
                "INSERT INTO \"user\" (email, name) VALUES ($1, $2) RETURNING id, email, name, pwd, pqd_salt, token_salt, refresh_token"
            }
            UserQueries::ExistsByEmail => {
                "SELECT EXISTS (SELECT 1 FROM \"user\" WHERE id = $1 AND email = $2)"
            }
            UserQueries::GetByID => {
                "SELECT id, email, name, pwd, salt, refresh_token FROM \"user\" WHERE id = $1"
            }
            UserQueries::GetByEmail => {
                "SELECT id, email, name, pwd, salt, refresh_token FROM \"user\" WHERE email = $1"
            }
            UserQueries::UpdatePwd => "UPDATE \"user\" SET pwd = $2 WHERE id = $1",
        }
    }
}

pub(crate) async fn exists_by_email(
    query: UserQueries,
    pool: &PgPool,
    email: &String,
) -> Result<bool> {
    Ok(sqlx::query_scalar(query.value())
        .bind(&email)
        .fetch_one(pool)
        .await
        .map_err(|e| Error::Generic { msg: e.to_string() })?)
}

pub(crate) async fn create() -> Result<UserFull> {
    Ok(sqlx::query_as(query)
        .bind(&user_fi.email)
        .bind(&user_fi.name)
        .fetch_one(pool)
        .await
        .map_err(|e| Error::Generic { msg: e.to_string() })?)
}

pub(crate) async fn query(query: UserQueries, pool: &PgPool, id: &String) -> Result<UserFull> {
    Ok(sqlx::query_as(query.value())
        .bind(&id)
        .fetch_one(pool)
        .await
        .map_err(|e| Error::Generic { msg: e.to_string() })?)
}
