package com.abshtyfikant.calculator_xml

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //COMPONENTS DECLARATION
        //digits
        val oneButton = findViewById<Button>(R.id.btn_1)
        val twoButton = findViewById<Button>(R.id.btn_2)
        val threeButton = findViewById<Button>(R.id.btn_3)
        val fourButton = findViewById<Button>(R.id.btn_4)
        val fiveButton = findViewById<Button>(R.id.btn_5)
        val sixButton = findViewById<Button>(R.id.btn_6)
        val sevenButton = findViewById<Button>(R.id.btn_7)
        val eightButton = findViewById<Button>(R.id.btn_8)
        val nineButton = findViewById<Button>(R.id.btn_9)
        val zeroButton = findViewById<Button>(R.id.btn_0)
        val commaButton = findViewById<Button>(R.id.btn_comma)

        //operations
        val divisionButton = findViewById<Button>(R.id.btn_divide)
        val multiplicationButton = findViewById<Button>(R.id.btn_multiply)
        val additionButton = findViewById<Button>(R.id.btn_add)
        val subtractionButton = findViewById<Button>(R.id.btn_subtract)

        //calculator control
        val clearButton = findViewById<Button>(R.id.btn_clear)
        val backspaceButton = findViewById<Button>(R.id.btn_backspace)
        val resultButton = findViewById<Button>(R.id.btn_result)

        //textViews
        val inputText = findViewById<TextView>(R.id.textInput)
        val outputText = findViewById<TextView>(R.id.textOutput)

        //data variables
        var inputString = ""
        var inputSpannableString = SpannableStringBuilder("")
        val operators = "+-*/"
        val operationsMap = mapOf("+" to 1.0, "-" to -1.0)

        //Toast error message
        fun errorToast(){
            val msg = if(inputString.isEmpty()) "Input some numbers first!"
                else "Can't use an operator here."
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }

        //Convert string to operator
        fun operatorFromString(sign: String): (Double, Double) -> Double{
            return when(sign){
                "*" -> {a, b -> a * b}
                "/" -> {a, b -> a / b}
                else -> throw Exception("Operator conversion error.")
            }
        }

        //USER INPUT
        //digits
        oneButton.setOnClickListener {
            inputString += "1"
            inputText.text = inputSpannableString.append("1")
        }

        twoButton.setOnClickListener {
            inputString += "2"
            inputText.text = inputSpannableString.append("2")
        }

        threeButton.setOnClickListener {
            inputString += "3"
            inputText.text = inputSpannableString.append("3")
        }

        fourButton.setOnClickListener {
            inputString += "4"
            inputText.text = inputSpannableString.append("4")
        }

        fiveButton.setOnClickListener {
            inputString += "5"
            inputText.text = inputSpannableString.append("5")
        }

        sixButton.setOnClickListener {
            inputString += "6"
            inputText.text = inputSpannableString.append("6")
        }

        sevenButton.setOnClickListener {
            inputString += "7"
            inputText.text = inputSpannableString.append("7")
        }

        eightButton.setOnClickListener {
            inputString += "8"
            inputText.text = inputSpannableString.append("8")
        }

        nineButton.setOnClickListener {
            inputString += "9"
            inputText.text = inputSpannableString.append("9")
        }

        zeroButton.setOnClickListener {
            if (inputString.isEmpty()) {
                errorToast()
            } else {
                inputString += "0"
                inputText.text = inputSpannableString.append("0")
            }
        }

        commaButton.setOnClickListener {
            if (inputString.isEmpty()){
                errorToast()
            } else {
                inputString += "."
                inputText.text = inputSpannableString.append(",")
            }
        }

        //operators
        multiplicationButton.setOnClickListener{
            if (inputString.isEmpty())
                errorToast()
            else if (inputString.last() in operators)
                errorToast()
            else {
                inputString += "*"
                inputSpannableString.append("*")
                val lastIndex = inputSpannableString.length - 1
                val operatorColor = ForegroundColorSpan(Color.RED)
                inputSpannableString.setSpan(operatorColor, lastIndex, lastIndex + 1, 0)
                inputText.text = inputSpannableString
            }
        }

        divisionButton.setOnClickListener{
            if (inputString.isEmpty())
                errorToast()
            else if (inputString.last() in operators)
                errorToast()
            else {
                inputString += "/"
                inputSpannableString.append("/")
                val lastIndex = inputSpannableString.length - 1
                val operatorColor = ForegroundColorSpan(Color.RED)
                inputSpannableString.setSpan(operatorColor, lastIndex, lastIndex + 1, 0)
                inputText.text = inputSpannableString
            }
        }

        subtractionButton.setOnClickListener{
            if (inputString.isNotEmpty() && inputString.last() in operators)
                errorToast()
            else {
                inputString += if(inputString.isEmpty()) "0-" else "-"
                inputSpannableString.append("-")
                val lastIndex = inputSpannableString.length - 1
                val operatorColor = ForegroundColorSpan(Color.RED)
                inputSpannableString.setSpan(operatorColor, lastIndex, lastIndex + 1, 0)
                inputText.text = inputSpannableString
            }
        }

        additionButton.setOnClickListener{
            if (inputString.isEmpty())
                errorToast()
            else if (inputString.last() in operators)
                errorToast()
            else {
                inputString += "+"
                inputSpannableString.append("+")
                val lastIndex = inputSpannableString.length - 1
                val operatorColor = ForegroundColorSpan(Color.RED)
                inputSpannableString.setSpan(operatorColor, lastIndex, lastIndex + 1, 0)
                inputText.text = inputSpannableString
            }
        }

        //calculator control
        backspaceButton.setOnClickListener{
            if(inputString.isEmpty()){
                errorToast()
            } else {
                val lastIndex = inputSpannableString.length - 1
                val temp = inputString.dropLast(1)
                inputString = temp
                inputSpannableString.delete(lastIndex, lastIndex + 1)
                inputText.text = inputSpannableString
            }
        }

        clearButton.setOnClickListener{
            inputString = ""
            inputSpannableString = SpannableStringBuilder("")
            inputText.text = ""
            outputText.text = ""
        }

        resultButton.setOnClickListener{
            if(inputString.isEmpty())
                errorToast()
            else if(inputString.last() in operators) outputText.text = inputString.dropLast(1)
            else if (inputString.count {it in operators} < 1) outputText.text = inputString
            else{
                val operationList = Regex("[+\\-*/]").findAll(inputString)
                    .map { it.value }
                    .toMutableList()
                val numbersList = mutableListOf<Double>()

                val tempNumbersList = inputString.split(Regex("[" + Regex.escape(operators) + "]"))
                for(i in tempNumbersList) {
                    numbersList.add(i.toDouble())
                }

                val isDivOrMult = operationList.find{it == "*" || it == "/"} ?: ""
                if(isDivOrMult != ""){
                    val operatorIndices = mutableListOf<Int>()
                    for (i in operationList.indices) {
                        if (operationList[i] == "*" || operationList[i] == "/") {
                            operatorIndices.add(i)
                        }
                    }
                    for (i in operatorIndices){
                        numbersList[i+1] = operatorFromString(operationList[i]).invoke(
                            numbersList[i], numbersList[i+1]).toDouble()
                    }
                    for ((foo, i) in operatorIndices.withIndex()){
                        numbersList.removeAt(i - foo)
                        operationList.removeAt(i - foo)
                    }
                }
                if (operationList.isNotEmpty()) {
                    for (i in operationList.indices) {
                        numbersList[i + 1] *= operationsMap[operationList[i]]
                            ?: throw Exception("Error in changing signs of numbers.")
                    }
                    outputText.text = numbersList.sum().toString().replace(".", ",")
                } else {
                    outputText.text = numbersList[0].toString().replace(".", ",")
                }
            }
        }
    }
}