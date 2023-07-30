package com.mbh.moviebrowser.data.network.di

import com.mbh.moviebrowser.data.network.api.MovieManagerApi
import com.mbh.moviebrowser.data.network.source.MovieNetworkDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MovieNetworkModule {

    @Singleton
    @Provides
    fun provideCocktailNetworkDataSource(movieManagerApi: MovieManagerApi): MovieNetworkDataSource =
        MovieNetworkDataSource(movieManagerApi)
}