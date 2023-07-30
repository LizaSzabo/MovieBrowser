package com.mbh.moviebrowser.features.movieDetails

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.mbh.moviebrowser.R
import com.mbh.moviebrowser.domain.model.Movie

@Composable
fun MovieDetailsScreen(viewModel: MovieDetailsViewModel = hiltViewModel(), movieId: Long) {

    val uiState: MovieDetailsUIState by viewModel.uiState.collectAsState(MovieDetailsUIState.Loading)

    when (uiState) {
        is MovieDetailsUIState.Loading -> {
            viewModel.getDetails(movieId)
            MovieDetailsScreenUILoading()
        }
        is MovieDetailsUIState.MovieReady -> {

            MovieDetailsScreenUI(
                (uiState as MovieDetailsUIState.MovieReady).movie,
                viewModel::onFavoriteClicked,
            )
        }
    }
}

@Composable
fun MovieDetailsScreenUILoading() {
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
fun MovieDetailsScreenUI(
    movie: Movie?,
    onFavoriteClicked: (Boolean, Movie) -> Unit,
) {
    if (movie == null) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.large_padding)))
        AsyncImage(
            model = movie.coverUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.large_padding)))
        val image = if (movie.isFavorite) {
            painterResource(id = android.R.drawable.btn_star_big_on)
        } else {
            painterResource(id = android.R.drawable.btn_star_big_off)
        }
        Image(
            painter = image,
            contentDescription = null,
            modifier = Modifier
                .clickable {
                    onFavoriteClicked(!movie.isFavorite, movie)
                }
                .size(dimensionResource(id = R.dimen.medium_icon_size)),
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.medium_padding)))
        Text(
            text = movie.title,
            style = MaterialTheme.typography.headlineMedium,
            color = colorResource(id = R.color.blue_dark),
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.medium_padding)),
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.medium_padding)))
        Text(
            text = movie.overview ?: "",
            style = MaterialTheme.typography.bodyLarge,
            color = colorResource(id = R.color.blue_dark),
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.large_padding)))
    }
}

@Composable
@Preview(
    name = "phone",
    device = "spec:shape=Normal,width=360,height=640,unit=dp,dpi=480",
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
fun MovieDetailsScreenUIPreview() {
    MovieDetailsScreenUI(
        movie = Movie(
            id = 123L,
            title = "Example Movie",
            genres = "Action, Adventure, Sci-Fi",
            overview = "This is an overview of the example movie. It's full of action, adventure and sci-fi elements.",
            coverUrl = "https://image.tmdb.org/t/p/w300/qW4crfED8mpNDadSmMdi7ZDzhXF.jpg",
            rating = 4.5f,
            isFavorite = false,
        ),
        onFavoriteClicked = ::onFavoriteClicked
    )
}

private fun onFavoriteClicked(isFavorite: Boolean, movie: Movie) {}
