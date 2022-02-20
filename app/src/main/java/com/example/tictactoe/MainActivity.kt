package com.example.tictactoe

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.tictactoe.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private var PLAYER = true
    private var COUNT = 0
    private val TAG = "TIC_TAC_TOE"

    var matrixStatus = Array(3){IntArray(3)}
    lateinit var matrix: Array<Array<Button>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        matrix = arrayOf(
            arrayOf(binding.btn1,binding.btn2,binding.btn3),
            arrayOf(binding.btn4,binding.btn5,binding.btn6),
            arrayOf(binding.btn7,binding.btn8,binding.btn9)
        )

        for(buttons in matrix){
            for(button in buttons){
             button.setOnClickListener(this)
            }
        }

        initializeMatrixStatus()

        binding.resetBtn.setOnClickListener{
            PLAYER = true
            COUNT = 0
            updateDisplay("Player X Turn")
            initializeMatrixStatus()
        }
    }

    private fun updateDisplay(s: String) {
        binding.textDisplay.text = s
    }


    private fun initializeMatrixStatus(){
        for(i in 0..2){
            for(j in 0..2){
                matrixStatus[i][j] = -1;
                matrix[i][j].isEnabled = true
                matrix[i][j].text = ""
                matrix[i][j].setTextColor(Color.parseColor("#FFFFFF"))

            }
        }
    }

    private fun disableButton(){
        for(buttons in matrix){
            for(button in buttons){
                button.isEnabled = false
            }
        }
    }

    private fun changeTextColor( row1:Int,col1:Int,row2:Int,col2:Int,row3:Int,col3:Int,){

        matrix[row1][col1].setTextColor(Color.parseColor("#00FFFF"));
        matrix[row2][col2].setTextColor(Color.parseColor("#00FFFF"));
        matrix[row3][col3].setTextColor(Color.parseColor("#00FFFF"));
    }

    override fun onClick(view: View) {

        when(view.id){
            R.id.btn1->{
                updateValue(row=0,col=0,player=PLAYER)
            }
            R.id.btn2->{
                updateValue(row=0,col=1,player=PLAYER)

            }
            R.id.btn3->{
                updateValue(row=0,col=2,player=PLAYER)

            }
            R.id.btn4->{
                updateValue(row=1,col=0,player=PLAYER)

            }
            R.id.btn5->{
                updateValue(row=1,col=1,player=PLAYER)

            }
            R.id.btn6->{
                updateValue(row=1,col=2,player=PLAYER)

            }
            R.id.btn7->{
                updateValue(row=2,col=0,player=PLAYER)

            }
            R.id.btn8->{
                updateValue(row=2,col=1,player=PLAYER)

            }
            R.id.btn9->{
                updateValue(row=2,col=2,player=PLAYER)

            }

        }

        PLAYER = !PLAYER
        COUNT++

        if(PLAYER){
            updateDisplay("Player X Turn")
        }else{
            updateDisplay("Player O Turn")
        }

        if(COUNT == 9)
            updateDisplay("Game Draw")

        checkWinningCondition()
    }

    private fun checkWinningCondition() {

        // for row winning condition
        for(i in 0..2){
            Log.d(TAG, "checkWinningCondition before: ${matrixStatus.contentDeepToString()} i = ${i}")
            if(matrixStatus[i][0] == matrixStatus[i][1] && matrixStatus[i][1] == matrixStatus[i][2] && matrixStatus[i][0]!=-1){
                Log.d(TAG, "checkWinningCondition after: ${matrixStatus.contentDeepToString()} i = ${i}")
                if(matrixStatus[i][0] == 1){
                    updateDisplay("Player X is Winner")
                    disableButton()
                    changeTextColor(i,0,i,1,i,2)
                    break
                }
                else if(matrixStatus[i][0] == 0){
                    updateDisplay("Player O is Winner")
                    changeTextColor(i,0,i,1,i,2)
                    disableButton()
                    break
                }
            }
        }

        // for column winning condition
        for(i in 0..2){
            if(matrixStatus[0][i] == matrixStatus[1][i] && matrixStatus[1][i] == matrixStatus[2][i] && matrixStatus[0][i]!=-1){
                if(matrixStatus[0][i] == 1){
                    updateDisplay("Player X is Winner")
                    changeTextColor(0,i,1,i,2,i)
                    disableButton()
                    break
                }
                else if(matrixStatus[0][i] == 0){
                    updateDisplay("Player O is Winner")
                    changeTextColor(0,i,1,i,2,i)
                    disableButton()
                    break
                }
            }
        }



        //for primary diagonal
        if(matrixStatus[0][0] == matrixStatus[1][1] && matrixStatus[1][1] == matrixStatus[2][2]){

            if(matrixStatus[1][1] == 1){
                updateDisplay("Player X is Winner")
                changeTextColor(0,0,1,1,2,2);
                disableButton()
            }
            else if(matrixStatus[1][1] == 0) {
                updateDisplay("Player O is Winner")
                changeTextColor(0,0,1,1,2,2);
                disableButton()
            }
        }

        //for secondary diagonal
        if(matrixStatus[0][2] == matrixStatus[1][1] && matrixStatus[1][1] == matrixStatus[2][0]){
            if(matrixStatus[1][1] == 1){
                updateDisplay("Player X is Winner")
                changeTextColor(2,0,1,1,0,2);
                disableButton()
            }
            else if(matrixStatus[1][1] == 0) {
                changeTextColor(2,0,1,1,0,2);
                updateDisplay("Player O is Winner")
                disableButton()
            }
        }
    }




    private fun updateValue(row: Int, col: Int, player: Boolean) {

        val text = if(player) "X" else "O"
        val value = if(player) 1 else 0

        matrix[row][col].apply{
            isEnabled = false
            setText(text)
        }
        matrixStatus[row][col] = value


    }
}