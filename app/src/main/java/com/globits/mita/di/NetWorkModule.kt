package com.globits.mita.di

import android.content.Context
import com.globits.mita.data.network.*
import com.globits.mita.data.repository.AuthRepository
import com.globits.mita.data.repository.LabTestRepository
import com.globits.mita.data.repository.TestRepository
import com.globits.mita.data.repository.UserRepository
import dagger.Module
import dagger.Provides

@Module
object NetWorkModule {

    @Provides
    fun labTestRepository(labTestApi : LabTestApi): LabTestRepository = LabTestRepository(labTestApi)

    @Provides
    fun providerLabTestApi(
        remoteDataSource: RemoteDataSource,
        context: Context
    ) = remoteDataSource.buildApi(LabTestApi::class.java, context)

    @Provides
    fun providerRemoteDateSource(): RemoteDataSource = RemoteDataSource()


    @Provides
    fun providerUserPreferences(context: Context): SessionManager = SessionManager(context)

    @Provides
    fun providerAuthApi(
        remoteDataSource: RemoteDataSource
    ) = remoteDataSource.buildApiAuth(AuthApi::class.java)

    @Provides
    fun providerAuthRepository(
        userPreferences: SessionManager,
        api: AuthApi
    ): AuthRepository = AuthRepository(api, userPreferences)

    @Provides
    fun providerUserApi(
        remoteDataSource: RemoteDataSource,
        context: Context
    ) = remoteDataSource.buildApi(UserApi::class.java, context)

    @Provides
    fun providerUserRepository(
        api: UserApi
    ): UserRepository = UserRepository(api)

    @Provides
    fun providerTestApi(
        remoteDataSource: RemoteDataSource,
        context: Context
    ) = remoteDataSource.buildApi(TestApi::class.java, context)

    @Provides
    fun providerTestRepository(
        api: TestApi
    ): TestRepository = TestRepository(api)

}