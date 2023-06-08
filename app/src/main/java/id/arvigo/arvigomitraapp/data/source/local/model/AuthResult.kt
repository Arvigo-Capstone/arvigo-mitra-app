package id.arvigo.arvigomitraapp.data.source.local.model

import id.arvigo.arvigomitraapp.utils.Resource

data class AuthResult(
    val passwordError: String? = null,
    val emailError : String? = null,
    val result: Resource<Unit>? = null
)