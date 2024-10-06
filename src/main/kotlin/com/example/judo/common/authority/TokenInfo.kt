package com.example.judo.common.authority

import lombok.NoArgsConstructor

@NoArgsConstructor
data class TokenInfo (
    val grantType: String,
    var accessToken: String,
    var refreshToken: String?,
)
