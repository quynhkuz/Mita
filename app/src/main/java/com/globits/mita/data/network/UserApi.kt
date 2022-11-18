package com.globits.mita.data.network

import com.globits.mita.data.model.User
import io.reactivex.Observable
import retrofit2.http.GET


interface UserApi {
    @GET("users/get-user-current")
    fun getCurrentUser(): Observable<User>

}