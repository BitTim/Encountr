/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       mod.rs
 * Module:     Encountr
 * Author:     Tim Anhalt (BitTim)
 * Modified:   18.07.25, 20:24
 */

pub mod entity;

use crate::{Error, Result};
use sqlx::PgPool;

pub(crate) async fn init() -> Result<PgPool> {
    let url = "postgres://db:password@localhost:5432/testing"; // TODO: Get from env variables
    let pool = PgPool::connect(url)
        .await
        .map_err(|_| Error::InvalidPostgresURL)?;

    sqlx::migrate!("./migrations")
        .run(&pool)
        .await
        .map_err(|_| Error::MigrationsFailed)?;
    Ok(pool)
}
