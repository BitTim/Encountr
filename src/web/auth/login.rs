/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       login.rs
 * Module:     Encountr
 * Author:     Tim Anhalt (BitTim)
 * Modified:   18.07.25, 20:26
 */

use crate::{Error, Result, crypt, db};
use axum::Json;
use axum::extract::State;
use serde::Deserialize;
use sqlx::PgPool;

#[derive(Debug, Deserialize)]
pub(crate) struct LoginParams {
    pub(crate) email: String,
    pub(crate) pwd_clear: String,
}

pub(crate) async fn login_handler(
    State(pool): State<PgPool>,
    Json(params): Json<LoginParams>,
) -> Result<()> {
    let user = db::entity::user::read_by_email(&params.email, &pool)
        .await
        .map_err(|e| Error::Generic {
            msg: format!("{} ({:?})", e.to_string(), &params),
        })?;
    let pwd = crypt::salt_and_hash(&user.salt.to_string(), params.pwd_clear);

    if user.pwd.is_none_or(|value| value != pwd) {
        return Err(Error::UserNotFound {
            email: params.email,
        });
    }

    // TODO: Generate and return JWT

    Ok(())
}
