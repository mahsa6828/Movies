package com.example.myapplication.di

import android.content.Context
import androidx.room.Room
import com.example.myapplication.db.MovieEntity
import com.example.myapplication.db.MoviesDataBase
import com.example.myapplication.utils.MOVIES_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,MoviesDataBase::class.java, MOVIES_DATABASE)
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideDao(db:MoviesDataBase) = db.movieDao()

    @Provides
    @Singleton
    fun provideEntity() = MovieEntity()
}