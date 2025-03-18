package health.swastham

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import health.swastham.MainViewModel
import health.swastham.`in`.MainLayout
import health.swastham.`in`.Screens.Screen
import health.swastham.`in`.poppinsFont
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier) {

    val viewModel:MainViewModel = viewModel()
    LaunchedEffect(Unit) {
        while(true){
            viewModel.fetchData()
            delay(5000)
        }
    }
    var isRefreshing by remember { mutableStateOf(false) }
    val refreshState = rememberPullToRefreshState()
    val coroutineScope = rememberCoroutineScope()

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = {
            coroutineScope.launch {
                isRefreshing=true
                viewModel.fetchData()
                delay(3000L)
                isRefreshing=false
            }

        },
        modifier = Modifier.fillMaxSize(),
        state = refreshState
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when {
                viewModel.healthData.value.loading -> {
                    CircularProgressIndicator(
                        Modifier.align(Alignment.CenterHorizontally),
                        color = Color.Black

                    )
                }

                viewModel.healthData.value.error != null -> {
                    Text(
                        text = viewModel.healthData.value.error.toString(),
                        fontWeight = FontWeight.Bold,
                        fontFamily = poppinsFont,
                        color = Color.Red,
                        modifier = Modifier.padding(8.dp),
                        fontSize = 18.sp,
                    )
                }


                viewModel.healthData.value.data != null -> {
                    MainLayout(
                        modifier = Modifier,
                        state = viewModel.healthData.value
                    )
                }


            }
        }
    }


}