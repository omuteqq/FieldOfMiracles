@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.field_of_miracles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.field_of_miracles.data.Scores
import com.example.field_of_miracles.data.Words
import com.example.field_of_miracles.useCases.findIndex
import com.example.field_of_miracles.useCases.findWord
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val words = Words()
            val findWord = findWord()

        val navController = rememberNavController()

        NavHost(navController, startDestination = "welcome") {
            composable("welcome") { WelcomeScreen(navController) }
            composable("mainScreen") { MainScreen(findWord.findWordExecute(words.arrayOfWords), findWord.findQuestionExecute())}
        }

            val darkTheme = isSystemInDarkTheme()
            val systemUiController = rememberSystemUiController()
            systemUiController.isSystemBarsVisible = false
            if(darkTheme){
                systemUiController.setSystemBarsColor(
                    color = Color.Transparent
                )
            }else{
                systemUiController.setSystemBarsColor(
                    color = Color.Transparent
                )
            }



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

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(top = 220.dp),
        contentAlignment = Alignment.Center){
        Text(text = "ИГРАТЬ",
            modifier = Modifier
                .border(3.dp, color = Color.White)
                .clip(
                    shape = RoundedCornerShape(12.dp)
                )
                .width(240.dp)
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
fun MainScreen(mainWord: String, mainQuestion: String) {

    // variables
    val findWord = findWord()
    val scores = Scores()
    val findIndex = findIndex()

    var enabledTextField by remember {
        mutableStateOf(false)
    }

    var enabledButton1 by remember {
        mutableStateOf(true)
    }

    var enabledButton2 by remember {
        mutableStateOf(true)
    }

    var enabledButton3 by remember {
        mutableStateOf(false)
    }

    var sectorPrize by remember {
        mutableStateOf(false)
    }

    var isException by remember {
        mutableStateOf(false)
    }

    var text by remember {
        mutableStateOf("")
    }




    var counter by remember {
        mutableStateOf(0)
    }

    var num by remember {
        mutableStateOf(0)
    }

    var score by remember {
        mutableStateOf(0)
    }

    var flag by remember {
        mutableStateOf(0)
    }

    var isWinner by remember {
        mutableStateOf(false)
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
    var colorBorder2 = Color.Black
    var colorBorder3 = Color.Black


    var editText by remember {
        mutableStateOf("${counter % 3 + 1} Игрок \nКрутите барабан")
    }


    var textField by remember {
        mutableStateOf("")
    }

    var widthTextField by remember {
        mutableStateOf(0.2f)
    }

    // Border colors

    if (counter % 3 == 0) {
        colorBorder1 = Color.Green
        colorBorder2 = Color.Black
        colorBorder3 = Color.Black
    } else
        if (counter % 3 == 1) {
            colorBorder1 = Color.Black
            colorBorder2 = Color.Green
            colorBorder3 = Color.Black
        } else {
            if (counter % 3 == 2) {
                colorBorder1 = Color.Black
                colorBorder2 = Color.Black
                colorBorder3 = Color.Green
            }
        }


    // Players icons

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(74.dp)
                    .border(3.dp, colorBorder1),
                contentAlignment = Alignment.Center
            ) {
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

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(74.dp)
                    .border(3.dp, colorBorder2),
                contentAlignment = Alignment.Center
            ) {
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

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(74.dp)
                    .border(3.dp, colorBorder3),
                contentAlignment = Alignment.Center
            ) {
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 170.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Text(
            text = editText,
            fontSize = 32.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center
        )


        TextField(
            value = textField, onValueChange = { newText ->
                textField = newText

            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth(widthTextField),
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
            textStyle = TextStyle.Default.copy(fontSize = 32.sp, textAlign = TextAlign.Center),
            enabled = enabledTextField,
            keyboardActions = KeyboardActions(onDone = {


                if (score == 0 && sectorPrize && textField != "") {
                    try {
                        num = textField.toInt()
                    } catch (e: Exception) {
                        if (textField != "") {
                            isException = true
                            flag = 1
                        }
                    } finally {
                        if (flag == 0) {
                            isException = false
                        }
                        if (isException) {
                            editText = "Введите натуральное число!"
                            flag = 0
                        } else {
                            if (num < 1 || num > mainWord.length) {
                                editText = "Введите число \nот 1 до ${mainWord.length}"
                            } else {

                                enabledTextField = false
                                enabledButton2 = true
                                editText = "${textField} буква открыта \n" +
                                        "${counter % 3 + 1} Игрок крутите барабан"
                                enabledTextField = false
                                var j = 1
                                val letter = mainWord[textField.toInt() - 1]
                                val newText = text.toCharArray()

                                text.toCharArray()
                                while (j <= mainWord.length) {
                                    if (letter == mainWord[j-1]){
                                        newText[(j*3)-2] = letter
                                    }

                                    j++
                                }

                                text = String(newText)
                                if (text.contains("_") == false && textField != "") {
                                    enabledButton1 = false
                                    enabledButton2 = false
                                    enabledButton3 = false
                                    enabledTextField = false
                                    j = 0
                                    text = ""
                                    while (j < mainWord.length) {
                                        text += " ${mainWord[j]} "
                                        j++
                                    }
                                    isWinner = true
                                    editText =
                                        "Победил ${counter % 3 + 1} игрок" // Реализовать WinnerScreen

                                }
                            }
                        }


                    }

                } else
                    if (widthTextField == 0.65f && textField != "" || text.contains("_") == false) {

                        if (textField
                                .lowercase()
                                .replace(" ", "") == mainWord.lowercase()
                            || text.contains("_") == false
                        ) {
                            enabledButton1 = false
                            enabledButton2 = false
                            enabledButton3 = false
                            enabledTextField = false
                            var j = 0
                            text = ""
                            while (j < mainWord.length) {
                                text += " ${mainWord[j]} "
                                j++
                            }
                            isWinner = true
                            editText =
                                "Победил ${counter % 3 + 1} игрок" // Реализовать WinnerScreen

                        } else {
                            widthTextField = 0.2f
                            counter++
                            editText =
                                "Это не то слово. \n${counter % 3 + 1} Игрок крутите барабан"
                            enabledButton2 = true
                            enabledButton3 = false
                            enabledTextField = false
                        }
                    } else
                        if (textField.length > 1) {
                            editText =
                                "Введите не более одной буквы!"

                        } else
                            if (textField != "") {
                                if (textField.lowercase() !in "а".."я"){
                                    editText = "Введите русскую букву"
                                }else

                                if (textField in text) {
                                    counter++
                                    editText =
                                        "Уже вводили эту букву \n${counter % 3 + 1} Игрок крутите барабан"
                                    enabledButton2 = true
                                    enabledTextField = false
                                } else {
                                    if (findWord.isLetterInWord(
                                            word = mainWord,
                                            textField
                                        )
                                    ) {

                                        val newText = text
                                        var count = newText

                                            .filter { it.toString() == "_" }
                                            .count()
                                        text = findWord.findArrayOfLetters(
                                            mainWord.lowercase(),
                                            textField.lowercase(),
                                            text
                                        )
                                        count -= text
                                            .filter { it.toString() == "_" }
                                            .count()
                                        if (counter % 3 == 0) {
                                            score1 += score * count
                                        } else
                                            if (counter % 3 == 1) {
                                                score2 += score * count
                                            } else {
                                                if (counter % 3 == 2) {
                                                    score3 += score * count
                                                }
                                            }
                                        if (text.contains("_") == false && textField != "") {
                                            enabledButton1 = false
                                            enabledButton2 = false
                                            enabledButton3 = false
                                            enabledTextField = false
                                            var j = 0
                                            text = ""
                                            while (j < mainWord.length) {
                                                text += " ${mainWord[j]} "
                                                j++
                                            }
                                            isWinner = true
                                            editText =
                                                "Победил ${counter % 3 + 1} игрок" // Реализовать WinnerScreen

                                        } else {

                                            editText =
                                                "Есть такая буква. \nКрутите ещё раз барабан"
                                            enabledButton2 = true
                                            enabledTextField = false
                                            enabledButton3 = false
                                        }
                                    } else {
                                        enabledTextField = false
                                        counter++
                                        enabledButton2 = true

                                        editText =
                                            "Нет такой буквы. \n ${counter % 3 + 1} игрок крутите барабан:"
                                    }
                                }
                            }
                textField = ""
            })

        )

        Text(
            text = text,
            modifier = Modifier
                .padding(top = 10.dp),
            fontSize = 42.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center
        )


        Row(
            modifier = Modifier
                .padding(top = 48.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {


            Box(
                modifier = Modifier
                    .clickable(enabledButton1, onClickLabel = null, role = null, onClick = {
                        textField = ""
                        editText = "Ваше слово:"
                        widthTextField = 0.65f
                        sectorPrize = false
                        enabledTextField = true
                        enabledButton2 = false
                        enabledButton3 = true


                    })
                    .width(125.dp)
                    .height(68.dp)
                    .border(3.dp, Color.Black),

                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Отгадать слово",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center
                )
            }

            Box(
                modifier = Modifier
                    .clickable(enabledButton2, onClickLabel = null, role = null, onClick = {
                        widthTextField = 0.2f
                        score = scores.arrayOfScores[findIndex.findScoreIndex(scores.arrayOfScores)]
                        if (score == 0) {
                            editText = "Сектор + на барабане. Выберите номер буквы:"
                            enabledTextField = true
                            sectorPrize = true
                            enabledButton3 = true
                            enabledButton2 = false
                        } else if (score == -1) {
                            counter++
                            editText =
                                "На барабане пропуск хода. \n ${counter % 3 + 1} игрок крутите барабан"
                            enabledButton3 = true
                            sectorPrize = false
                        } else {
                            editText = "На барабане $score баллов. Ваша буква:"
                            enabledButton2 = false
                            enabledButton3 = true
                            enabledTextField = true
                            sectorPrize = false
                        }

                    })
                    .width(125.dp)
                    .height(68.dp)
                    .border(3.dp, Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Крутить барабан",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center
                )
            }

        }

    }

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .padding(bottom = 80.dp), contentAlignment = Alignment.BottomCenter
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Вопрос:",
                fontSize = 36.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 14.dp)
            )
            Text(
                text = mainQuestion,
                fontSize = 24.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
    }
}
