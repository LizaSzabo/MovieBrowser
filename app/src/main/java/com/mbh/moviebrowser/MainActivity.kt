package com.mbh.moviebrowser

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mbh.moviebrowser.features.movieDetails.MovieDetailsScreen
import com.mbh.moviebrowser.features.movieDetails.MovieDetailsViewModel
import com.mbh.moviebrowser.features.movieList.MovieListScreen
import com.mbh.moviebrowser.features.movieList.MovieListViewModel
import com.mbh.moviebrowser.store.MovieStore
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val movieStore = MovieStore()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "list") {
                    composable("list") {
                        MovieListScreen(navController)
                    }
                    composable(
                        "details/{movieId}",
                        arguments = listOf(navArgument("movieId") { type = NavType.LongType })
                    ) { backStackEntry ->
                        backStackEntry.arguments?.getLong("movieId")?.let { movieId ->
                            MovieDetailsScreen(movieId = movieId)
                        }
                    }
                }
            }
        }
    }
}

