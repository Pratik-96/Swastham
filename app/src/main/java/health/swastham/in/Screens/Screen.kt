package health.swastham.`in`.Screens

sealed class Screen(val route:String) {
    object HomeScreen: Screen("Swastham")
    object HistoryScreen: Screen("History")
}
