package com.example.myphotoviwer.presentation.module

import com.example.myphotoviwer.data.repository.RepositoryImpl
import com.example.myphotoviwer.data.repository.datasource.DataSource
import com.example.myphotoviwer.data.repository.datasourceimpl.DataSourceImpl
import com.example.myphotoviwer.domain.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object MyModule {
//    @Provides
//    @Singleton
//    fun provideSettingsRepository(
//        sharedPreference: SharedPreferences,
//        bridgeDatabase: BridgeDatabase
//    ): SettingsRepository {
//        return SettingsRepositoryImpl(sharedPreference, bridgeDatabase)
//    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(): DataSource {
        return DataSourceImpl()
    }

    @Provides
    @Singleton
    fun provideApiRepository(dataSource: DataSource): Repository {
        return RepositoryImpl(dataSource)
    }
}