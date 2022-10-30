package ru.artelsv.exchangeapp

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.artelsv.exchangeapp.ui.viewmodel.Sort

@ExperimentalMaterial3Api
@Composable
fun ExchangeTopBar(
    @StringRes titleRes: Int = R.string.main_title,
    sorts: List<Sort> = listOf(Sort.Asc, Sort.Desc, Sort.AscValue, Sort.DescValue),
    onFilterChange: (Sort) -> Unit
) {

    var showMenu by remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text(text = stringResource(id = titleRes)) },
        actions = {
            Spacer(modifier = Modifier.width(8.dp))
            IconButton({ showMenu = true }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_sort_24),
                    contentDescription = null,
                )
            }

            DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                sorts.forEach {
                    DropdownMenuItem(text = { Text(text = stringResource(id = it.textRes)) }, onClick = {
                        onFilterChange(it)
                        showMenu = false
                    })
                }
            }
        }
    )
}