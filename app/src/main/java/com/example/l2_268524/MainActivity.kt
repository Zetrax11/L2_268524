package com.example.l2_268524

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.l2_268524.ui.theme.L2_268524Theme
import com.example.l2_268524.ui.theme.Teal200
import com.example.l2_268524.ui.theme.greenColor

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            L2_268524Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(backgroundColor = Teal200,
                            title = {
                                Text(
                                    text = "Czujnik odległości",
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    color = Color.White
                                )
                            })
                        }
                    )
                    {
                        ProximitySensor()
                    }
                }
            }
        }
    }
}

@Composable
fun ProximitySensor() {
    val ctx = LocalContext.current

    val sensorManager: SensorManager = ctx.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val proximitySensor: Sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)

    val sensorStatus = remember {
        mutableStateOf("---")
    }
    val sensorColor = remember {
        mutableStateOf(Color.Black)
    }

    val proximitySensorEventListener = object :SensorEventListener {
        override fun onAccuracyChanged(sensor : Sensor, accuracy: Int) {
        }

        override fun onSensorChanged(event: SensorEvent){
            if(event.sensor.type == Sensor.TYPE_PROXIMITY){
                if(event.values[0] <= 1){
                    sensorStatus.value = "Blisko"
                    sensorColor.value = Color.Green
                } else {
                    sensorStatus.value = "Daleko"
                    sensorColor.value = Color.Red
                }
            }
        }
    }
    sensorManager.registerListener(
        proximitySensorEventListener,
        proximitySensor,
        SensorManager.SENSOR_DELAY_NORMAL
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){

        Text(
            text = "Obiekt jest",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Default,
            fontSize = 40.sp, modifier = Modifier.padding(5.dp)
        )

        Text(
            text = sensorStatus.value,
            color = sensorColor.value,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Default,
            fontSize = 40.sp, modifier = Modifier.padding(5.dp)
        )
        Text(
            text = "Czujnika",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Default,
            fontSize = 40.sp, modifier = Modifier.padding(5.dp)
        )
    }
}