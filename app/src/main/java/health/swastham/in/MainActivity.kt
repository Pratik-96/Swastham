package health.swastham.`in`

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsEndWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import health.swastham.HistoryScreen
import health.swastham.HomeScreen
import health.swastham.MainViewModel
import health.swastham.NormalText
import health.swastham.NotificationUtils
import health.swastham.`in`.Screens.Screen
import health.swastham.`in`.ui.theme.SwasthamTheme
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar


val poppinsFont = FontFamily(
    Font(R.font.poppins, FontWeight.Normal), // Regular
    // Bold Italic
)

var t = "Swastham"

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    override fun onBackPressed() {
        super.onBackPressed()
        t = "Swastham"
    }

    private fun requestNotificationPermission(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1)
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestNotificationPermission(this)
        enableEdgeToEdge()

        setContent {
            val viewModel: MainViewModel = viewModel()

            val context  = LocalContext.current
            LaunchedEffect(Unit) {
                NotificationUtils.createNotificationChannel(context)
            }



            var title by remember { mutableStateOf(t) }
            val navHostController = rememberNavController()
            val navBackStackEntry by navHostController.currentBackStackEntryAsState()
            title = navBackStackEntry?.destination?.route.toString()


            SwasthamTheme {
                val viewModel: MainViewModel = viewModel()

//                Log.d("Data", "onCreate: "+viewModel.getData())
                Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                    TopAppBar(
                        actions = {
                            if (title == "Swastham") {
                                IconButton(onClick = {

                                    navHostController.navigate(Screen.HistoryScreen.route)
                                }) {
                                    Image(
                                        painter = painterResource(R.drawable.baseline_history_24),  // You can change the icon
                                        contentDescription = "End",
//                                    tint = Color.Black
                                    )
                                }
                            }
                        },
                        // Text Color change
                        title = {
                            Text(
                                text = title,
                                color = Color.Black,
                                fontFamily = poppinsFont,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(8.dp)
                            )
                        },
                        navigationIcon = {
                            if (title == "Swastham") {
                                Image(
                                    painter = painterResource(R.drawable.logo),
                                    modifier = Modifier.size(50.dp),
                                    contentDescription = null
                                )
                            } else {
                                IconButton(onClick = {
//                                    title = "Swastham"
//                                    t = "Swastham"

                                    navHostController.navigateUp()

                                }) {
                                    Image(
                                        imageVector = Icons.Default.ArrowBack,  // You can change the icon
                                        contentDescription = "End",
//                                        tint = Color.Black
                                    )
                                }
                            }
                        },
                        colors = TopAppBarColors(
                            containerColor = Color.White,
                            scrolledContainerColor = Color.White,
                            navigationIconContentColor = Color.Black,
                            titleContentColor = Color.Black,
                            actionIconContentColor = Color.Black
                        ),

                        )
                }) { innerPadding ->
                    NavHost(
                        navController = navHostController,
                        startDestination = Screen.HomeScreen.route
                    ) {
                        composable(route = Screen.HomeScreen.route) {
//                            title="Swastham"
                            HomeScreen(Modifier.padding(innerPadding))
                        }
                        composable(route = Screen.HistoryScreen.route) {
//                    MainLayout(Modifier,viewModel.healthData.value)
//                            title="Swastham"
                            HistoryScreen(Modifier.padding(innerPadding))
                        }
                    }
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainLayout(modifier: Modifier, state: MainViewModel.dataState) {
//    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.heart_rate))
//    val isPlaying by remember { mutableStateOf(true) }
//
//    val progress by animateLottieCompositionAsState(
//        composition,
//        isPlaying = isPlaying,
//        iterations = LottieConstants.IterateForever
//    )

    val list = state.data
    val context = LocalContext.current


    val heartRate = list?.get(list.size.minus(1))?.heartRate.toString()
    val spo2 = list?.get(list.size.minus(1))?.spo2.toString()
    val body = list?.get(list.size.minus(1))?.bodyTemp.toString()

    if(Integer.parseInt(heartRate)>= 150){
        NotificationUtils.showNotification(context,"Alert","Heart Rate above 150!!")
    }
    if(Integer.parseInt(spo2)<= 95){
        NotificationUtils.showNotification(context,"Alert","Oxygen level below 95!!")
    }


    if(body.toDouble()>=100.0){
        NotificationUtils.showNotification(context,"Alert","Body Temperature above 100!!")
    }


    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.White)
    ) {
        val currentDateTime = LocalDateTime.now(ZoneId.of("Asia/Kolkata"))

        // Define formats
        val dateFormatter = DateTimeFormatter.ofPattern("MMMM dd")
        val dayFormatter = DateTimeFormatter.ofPattern("EEEE") // Full day name
        val timeFormatter = DateTimeFormatter.ofPattern("hh:mm a") // 12-hour format with AM/PM

        // Extract values
        val date = currentDateTime.format(dateFormatter)  // 12-03-2025
        val day = currentDateTime.format(dayFormatter)    // Wednesday
        val time = currentDateTime.format(timeFormatter)  // 07:30 PM

        val currentTime: Calendar = Calendar.getInstance()
        val hour: Int = currentTime.get(Calendar.HOUR_OF_DAY)
        var greet = ""
        Log.d("Time", "HomeScreen: " + hour)
        if (hour >= 1 && hour < 12) {
            greet = "Good morning"
        } else if (hour >= 12 && hour < 16) {
            greet = "Good afternoon"
        } else {
            greet = "Good evening"
        }
        Spacer(Modifier.padding(5.dp))
        val context = LocalContext.current

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {


            Column(
                modifier = Modifier
                    .wrapContentSize()
                .width(200.dp)
                    .height(200.dp)
                    .weight(1f)
                    .padding(8.dp)
                    .shadow(elevation = 8.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(8.dp)),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Today",
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = poppinsFont,
                    color = Color.Black,
                    modifier = modifier.padding(start = 8.dp),
                    fontSize = 28.sp,
                )
                NormalText(day + ", " + date, modifier)
                NormalText(time, modifier)
                NormalText(greet, modifier)

            }














            Column(
                modifier = Modifier
                    .wrapContentSize()

//                .width(200.dp)
                    .weight(1f)
                    .height(200.dp)
                    .padding(8.dp)
                    .shadow(elevation = 8.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(8.dp)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(16.dp)
                            .background(color = pink, shape = RoundedCornerShape(8.dp))
                    ) {
                        Image(
                            painter = painterResource(R.drawable.heart),
                            null,
                            modifier = Modifier
                                .size(58.dp)
                                .padding(8.dp)
                        )
                    }
                    Text(
                        "Heart Rate",
                        Modifier.padding(top = 16.dp, end = 16.dp, bottom = 16.dp),
                        fontWeight = FontWeight.Bold,
                        fontFamily = poppinsFont,
                        color = Color.Black,
                        fontSize = 16.sp,
                    )

                }

                Row(
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Center
                ) {



                    Text(
                        text = list?.get(list.size.minus(1))?.heartRate.toString(),
                        fontWeight = FontWeight.Bold,
                        fontFamily = poppinsFont,
                        color = darkPink,
                        fontSize = 48.sp,
                    )
                    Text(
                        "bpm",
                        fontFamily = poppinsFont,
                        color = darkPink,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
                    )
                }
            }


        }


        Spacer(Modifier.padding(5.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {


            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .width(200.dp)

                    .weight(1f)
                    .height(200.dp)
                    .padding(8.dp)
                    .shadow(elevation = 8.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(8.dp)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(16.dp)
                            .background(color = blue, shape = RoundedCornerShape(8.dp))
                    ) {
                        Image(
                            painter = painterResource(R.drawable.spo),
                            null,
                            modifier = Modifier
                                .size(58.dp)
                                .padding(8.dp)
                        )
                    }
                    Text(
                        "Spo2",
                        Modifier.padding(top = 16.dp, end = 16.dp, bottom = 16.dp),
                        fontWeight = FontWeight.Bold,
                        fontFamily = poppinsFont,
                        color = Color.Black,
                        fontSize = 16.sp,
                    )

                }

                Row(
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = list?.get(list.size.minus(1))?.spo2.toString(),
                        fontWeight = FontWeight.Bold,
                        fontFamily = poppinsFont,
                        color = darkBlue,
                        fontSize = 48.sp,
                    )
                    Text(
                        "%",
                        fontFamily = poppinsFont,
                        color = darkBlue,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
                    )
                }
            }




            Column(
                modifier = Modifier
                    .wrapContentSize()
//                .width(200.dp)
                    .weight(1f)
                    .height(200.dp)
                    .padding(8.dp)
                    .shadow(elevation = 8.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(8.dp)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(16.dp)
                            .background(color = yellow, shape = RoundedCornerShape(8.dp))
                    ) {
                        Image(
                            painter = painterResource(R.drawable.temp),
                            null,
                            modifier = Modifier
                                .size(58.dp)
                                .padding(8.dp)
                        )
                    }
                    Text(
                        "Body Temp.",
                        Modifier.padding(top = 16.dp, end = 16.dp, bottom = 16.dp),
                        fontWeight = FontWeight.Bold,
                        fontFamily = poppinsFont,
                        color = Color.Black,
                        fontSize = 16.sp,
                    )

                }

                Row(
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = list?.get(list.size.minus(1))?.bodyTemp.toString(),
                        fontWeight = FontWeight.Bold,
                        fontFamily = poppinsFont,
                        color = darkYellow,
                        fontSize = 48.sp,
                    )
                    Text(
                        "°F",
                        fontFamily = poppinsFont,
                        color = darkYellow,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
                    )
                }
            }


        }


