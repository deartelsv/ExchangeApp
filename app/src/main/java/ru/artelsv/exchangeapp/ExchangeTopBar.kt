package ru.artelsv.exchangeapp

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.artelsv.exchangeapp.ui.viewmodel.Sort

@ExperimentalMaterial3Api
@Composable
fun ExchangeTopBar(
    onFilterChange: (Sort) -> Unit
) {

    var showMenu by remember { mutableStateOf(false) }

    TopAppBar(
//        modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp),
        title = { Text(text = "Обмен валют") },
        actions = {
            Spacer(modifier = Modifier.width(8.dp))
            IconButton({ showMenu = true }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_sort_24),
                    contentDescription = null,
                )
            }

            DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                DropdownMenuItem(text = { Text(text = "Asc") }, onClick = {
                    onFilterChange(Sort.Asc)
                    showMenu = false
                })
                DropdownMenuItem(text = { Text(text = "Desc") }, onClick = {
                    onFilterChange(Sort.Desc)
                    showMenu = false
                })
                DropdownMenuItem(text = { Text(text = "AscValue") }, onClick = {
                    onFilterChange(Sort.AscValue)
                    showMenu = false
                })
                DropdownMenuItem(text = { Text(text = "DescValue") }, onClick = {
                    onFilterChange(Sort.DescValue)
                    showMenu = false
                })
            }
        }
    )
}