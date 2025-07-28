/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       env.rs
 * Module:     Encountr
 * Author:     Tim Anhalt (BitTim)
 * Modified:   28.07.25, 18:45
 */
use std::env;
use std::string::ToString;

pub struct Env {
    jwt_secret: String,
}

impl Env {
    pub fn new() -> Self {
        Self {
            jwt_secret: env::var("JWT_SECRET")
                .unwrap_or_else(|_| "SuperSecretJWTSecret".to_string()),
        }
    }
}
