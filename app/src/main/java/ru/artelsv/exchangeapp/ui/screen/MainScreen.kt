package ru.artelsv.exchangeapp.ui.screen

import android.util.Log
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
import ru.artelsv.exchangeapp.ExchangeTopBar
import ru.artelsv.exchangeapp.R
import ru.artelsv.exchangeapp.domain.BaseCurrency
import ru.artelsv.exchangeapp.ui.viewmodel.MainScreenState
import ru.artelsv.exchangeapp.ui.viewmodel.MainViewModel

/**
 * Я до конца пока непонимаю компоуз (https://developer.android.com/jetpack/compose/state#viewmodels-source-of-truth)
 * нельзя поидеи спускать вьюМодель ниже по компоуз функциям)) Так шо ниже написанное легально
 */
@ExperimentalMaterial3Api
@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    Column {

        when (state) {
            is MainScreenState.DataLoaded -> {
                ExchangeTopBar {
                    Log.e("a", it.toString())
                    viewModel.sort(it)
                }

                ListContent(data = state.values, state.selected, {
                    viewModel.setCurrency(it)
                }, {
                    viewModel.like(it)
                })
            }
            is MainScreenState.Error -> ErrorContent()
            is MainScreenState.Loading -> LoadingContent()
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun ListContent(
    data: List<BaseCurrency.Currency>,
    selected: BaseCurrency,
    onItemClick: (BaseCurrency.Currency) -> Unit,
    onLikeClick: (BaseCurrency.Currency) -> Unit
) {
    LazyColumn(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Card(
                    modifier = Modifier
                        .padding(PaddingValues(horizontal = 64.dp))
                        .fillMaxWidth(),
                    onClick = { }
                ) {
                    Text(
                        text = if (selected is BaseCurrency.Currency) selected.name else "?",
                        modifier = Modifier
                            .padding(horizontal = 32.dp, vertical = 16.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
        items(data) { item ->
            Card(
                modifier = Modifier
                    .padding(PaddingValues(horizontal = 16.dp))
                    .fillMaxWidth(),
                onClick = { onItemClick(item) }
            ) {

                var like by remember { mutableStateOf(item.favourite) }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${item.name} ${if (item.value == 0.0) "" else item.value}",
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

@Composable
fun ErrorContent() {
    Surface(modifier = Modifier.fillMaxSize()) {

        Text(text = "Error")
    }
}

@Composable
fun LoadingContent() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}