package ru.artelsv.exchangeapp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.artelsv.exchangeapp.ExchangeTopBar
import ru.artelsv.exchangeapp.R
import ru.artelsv.exchangeapp.domain.BaseCurrency
import ru.artelsv.exchangeapp.ui.viewmodel.FavouriteScreenState
import ru.artelsv.exchangeapp.ui.viewmodel.FavouriteViewModel
import ru.artelsv.exchangeapp.ui.viewmodel.Sort

@ExperimentalMaterial3Api
@Composable
fun FavouriteScreen(
    viewModel: FavouriteViewModel = hiltViewModel()
) {
    Column {
        when (val state = viewModel.state.collectAsState().value) {
            is FavouriteScreenState.DataLoaded -> {
                ExchangeTopBar(R.string.favourite_title, listOf(Sort.Asc, Sort.Desc)) {
                    viewModel.sort(it)
                }

                ListFavouriteContent(data = state.values, onLikeClick = {
                    viewModel.like(it)
                })
            }
            is FavouriteScreenState.Error -> ErrorContent()
            is FavouriteScreenState.Loading -> LoadingContent()
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun ListFavouriteContent(
    data: List<BaseCurrency.Currency>,
    onLikeClick: (BaseCurrency.Currency) -> Unit
) {
    LazyColumn(
        Modifier.fillMaxWidth().padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(data) { item ->
            Card(
                modifier = Modifier
                    .padding(PaddingValues(horizontal = 16.dp))
                    .fillMaxWidth(),
                onClick = {  }
            ) {

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = item.name,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(start = 16.dp)
                    )
                    IconButton(onClick = {
                        onLikeClick(item.copy(favourite = !item.favourite))
                    }) {
                        Icon(
                            painter = painterResource(
                                id = R.drawable.ic_baseline_favorite_24
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