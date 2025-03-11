package com.example.retrofitapp.view.home

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.retrofitapp.model.WeatherEntity
import com.example.retrofitapp.model.getWindowSizeClass
import com.example.retrofitapp.model.WindowSizeClassSearchh
import com.example.retrofitapp.view.search.SearchBarView
import com.example.retrofitapp.viewmodel.WeatherViewModel


@Composable
fun HomeScreen(navController: NavController, viewModel: WeatherViewModel = viewModel()) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val windowSizeClass = getWindowSizeClass(context)

    val weatherList by viewModel.popularCitiesWeather.observeAsState(emptyList())  // 🔹 Observa los datos del ViewModel
    val isLoading by viewModel.isLoading.observeAsState(false)

    var searchQuery by remember { mutableStateOf("") }
    val searchHistory = remember { mutableStateListOf<String>() }

    // 🔹 Filtrar la lista de ciudades según la búsqueda
    val filteredWeatherList = weatherList.filter { it.city.contains(searchQuery, ignoreCase = true) }

    // 🔹 Carga inicial de ciudades populares
    LaunchedEffect(Unit) {
        viewModel.fetchPopularCitiesWeather()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colors = listOf(Color(0xFF0D47A1), Color(0xFF42A5F5)))),
        contentAlignment = Alignment.Center
    ) {
        if (isLandscape || windowSizeClass == WindowSizeClassSearchh.EXPANDED) {
            // 🖥️ **Diseño para pantallas grandes**
            Row(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    HomeTitle()
                    SearchBarView(
                        searchQuery = searchQuery,
                        onQueryChange = { searchQuery = it },
                        onSearch = {
                            if (searchQuery.isNotBlank() && !searchHistory.contains(searchQuery)) {
                                searchHistory.add(0, searchQuery) // ✅ Guarda en historial
                            }
                            viewModel.fetchWeather(searchQuery)
                        }
                    )
                    SearchHistoryList(searchHistory) { searchQuery = it }
                }
                Spacer(modifier = Modifier.width(20.dp))
                WeatherGrid(isLoading, filteredWeatherList, navController, windowSizeClass) // ✅ Usa la lista filtrada
            }
        } else {
            // 📱 **Diseño para móviles**
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HomeTitle()
                SearchBarView(
                    searchQuery = searchQuery,
                    onQueryChange = { searchQuery = it },
                    onSearch = {
                        if (searchQuery.isNotBlank() && !searchHistory.contains(searchQuery)) {
                            searchHistory.add(0, searchQuery) // ✅ Guarda en historial
                        }
                        viewModel.fetchWeather(searchQuery)
                    }
                )
                SearchHistoryList(searchHistory) { searchQuery = it }
                WeatherGrid(isLoading, filteredWeatherList, navController, windowSizeClass) // ✅ Usa la lista filtrada
            }
        }
    }
}


// 🔹 **Título principal**
@Composable
fun HomeTitle() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
    ) {
        Icon(
            imageVector = Icons.Default.LocationCity,
            contentDescription = "Ciudades",
            tint = Color.White,
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Ciudades Populares",
            fontSize = 26.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

// 🔹 **Lista de historial de búsqueda**
@Composable
fun SearchHistoryList(searchHistory: List<String>, onSearchSelected: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(searchHistory) { pastSearch ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable { onSearchSelected(pastSearch) },
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
            ) {
                Text(
                    text = pastSearch,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
// 🔹 **Grid con la lista de climas**
@Composable
fun WeatherGrid(
    isLoading: Boolean,
    weatherList: List<WeatherEntity>,
    navController: NavController,
    windowSizeClass: WindowSizeClassSearchh
) {
    if (isLoading) {
        CircularProgressIndicator(color = Color.White)
    } else if (weatherList.isEmpty()) {
        // ✅ Mostrar mensaje si no hay resultados tras filtrar
        Text(
            text = "No se encontraron resultados",
            fontSize = 18.sp,
            color = Color.White,
            modifier = Modifier.padding(16.dp)
        )
    } else {
        val columns = when (windowSizeClass) {
            WindowSizeClassSearchh.EXPANDED -> 3
            WindowSizeClassSearchh.MEDIUM -> 2
            else -> 1
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(columns),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(weatherList) { weather ->  // ✅ Se usa `filteredWeatherList`
                HomeWeatherCard(weather, navController)
            }
        }
    }
}


// 🔹 **Tarjeta de Clima**
@Composable
fun HomeWeatherCard(weather: WeatherEntity, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { navController.navigate("details/${weather.city}") },
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = weather.city,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1565C0),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${weather.temperature}°C",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Humedad: ${weather.humidity}%",
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