//        Box(
//            modifier = Modifier
//                .size(300.dp)
//                .padding(16.dp)
//                .border(width = 1.dp, color = Color.Red, shape = RoundedCornerShape(16.dp))
//                .clip(RoundedCornerShape(16.dp))
//                .shadow(elevation = 20.dp)
//                .background(color = Color.White)
//                .align(Alignment.CenterHorizontally)
//        ) {
//            Column(modifier = Modifier.align(Alignment.Center)) {
//                Row(
//                    modifier = Modifier
//                        .wrapContentSize()
//                        .align(Alignment.CenterHorizontally),
//                    verticalAlignment = Alignment.Bottom,
//                    horizontalArrangement = Arrangement.Center
//                ) {
//
//                    Text(
//                        text = list?.get(list.size.minus(1) )?.heartRate.toString(),
//                        fontWeight = FontWeight.Bold,
//                        fontFamily = poppinsFont,
//                        color = Color.Red,
//                        fontSize = 48.sp,
//                    )
//                    Text(
//                        "bpm",
//                        fontFamily = poppinsFont,
//                        color = Color.Red,
//                        fontSize = 18.sp,
//                        modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
//                    )
//                }
//                LottieAnimation(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(100.dp)
//                        .align(Alignment.CenterHorizontally),
//                    composition = composition,
//                    progress = { progress })
//                Text(
//                    "Heart Rate",
//                    fontFamily = poppinsFont,
//                    color = Color.Red,
//                    fontSize = 28.sp,
//                    fontWeight = FontWeight.Bold,
//                    modifier = Modifier.align(Alignment.CenterHorizontally)
//                )
//
//
//            }
//
//        }
//
//        Row(modifier = Modifier.wrapContentSize()) {
//            Box(
//                modifier = Modifier
//                    .size(200.dp)
//                    .padding(16.dp)
//                    .clip(RoundedCornerShape(16.dp))
//                    .shadow(elevation = 20.dp)
//                    .border(width = 1.dp, color = Color.Blue, shape = RoundedCornerShape(16.dp))
//                    .background(color = Color.White)
//            ) {
//                Column(modifier = Modifier.align(Alignment.Center)) {
//                    Row(
//                        modifier = Modifier
//                            .wrapContentSize()
//                            .align(Alignment.CenterHorizontally),
//                        verticalAlignment = Alignment.Bottom,
//                        horizontalArrangement = Arrangement.Center
//                    ) {
//
//                        Text(
//                            text = list?.get(list.size.minus(1) )?.spo2.toString(),
//                            fontWeight = FontWeight.Bold,
//                            fontFamily = poppinsFont,
//                            color = Color.Blue,
//                            fontSize = 48.sp,
//                        )
//                        Text(
//                            "%",
//                            fontFamily = poppinsFont,
//                            color = Color.Blue,
//                            fontSize = 18.sp,
//                            modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
//                        )
//                    }
//
//                    Text(
//                        "SPO2",
//                        fontFamily = poppinsFont,
//                        color = Color.Blue,
//                        fontSize = 18.sp,
//                        fontWeight = FontWeight.Bold,
//                        modifier = Modifier
//                            .padding(8.dp)
//                            .align(Alignment.CenterHorizontally)
//                    )
//
//
//                }
//
//            }
//
//            Box(
//                modifier = Modifier
//                    .size(200.dp)
//                    .padding(16.dp)
//                    .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(16.dp))
//                    .clip(RoundedCornerShape(16.dp))
//                    .shadow(elevation = 20.dp)
//                    .background(color = Color.White)
//            ) {
//                Column(modifier = Modifier.align(Alignment.Center)) {
//                    Row(
//                        modifier = Modifier
//                            .wrapContentSize()
//                            .align(Alignment.CenterHorizontally),
//                        verticalAlignment = Alignment.Top,
//                        horizontalArrangement = Arrangement.Center
//                    ) {
//
//                        Text(
//                            text = list?.get(list.size.minus(1) )?.bodyTemp.toString(),
//                            fontWeight = FontWeight.Bold,
//                            fontFamily = poppinsFont,
//                            color = Color.Black,
//                            fontSize = 48.sp,
//                        )
//                        Text(
//                            "°F",
//                            fontFamily = poppinsFont,
//                            color = Color.Black,
//                            fontSize = 18.sp,
//                            modifier = Modifier.padding(top = 8.dp, start = 4.dp)
//                        )
//                    }
//
//                    Text(
//                        "Body Temp.",
//                        fontFamily = poppinsFont,
//                        color = Color.Black,
//                        fontSize = 18.sp,
//                        fontWeight = FontWeight.Bold,
//                        modifier = Modifier
//                            .padding(8.dp)
//                            .align(Alignment.CenterHorizontally)
//                    )
//
//
//                }
//
//            }
//        }

    }


}

val pink = Color(0xFFFBF0F3)
val darkPink = Color(0xFFDA8184)
val blue = Color(0xFFD0FBFF)
val darkBlue = Color(0xFF478F96)
val yellow = Color(0xFFF8DEBD)
val darkYellow = Color(0xFFE79B38)


@Preview
@Composable
fun Prev(modifier: Modifier = Modifier) {


}
