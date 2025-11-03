package com.example.mathgame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainLayout()
        }
    }
}

enum class Screen {
    Start,
    Play,
    GameOver
}

@Composable
@Preview
fun MainLayout() {

    var currentScreen by remember { mutableStateOf(Screen.Start) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Yellow
    ) {
        when (currentScreen) {
            Screen.Start -> StartScreen(
                onStartClicked = { currentScreen = Screen.Play }
            )

            Screen.Play -> PlayScreen(
                onGameOver = { currentScreen = Screen.GameOver },
                onGoBack = { currentScreen = Screen.Start }
            )

            Screen.GameOver -> GameOverScreen(
                onRestart = { currentScreen = Screen.Start }
            )
        }
    }
}

@Composable
fun PlayScreen(
    onGameOver: () -> Unit,
    onGoBack: () -> Unit
) {
    val ctx = LocalContext.current

    var backColor by remember { mutableStateOf(Color.Yellow) }
    var score by remember { mutableIntStateOf(0) }
    var strikes by remember { mutableIntStateOf(0) }

    var scoreColor by remember { mutableStateOf(Color.Black) }
    var strikeColor by remember { mutableStateOf(Color.Black) }

    var instruction by remember { mutableStateOf("Tap the Larger Number!") }
    var gameEnded by remember { mutableStateOf(false) }
    var won by remember { mutableStateOf(false) }

    var numA by remember { mutableIntStateOf(Random.nextInt(1, 100)) }
    var numB by remember { mutableIntStateOf(Random.nextInt(1, 100)) }

    fun newPair() {
        numA = Random.nextInt(1, 100)
        numB = Random.nextInt(1, 100)
    }

    fun endGame(didWin: Boolean) {
        gameEnded = true
        won = didWin
        backColor = Color.Yellow
        instruction = "Tap restart to play again!"
        if (didWin) {
            scoreColor = Color(0xFF2E7D32)
            strikeColor = Color.Black
            Toast.makeText(ctx, "Congrats, you won!", Toast.LENGTH_SHORT).show()
        } else {
            strikeColor = Color(0xFFB71C1C)
            scoreColor = Color.Black
            Toast.makeText(ctx, "Sorry, you lost", Toast.LENGTH_SHORT).show()
        }
    }

    fun handleTap(isCorrect: Boolean) {
        if (gameEnded) return
        if (isCorrect) {
            score += 1
            scoreColor = Color.Yellow
            strikeColor = Color.Black
            backColor = Color(0xFF66BB6A)
            if (score >= 10) {
                endGame(true)
                return
            }
        } else {
            strikes += 1
            strikeColor = Color.Yellow
            scoreColor = Color.Black
            backColor = Color(0xFFEF5350)
            if (strikes >= 3) {
                endGame(false)
                return
            }
        }
        newPair()
    }

    fun restart() {
        score = 0
        strikes = 0
        scoreColor = Color.Black
        strikeColor = Color.Black
        backColor = Color.Yellow
        instruction = "Tap the Larger Number!"
        gameEnded = false
        won = false
        newPair()
    }

    Surface(
        color = backColor,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Score: $score",
            fontSize = 24.sp,
            color = scoreColor,
            modifier = Modifier.padding(start = 16.dp, top = 64.dp)
        )
        Text(
            text = "Strikes: $strikes",
            fontSize = 24.sp,
            color = strikeColor,
            modifier = Modifier.padding(start = 16.dp, top = 110.dp)
        )
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Button(onClick = { restart() }) {
                Text("Restart")
            }
            Spacer(modifier = Modifier.size(16.dp))

            Text(instruction, fontSize = 32.sp)
            Spacer(modifier = Modifier.size(48.dp))

            if (!gameEnded) {
                NumBox(Color(0xFF90D3F1), numA) {
                    handleTap(numA >= numB)
                }
                Spacer(modifier = Modifier.size(48.dp))
                NumBox(Color(0xFFFFB342), numB) {
                    handleTap(numB >= numA)
                }
                Spacer(modifier = Modifier.size(48.dp))
            }
        }
    }
}

@Composable
fun NumBox(color: Color, number: Int, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .size(150.dp)
            .clickable { onClick() },
        color = color
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "$number",
                fontSize = 64.sp
            )
        }
    }
}

@Composable
fun GameOverScreen(onRestart: () -> Unit) {

    Surface(color = Color.Black, modifier = Modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text("Game Over!", color = Color.White, fontSize = 32.sp)

            Spacer(modifier = Modifier.size(24.dp))

            Button(onClick = { onRestart() }) {
                Text("Restart")
            }
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
                onStartClicked()
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

        Surface(
            modifier = Modifier
                .size(width = 300.dp, height = 200.dp)
                .clip(RoundedCornerShape(8.dp))
                .border(BorderStroke(4.dp, Color.Red)),

            ) {
            Image(
                painter = painterResource(R.drawable.launchimage),
                contentDescription = "start",
                modifier = Modifier
                    .fillMaxSize()


            )
        }
    }
}
