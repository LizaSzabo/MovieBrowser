package com.mbh.moviebrowser.data.database.di

import android.content.Context
import androidx.room.Room
import com.mbh.moviebrowser.data.database.MovieDatabase
import com.mbh.moviebrowser.data.database.dao.GenreDao
import com.mbh.moviebrowser.data.database.dao.MovieDao
import com.mbh.moviebrowser.data.database.source.GenreDataSource
import com.mbh.moviebrowser.data.database.source.MovieDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MovieDatabaseModule {

    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(context, MovieDatabase::class.java, "moviesDatabase")
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieDao(movieDatabase: MovieDatabase): MovieDao = movieDatabase.movieDao()

    @Provides
    @Singleton
    fun provideGenreDao(movieDatabase: MovieDatabase): GenreDao = movieDatabase.genreDao()

    @Provides
    @Singleton
    fun provideMovieDataSource(movieDao: MovieDao): MovieDataSource =
        MovieDataSource(movieDao = movieDao)

    @Provides
    @Singleton
    fun provideGenreDataSource(genreDao: GenreDao): GenreDataSource =
        GenreDataSource(genreDao = genreDao)
}