package com.example.tictactoekotlyn

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tictactoekotlyn.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    enum class Turn{
        CROSS,
        NAUGHT
    }

    private var firstTurn = Turn.CROSS
    private var currentTurn = Turn.CROSS
    private lateinit var binding : ActivityMainBinding
    private var boardList = mutableListOf<Button>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBoard()

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initBoard(){
        boardList.add(binding.a1)
        boardList.add(binding.a2)
        boardList.add(binding.a3)
        boardList.add(binding.b1)
        boardList.add(binding.b2)
        boardList.add(binding.b3)
        boardList.add(binding.c1)
        boardList.add(binding.c2)
        boardList.add(binding.c3)
    }

    fun boardTapped(view:View){
        if(view !is Button)
            return
        addToBoard(view)
        
        if(checkForVictory("O"))
        {
            result("NOUGHTS WIN")
        }
        else if(checkForVictory("X"))
        {
            result("CROSS WIN")
        }
        else if(fullBoard()){
            result("Draw")
        }
    }

    private fun checkForVictory(s:String): Boolean {

        if((binding.a1.text == s) && (binding.a2.text == s) && (binding.a3.text == s))
        {
            return true
        }
        if((binding.b1.text == s) && (binding.b2.text == s) && (binding.b3.text == s))
        {
            return true
        }
        if((binding.c1.text == s) && (binding.c2.text == s) && (binding.c3.text == s))
        {
            return true
        }



        if( (binding.a1.text == s) && (binding.b1.text == s) && (binding.c1.text == s)){
            return true
        }
        if( (binding.a2.text == s) && (binding.b2.text == s) && (binding.c2.text == s)){
            return true
        }
        if( (binding.a3.text == s) && (binding.b3.text == s) && (binding.c3.text == s)){
            return true
        }


        if( (binding.a1.text == s) && (binding.b2.text == s) && (binding.c3.text == s)){
            return true
        }
        if( (binding.a3.text == s) && (binding.b2.text == s) && (binding.c1.text == s)){
            return true
        }
        return false
    }

    private fun result(title: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setPositiveButton("Reset")
            {_,_ ->
                resetBoard()
            }
            .setCancelable(false)
            .show()
    }

    private fun resetBoard() {
        for(button in boardList) {
            button.text = ""
        }
        firstTurn = Turn.CROSS
        currentTurn = Turn.CROSS
        setTurnLable()
    }

    private fun fullBoard(): Boolean {
        for(button in boardList)
        {
            if(button.text == "")
                return false
        }
        return true
    }

    private fun addToBoard(button: Button) {
        if(button.text != "")
            return

        if(currentTurn == Turn.NAUGHT)
        {
            button.text = "O"
            currentTurn = Turn.CROSS
        }
        else if(currentTurn == Turn.CROSS)
        {
            button.text = "X"
            currentTurn = Turn.NAUGHT
        }

        setTurnLable()
    }

    private fun setTurnLable() {
        var turnText = ""
        if(currentTurn == Turn.CROSS)
            turnText = "Turn X"
        else if(currentTurn == Turn.NAUGHT)
            turnText = "Turn O"

        binding.turnTV.text = turnText
    }
}