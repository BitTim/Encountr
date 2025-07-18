/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       main.rs
 * Module:     Encountr
 * Author:     Tim Anhalt (BitTim)
 * Modified:   18.07.25, 20:26
 */

pub use self::error::{Error, Result};

mod crypt;
pub mod db;
mod error;
pub mod web;

use crate::web::auth::auth_routes;
use axum::Router;
use axum::response::Html;
use axum::routing::get;
use sqlx::PgPool;
use tokio::net::TcpListener;

fn api_routes(pool: PgPool) -> Router {
    Router::new().nest("/auth", auth_routes(pool.clone()))
}

#[tokio::main]
async fn main() -> Result<()> {
    let pool = db::init().await?;

    let app_router = Router::new().nest("/api", api_routes(pool)).route(
        "/",
        get(|| async { Html("This page is under construction") }),
    );

    let listener = TcpListener::bind("127.0.0.1:3000").await.unwrap();
    axum::serve(listener, app_router).await.unwrap();

    Ok(())
}
