/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       register.rs
 * Module:     Encountr
 * Author:     Tim Anhalt (BitTim)
 * Modified:   23.07.25, 00:08
 */
use crate::{Error, Result, crypt, db};
use axum::Json;
use axum::extract::State;
use serde::Deserialize;
use sqlx::PgPool;

#[derive(Debug, Deserialize)]
pub(crate) struct RegisterParams {
    pub(crate) email: String,
    pub(crate) name: String,
    pub(crate) pwd_clear: String,
    pub(crate) reg_token: String,
}

pub(crate) async fn register_handler(
    State(pool): State<PgPool>,
    Json(params): Json<RegisterParams>,
) -> Result<()> {
    let exists = db::entity::user::exists_email(&params.email, &pool).await?;
    if exists {
        return Err(Error::UserExists);
    }

    if params.reg_token != "SuperSecret_TestingToken" {
        return Err(Error::Generic {
            msg: "Invalid reg_token".to_string(),
        });
    }

    let user_fi = db::entity::user::UserForInsert {
        email: params.email.clone(),
        name: params.name.clone(),
    };

    let new_user = db::entity::user::create(&user_fi, &pool).await?;
    let pwd = crypt::salt_and_hash(&new_user.pwd_salt.to_string(), params.pwd_clear);
    db::entity::user::update_pwd(&new_user.id, &pwd, &pool).await?;

    // TODO: Generate and return JWT

    Ok(())
}
