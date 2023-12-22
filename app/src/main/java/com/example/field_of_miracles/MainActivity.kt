@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.field_of_miracles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.field_of_miracles.data.Scores
import com.example.field_of_miracles.data.Words
import com.example.field_of_miracles.useCases.findIndex
import com.example.field_of_miracles.useCases.findQuestion
import com.example.field_of_miracles.useCases.findWord
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val words: Words = Words()
            val findWord: findWord = findWord()
            val findIndex = findIndex()
            val index = findIndex.findWordIndex(words.arrayOfWords)
            val findQuestion: findQuestion = findQuestion()

        val navController = rememberNavController()

        NavHost(navController, startDestination = "welcome") {
            composable("welcome") { WelcomeScreen(navController) }
            composable("mainScreen") { MainScreen(navController, findWord.findWordExecute(words.arrayOfWords, findIndex.findWordIndex(words.arrayOfWords)), findQuestion.findQuestionExecute(findIndex.findWordIndex(words.arrayOfWords))) }
        }

        var systemUiController = rememberSystemUiController()
            systemUiController.setSystemBarsColor(
                color = Color.Transparent
            )



        }
    }
}


@Composable
fun WelcomeScreen(navController: NavController){

    val interactionSource = remember {
        MutableInteractionSource()
    }

    val fontFamily = FontFamily(
        Font(R.font.mr_countryhouseg_0, FontWeight.Normal),
    )

    val words: Words = Words()
    val findWord: findWord = findWord()
    val findIndex = findIndex()
    val index = findIndex.findWordIndex(words.arrayOfWords)
    val findQuestion: findQuestion = findQuestion()

    Box(
        modifier = with (Modifier){
            fillMaxSize()
                .paint(
                    // Replace with your image id
                    painterResource(id = R.drawable.background),
                    contentScale = ContentScale.FillBounds)

        }){
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 380.dp),
        contentAlignment = Alignment.TopCenter) {

        Image(painter = painterResource(id = R.drawable.pole),
            contentDescription = "logo",
            modifier = Modifier.size(500.dp))
        
    }
        /*
    Column {
        Text (text = findWord.findWordExecute(words.arrayOfWords, index))
        Text (text = findQuestion.findQuestionExecute(index))
    }
        */
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(top = 220.dp),
        contentAlignment = Alignment.Center){
        Text(text = "НАЧАТЬ ИГРУ",
            modifier = Modifier
                .border(3.dp, Color.White)
                .clip(
                    shape = RoundedCornerShape(12.dp)
                )
                .width(340.dp)
                .clickable(indication = null, interactionSource = interactionSource) {
                    navController.navigate("mainScreen")
                },
            textAlign = TextAlign.Center,
            fontSize = 58.sp,
            color = colorResource(id = R.color.yellow),
            fontFamily = fontFamily
            )
        }
    }
}

@Composable
fun MainScreen(navController: NavController, mainWord: String, mainQuestion: String){

    // variables
    val findWord = findWord()
    val words = Words()
    val scores:Scores = Scores()
    val findIndex = findIndex()

    var enabledTextField by remember {
        mutableStateOf(true)
    }

    var score1 by remember {
        mutableStateOf(0)
    }
    var score2 by remember {
        mutableStateOf(0)
    }
    var score3 by remember {
        mutableStateOf(0)
    }

    var colorBorder1 = Color.Black
    var colorBorder2= Color.Black
    var colorBorder3= Color.Black

    var confirmCount by remember {
        mutableStateOf(0)
    }

    var editText by remember {
        mutableStateOf("Крутите барабан!")
    }

    var textField by remember {
        mutableStateOf("")
    }

    // Border colors

    if(confirmCount%3 == 0){
        colorBorder1 = Color.Green
        colorBorder2 = Color.Black
        colorBorder3 = Color.Black
    } else
        if(confirmCount%3 == 1){
            colorBorder1 = Color.Black
            colorBorder2 = Color.Green
            colorBorder3 = Color.Black
        }
        else {
            if(confirmCount%3 == 2){
                colorBorder1 = Color.Black
                colorBorder2 = Color.Black
                colorBorder3 = Color.Green
            }
        }

    // editText = "На барабане ${scores.arrayOfScores[findIndex.findScoreIndex(scores.arrayOfScores)]} баллов. Ваша буква:"

    // Players icons

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {

        Column (horizontalAlignment = Alignment.CenterHorizontally){
            Box(modifier = Modifier
                .size(74.dp)
                .border(3.dp, colorBorder1),
                contentAlignment = Alignment.Center){
                Text(
                    text = "1P",
                    fontSize = 38.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center
                )
            }
            Text(
                text = "$score1",
                fontSize = 32.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center
            )
        }

        Column (horizontalAlignment = Alignment.CenterHorizontally){
            Box(modifier = Modifier
                .size(74.dp)
                .border(3.dp, colorBorder2),
                contentAlignment = Alignment.Center){
                Text(
                    text = "2P",
                    fontSize = 38.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center
                )
            }
            Text(
                text = "$score2",
                fontSize = 32.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center
            )
        }

        Column (horizontalAlignment = Alignment.CenterHorizontally){
            Box(modifier = Modifier
                .size(74.dp)
                .border(3.dp, colorBorder3),
                contentAlignment = Alignment.Center){
                Text(
                    text = "3P",
                    fontSize = 38.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center
                )
            }
            Text(
                text = "$score3",
                fontSize = 32.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center
            )
        }
    }

    //

    Column (modifier = Modifier
        .fillMaxSize()
        .padding(top = 150.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {

        Text(text = "$editText",
            fontSize = 32.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center)


        TextField(value = textField, onValueChange ={ newText ->
            textField = newText

        },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth(0.2f),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Black,
                disabledIndicatorColor = Color.Black,
                errorIndicatorColor = Color.Black,
                unfocusedIndicatorColor = Color.Black,
                containerColor = Color.Transparent,
                cursorColor = Color.Black,
                unfocusedLabelColor = Color.Black,
                unfocusedTrailingIconColor = Color.Black,
                unfocusedSupportingTextColor = Color.Black,
                unfocusedLeadingIconColor = Color.Black,

            ),
            textStyle = TextStyle.Default.copy(fontSize = 44.sp, textAlign = TextAlign.Center),

            enabled = enabledTextField

        )

        Text(text = findWord.findArrayOfLetters(mainWord.lowercase(), textField.lowercase()),
            fontSize = 42.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center)

        Text(text = mainWord)
        Text(text = mainQuestion)
    }




}