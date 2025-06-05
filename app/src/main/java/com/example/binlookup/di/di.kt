package com.example.binlookup.di

import android.content.Context
import androidx.room.Room
import com.example.binlookup.data.local.dao.BinHistoryDao
import com.example.binlookup.data.local.database.BinLookupDatabase
import com.example.binlookup.data.remote.api.BinLookupApi
import com.example.binlookup.data.repository.BinLookupRepositoryImpl
import com.example.binlookup.domain.repository.BinLookupRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@Suppress("unused")
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideBinLookupApi(client: OkHttpClient): BinLookupApi {
        return Retrofit.Builder()
            .baseUrl("https://lookup.binlist.net/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BinLookupApi::class.java)
    }

    @Provides
    @Singleton
    fun provideBinLookupDatabase(@ApplicationContext context: Context): BinLookupDatabase {
        return Room.databaseBuilder(
            context,
            BinLookupDatabase::class.java,
            BinLookupDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideBinHistoryDao(database: BinLookupDatabase): BinHistoryDao {
        return database.binHistoryDao()
    }

    @Provides
    @Singleton
    fun provideBinLookupRepository(
        api: BinLookupApi,
        dao: BinHistoryDao
    ): BinLookupRepository {
        return BinLookupRepositoryImpl(api, dao)
    }
}