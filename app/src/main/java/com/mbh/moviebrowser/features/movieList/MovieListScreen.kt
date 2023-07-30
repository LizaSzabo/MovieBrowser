package com.mbh.moviebrowser.features.movieList

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.mbh.moviebrowser.domain.model.Movie
import com.mbh.moviebrowser.features.movieList.MovieListUIState.Loading
import com.mbh.moviebrowser.features.movieList.MovieListUIState.Error
import com.mbh.moviebrowser.features.movieList.MovieListUIState.MovieListReady
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mbh.moviebrowser.MovieBrowserApplication.Companion.appContext
import com.mbh.moviebrowser.R
import com.mbh.moviebrowser.features.widgets.SearchView

@Composable
fun MovieListScreen(navController: NavController, viewModel: MovieListViewModel = hiltViewModel()) {
    val uiState: MovieListUIState by viewModel.uiState.collectAsState(Loading)

    when (uiState) {
        is Loading -> {
            viewModel.getData()
            MovieListScreenUILoading()
        }
        is Error -> {
            MovieListScreenUIError((uiState as Error).errorMessage)
        }
        is MovieListReady -> {
            MovieListScreenUI(navController, (uiState as MovieListReady).movies, viewModel::getMoreMovies)
        }
    }
}

@Composable
fun MovieListScreenUILoading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(dimensionResource(id = R.dimen.large_icon_size)),
            color = colorResource(id = R.color.blue)
        )
    }
}

@Composable
fun MovieListScreenUI(navController: NavController, movies: List<Movie>, getMoreMovies: () -> Unit) {
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    val filteredMovies = movies.filter { movie ->
        movie.title.uppercase().contains(textState.value.text.uppercase())
    }

    Column {
        SearchView(state = textState)
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(filteredMovies) { item ->
                MovieListItem(
                    movie = item,
                    onDetailsClicked = { selectedMovie ->
                        navController.navigate("details/${selectedMovie.id}")
                    }
                )
            }
            item {
                LaunchedEffect(true) {
                    getMoreMovies()
                }
            }
        }
    }
}

@Composable
private fun MovieListItem(
    movie: Movie,
    onDetailsClicked: (Movie) -> Unit,
) {
    Row(
        Modifier
            .padding(horizontal = dimensionResource(id = R.dimen.medium_padding), vertical = dimensionResource(id = R.dimen.small_padding))
            .clickable {
                onDetailsClicked(movie)
            }
            .background(color = colorResource(id = R.color.blue_background)),
    ) {
        Box(modifier = Modifier.padding(dimensionResource(id = R.dimen.small_padding))) {
            AsyncImage(
                model = movie.coverUrl,
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .height(140.dp)
                    .zIndex(1.0f),
            )
            val image = if (movie.isFavorite) {
                painterResource(id = android.R.drawable.btn_star_big_on)
            } else {
                painterResource(id = android.R.drawable.btn_star_big_off)
            }
            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier
                    .padding(all = dimensionResource(id = R.dimen.small_padding))
                    .zIndex(2.0f)
                    .align(Alignment.TopEnd),
            )
        }
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.large_padding)))
        Column(modifier = Modifier.heightIn(min = 140.dp), verticalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = colorResource(id = R.color.blue_dark),
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.small_padding)))
                Text(
                    text = movie.genres,
                    style = MaterialTheme.typography.bodyLarge,
                    color = colorResource(id = R.color.blue_dark),
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.medium_padding)))
            }
            Column {
                LinearProgressIndicator(
                    progress = movie.rating / 10.0f,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensionResource(id = R.dimen.medium_padding))
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.medium_padding)))
            }
        }
    }
}

@Composable
fun MovieListScreenUIError(errorMessage: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.baseline_search_off_24),
                contentDescription = null,
                Modifier.size(dimensionResource(id = R.dimen.large_icon_size)),
            )
            Text(
                text = errorMessage,
                fontSize = 26.sp,
                color = colorResource(id = R.color.blue),
                modifier = Modifier.padding(dimensionResource(id = R.dimen.small_padding))
            )
        }
    }
}

@Composable
@Preview(
    name = "phone",
    device = "spec:shape=Normal,width=360,height=640,unit=dp,dpi=480",
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
fun MovieListScreenUIPreview() {
    MovieListScreenUI(
        NavController(appContext),
        listOf(
            Movie(
                id = 455476,
                title = "Knights of the Zodiac",
                genres = "Action, Sci-fi",
                overview = "When a headstrong street orphan, Seiya, in search of his abducted sister unwittingly taps into hidden powers, he discovers he might be the only person alive who can protect a reincarnated goddess, sent to watch over humanity. Can he let his past go and embrace his destiny to become a Knight of the Zodiac?",
                coverUrl = "https://image.tmdb.org/t/p/w500/qW4crfED8mpNDadSmMdi7ZDzhXF.jpg",
                rating = 6.5f,
                isFavorite = true,
            ),
            Movie(
                id = 385687,
                title = "Fast X",
                genres = "Action",
                overview = "Over many missions and against impossible odds, Dom Toretto and his family have outsmarted, out-nerved and outdriven every foe in their path. Now, they confront the most lethal opponent they've ever faced: A terrifying threat emerging from the shadows of the past who's fueled by blood revenge, and who is determined to shatter this family and destroy everything—and everyone—that Dom loves, forever.",
                coverUrl = "https://image.tmdb.org/t/p/w500/fiVW06jE7z9YnO4trhaMEdclSiC.jpg",
                rating = 7.4f,
                isFavorite = false,
            ),
        ),
        getMoreMovies = {}
    )
}
