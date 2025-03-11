package com.example.retrofitapp.view.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarView(
    searchQuery: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit
) {
    SearchBar(
        query = searchQuery,
        onQueryChange = { onQueryChange(it) },
        onSearch = { onSearch() },
        active = false,
        onActiveChange = {},
        leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = "Buscar") },
        placeholder = { Text("Buscar ciudad...") },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {}
}
