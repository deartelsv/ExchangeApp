package ru.artelsv.exchangeapp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.artelsv.exchangeapp.R
import ru.artelsv.exchangeapp.domain.BaseCurrency
import ru.artelsv.exchangeapp.ui.viewmodel.FavouriteScreenState
import ru.artelsv.exchangeapp.ui.viewmodel.FavouriteViewModel

@ExperimentalMaterial3Api
@Composable
fun FavouriteScreen(
    viewModel: FavouriteViewModel = hiltViewModel()
) {
    when(val state = viewModel.state.collectAsState().value) {
        is FavouriteScreenState.DataLoaded -> {
            ListFavouriteContent(data = state.values, onLikeClick = {
                viewModel.like(it)
            })
        }
        is FavouriteScreenState.Error -> ErrorContent()
        is FavouriteScreenState.Loading -> LoadingContent()
    }
}

@ExperimentalMaterial3Api
@Composable
fun ListFavouriteContent(
    data: List<BaseCurrency.Currency>,
    onLikeClick: (BaseCurrency.Currency) -> Unit
) {

    Spacer(modifier = Modifier.height(8.dp))

    LazyColumn(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(data) { item ->
            Card(
                modifier = Modifier
                    .padding(PaddingValues(horizontal = 16.dp))
                    .fillMaxWidth(),
                onClick = {  }
            ) {

                var like by remember { mutableStateOf(item.favourite) }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "",
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(start = 16.dp)
                    )
                    IconButton(onClick = {
                        like = !item.favourite
                        onLikeClick(item.copy(favourite = like))
                    }) {
                        Icon(
                            painter = painterResource(
                                id = if (like) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24
                            ),
                            contentDescription = null
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}