package com.abshtyfikant.calculator_xml

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
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
        val operators = "+-*/"
        val operationsMap = mapOf<String, Int>("+" to 1, "-" to -1)

        //Toast error message
        fun errorToast(){
            val msg = if(inputString.isEmpty()) "Input some numbers first!"
                else "Can't use an operator here."
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }

        //Convert string to operator
        fun operatorFromString(sign: String): (Int, Int) -> Int{
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
            inputText.text = inputString
        }

        twoButton.setOnClickListener {
            inputString += "2"
            inputText.text = inputString
        }

        threeButton.setOnClickListener {
            inputString += "3"
            inputText.text = inputString
        }

        fourButton.setOnClickListener {
            inputString += "4"
            inputText.text = inputString
        }

        fiveButton.setOnClickListener {
            inputString += "5"
            inputText.text = inputString
        }

        sixButton.setOnClickListener {
            inputString += "6"
            inputText.text = inputString
        }

        sevenButton.setOnClickListener {
            inputString += "7"
            inputText.text = inputString
        }

        eightButton.setOnClickListener {
            inputString += "8"
            inputText.text = inputString
        }

        nineButton.setOnClickListener {
            inputString += "9"
            inputText.text = inputString
        }

        zeroButton.setOnClickListener {
            if (inputString.isEmpty()) {
                errorToast()
            } else {
                inputString += "0"
                inputText.text = inputString
            }
        }

        //operators
        multiplicationButton.setOnClickListener{
            if (inputString.last() in operators || inputString.isEmpty()) {
                errorToast()
            } else {
                inputString += "*"
                inputText.text = inputString
            }
        }

        divisionButton.setOnClickListener{
            if (inputString.last() in operators || inputString.isEmpty()) {
                errorToast()
            } else {
                inputString += "/"
                inputText.text = inputString
            }
        }

        subtractionButton.setOnClickListener{
            if (inputString.last() in operators || inputString.isEmpty()) {
                errorToast()
            } else {
                inputString += "-"
                inputText.text = inputString
            }
        }

        additionButton.setOnClickListener{
            if (inputString.last() in operators || inputString.isEmpty()) {
                errorToast()
            } else {
                inputString += "+"
                inputText.text = inputString
            }
        }

        //calculator control
        backspaceButton.setOnClickListener{
            if(inputString.isEmpty()){
                errorToast()
            } else {
                val temp = inputString.dropLast(1)
                inputString = temp
                inputText.text = inputString
            }
        }

        clearButton.setOnClickListener{
            inputString = ""
            inputText.text = ""
            outputText.text = ""
        }

        resultButton.setOnClickListener{
            if(inputString.last() in operators) outputText.text = inputString.dropLast(1)
            else if (inputString.count {it in operators} < 1) outputText.text = inputString
            else{
                Log.w(null, "IN RESULT-ELSE")
                //var numbersList: MutableList<Int>
                val operationList = Regex("[+\\-*/]").findAll(inputString)
                    .map { it.value }
                    .toMutableList()
                val numbersList = mutableListOf<Int>()

                Log.w(null, "Input String: $inputString")

                val tempNumbersList = inputString.split(Regex("[" + Regex.escape(operators) + "]"))
                for(i in tempNumbersList) {
                    numbersList.add(i.toInt())
                    Log.w(null, "Added $i")
                }
//                val tempOperationList = inputString.split(Regex("[\\+\\-\\*/]"))
//                for(k in tempOperationList) {
//                    operationList.add(k)
//                    Log.w(null, "Operator Added $k")
//                }
//                inputString.split(Regex("[*\\-/+]")).forEach {
//                    operationList.add(it)
//                    Log.w(null, "Operator Added $it")
//                }

                val isDivOrMult = operationList.find{it == "*" || it == "/"} ?: ""
                Log.w(null, "after finding * and /")
                if(isDivOrMult != ""){
                    val operatorIndices = mutableListOf<Int>()
                    for (i in operationList.indices) {
                        Log.w(null, "1st indices loop")
                        if (operationList[i] == "*" || operationList[i] == "/") {
                            operatorIndices.add(i)
                        }
                    }
                    for (i in operatorIndices){
                        Log.w(null, "2nd indices loop")
                        numbersList[i+1] = operatorFromString(operationList[i]).invoke(
                            numbersList[i], numbersList[i+1])
                    }
                    for (i in operatorIndices){
                        Log.w(null, "3rd indices loop")
                        numbersList.removeAt(i)
                        operationList.removeAt(i)
                    }
                }
                for (i in operationList.indices){
                    Log.w(null, "4th indices loop")
                    numbersList[i+1] *= operationsMap[operationList[i]]
                        ?: throw Exception("Error in changing signs of numbers.")
                }
                outputText.text = numbersList.sum().toString()
            }
        }
    }
}