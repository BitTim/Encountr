/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       crypt.rs
 * Module:     Encountr
 * Author:     Tim Anhalt (BitTim)
 * Modified:   18.07.25, 20:26
 */

pub(crate) fn salt_and_hash(salt: &str, value: String) -> String {
    //TODO: Implement this

    format!("{salt}{value}")
}
