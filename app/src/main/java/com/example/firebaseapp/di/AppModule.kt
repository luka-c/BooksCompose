package com.example.firebaseapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import com.example.firebaseapp.data.BooksDao
import com.example.firebaseapp.data.BooksDatabase
import com.example.firebaseapp.network.BookApi
import com.example.firebaseapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideBookApi(): BookApi {
        return Retrofit.Builder()
            .baseUrl(Constants.API_ROOT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BookApi::class.java)
    }

    @Singleton
    @Provides
    fun provideBooksDao(booksDatabase: BooksDatabase): BooksDao =
        booksDatabase.booksDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): BooksDatabase
        = databaseBuilder(
            context,
            BooksDatabase::class.java,
            "books_database"
        )
        .fallbackToDestructiveMigrationFrom()
        .build()

    @Singleton
    @Provides
    @Named(Constants.THEME_DATA_STORE)
    fun provideThemeDataStore(@ApplicationContext context: Context) : DataStore<Preferences> =
        PreferenceDataStoreFactory.create {
            context.preferencesDataStoreFile(Constants.THEME_DATA_STORE)
        }
}