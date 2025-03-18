package health.swastham

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import health.swastham.`in`.poppinsFont
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

//@Preview(showBackground = true)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HistoryScreen(modifier: Modifier) {
    val viewModel: MainViewModel = viewModel()
    val state = viewModel.healthData.value


    Column(modifier = modifier.fillMaxSize().background(Color.White)) {
        when {
            state.loading -> {
                CircularProgressIndicator(
                    Modifier.align(Alignment.CenterHorizontally),
                    color = Color.Black

                )
            }


            else -> {
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxSize()
                        , columns = GridCells.Fixed(1)
                ) {
                    items(
                        items = state.data?.reversed()!!
                    ){item->

                        HistoryCard(modifier,item)

                    }
                }

            }
        }

    }


}

@Composable
fun NormalText(text: String, modifier: Modifier) {
    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        fontFamily = poppinsFont,
        color = Color.Black,
        modifier = Modifier.padding(8.dp),
        fontSize = 16.sp,
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HistoryCard(modifier: Modifier,item:HealthData) {

    val instant = Instant.parse(item.timestamp)

    // Convert to IST (UTC+5:30)
    val istZone = ZoneId.of("Asia/Kolkata")
    val istTime = instant.atZone(istZone)

    // Format Date and Time in dd-MM-yyyy HH:mm:ss format
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss", java.util.Locale.ENGLISH)
    val formattedDateTime = istTime.format(formatter)
    Column(
        modifier = Modifier
            .wrapContentSize()
            .fillMaxWidth()
            .padding(16.dp)
            .shadow(elevation = 8.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        NormalText("Heart Rate: "+item.heartRate, modifier.padding(16.dp))
        NormalText("Spo2: "+item.spo2+" %", modifier.padding(16.dp))
        NormalText("Body Temp. : "+item.bodyTemp+"°F", modifier.padding(16.dp))
        NormalText("Date: "+formattedDateTime, modifier.padding(16.dp))


    }
}