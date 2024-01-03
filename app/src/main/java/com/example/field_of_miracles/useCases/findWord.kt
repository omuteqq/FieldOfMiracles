package com.example.field_of_miracles.useCases

import com.example.field_of_miracles.data.Words
import kotlin.random.Random

class findWord {
    val findIndex = findIndex()
    val words = Words()
    val index = findIndex.findWordIndex(words.arrayOfWords)

    fun findWordExecute(words: List<String>): String{
        val word = words[index]
        return word
    }

    fun isLetterInWord(word:String, letter: String): Boolean{
        if (letter in word && letter  != ""){
            return true
        }
        return false
    }

    fun findArrayOfLetters(word:String, letter: String, text: String): String {

        var j = 0
        var count = 0
        val arrayOfWord = mutableListOf<String>()
        var newText = ""

        while (j < text.length){
            arrayOfWord.add(text[j].toString())
            j++
        }

        j = 0
        while (j < word.length){
            if (word[j].toString() == letter && arrayOfWord[j] != ""){
                arrayOfWord.removeAt(j*3+1)
                arrayOfWord.add(j*3+1, letter)
                count++
            }
            j++
        }

        count = 0
        j=0
        while (j < arrayOfWord.size){
            newText += arrayOfWord[j]
            j++
        }


        return newText
    }



    fun findQuestionExecute(): String{
        val question = Words()
        return question.arrayQuestions[index]
    }

}