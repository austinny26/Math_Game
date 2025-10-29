package com.example.mathgame

import android.os.Bundle
import android.widget.Button
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.nio.file.WatchEvent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { MainLayout() }
    }
}

@Composable
@Preview
fun MainLayout() {

    var isPlaying by remember { mutableStateOf(false) }
    var backColor by remember { mutableStateOf(Color.Yellow) }
    Surface(
        color = backColor,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize(),
            ) {
            if(isPlaying){
                PlayScreen()
            }else {
                StartScreen(onStartClicked = {isPlaying = true})
            }
        }

    }
}


@Composable
fun PlayScreen(){
    Surface(
        color = Color.Red
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            )
        {

        }
    }

}
@Composable
fun StartScreen(onStartClicked: () -> Unit) {

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                onStartClicked
            }
        ) {
            Text(
                text = "Start",
                fontSize = 24.sp
            )
        }

        Spacer(modifier = Modifier.size(32.dp))

        Text(text = "Tap start to begin!", fontSize = 32.sp)

        Spacer(modifier = Modifier.size(32.dp))

            Surface (
                modifier = Modifier
                    .size(width = 300.dp, height = 200.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(BorderStroke(4.dp, Color.Red)),

            ){
                Image(
                    painter = painterResource(R.drawable.launchimage),
                    contentDescription = "start",
                    modifier = Modifier
                        .fillMaxSize()


                )
            }

    }
}