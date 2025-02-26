data class WeatherResponse(
    val name: String, // Ciudad
    val main: Main,
    val weather: List<Weather>
)

data class Main(
    val temp: Double, // Temperatura
    val humidity: Int // ✅ Agregar humedad
)

data class Weather(
    val description: String // Descripción del clima
)
