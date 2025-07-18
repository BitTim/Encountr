/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       mod.rs
 * Module:     Encountr
 * Author:     Tim Anhalt (BitTim)
 * Modified:   18.07.25, 20:26
 */

use axum::Router;
use axum::routing::post;
use sqlx::PgPool;

pub mod login;
pub mod register;

pub(crate) fn auth_routes(pool: PgPool) -> Router {
    Router::new()
        .route("/login", post(login::login_handler))
        .with_state(pool)
}
