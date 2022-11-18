package com.globits.mita.data.repository

import com.globits.mita.data.model.TokenResponse
import com.globits.mita.data.model.UserCredentials
import com.globits.mita.data.network.AuthApi
import com.globits.mita.data.network.SessionManager
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    val api: AuthApi,
    val preferences: SessionManager
) {
    fun login(username: String, password: String): Observable<TokenResponse> = api.oauth(
        UserCredentials(
            AuthApi.CLIENT_ID,
            AuthApi.CLIENT_SECRET,
            username,
            password,
            null,
            AuthApi.GRANT_TYPE_PASSWORD
        )
    ).subscribeOn(Schedulers.io())

    fun saveAccessTokens(tokenResponse: TokenResponse) {

    }

}