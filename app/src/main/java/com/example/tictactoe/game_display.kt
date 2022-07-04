package com.example.tictactoe

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.tictactoe.databinding.GameDisplayBinding


class game_display : AppCompatActivity() {

    enum class playerTurn{
        X,
        O
    }

    private var firstTurn = playerTurn.X
    private var currentTurn = playerTurn.X

    private var Xscore = 0
    private var Oscore = 0


    private var boardList = mutableListOf<ImageButton>()

    private lateinit var binding: GameDisplayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideSystemBars()
        supportActionBar?.hide()
        binding = GameDisplayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBoard()
        setTurnLabel()

    }


    private fun initBoard(){
        boardList.add(binding.btnRow1Col1)
        boardList.add(binding.btnRow1Col2)
        boardList.add(binding.btnRow1Col3)
        boardList.add(binding.btnRow2Col1)
        boardList.add(binding.btnRow2Col2)
        boardList.add(binding.btnRow2Col3)
        boardList.add(binding.btnRow3Col1)
        boardList.add(binding.btnRow3Col2)
        boardList.add(binding.btnRow3Col3)
    }

    private fun playerNames(s: String) : String {
        val intent = getIntent()
        val player1 = intent.getStringExtra("Player1")
        val player2 = intent.getStringExtra("Player2")
        return if(s == "player1"){
            player1.toString()
        } else if(s == "player2"){
            player2.toString()
        } else{
            ""
        }
    }

    private fun setTurnLabel(){
        var turnText = ""
        if(currentTurn == playerTurn.X){
            turnText= "${playerNames("player1")}'s Turn"
        }
        else if(currentTurn == playerTurn.O){
            turnText = "${playerNames("player2")}'s Turn"
        }
        binding.tvPlayerTurn.text= turnText
    }


   fun boardTapped(view: View) {

        if(view !is ImageButton)
            return
        addToBoard(view)
       
       if(checkForVictory("Osymbol")){
           Oscore++
           result("${playerNames("player2")} WINS")
       }else if(checkForVictory("Xsymbol")){
           Xscore++
           result("${playerNames("player1")} WINS")
       }else if(fullBoard()){
           result("DRAW MATCH")
       }
   }

   private fun addToBoard(button: ImageButton){
        if(button.tag != null) {
            return
        }
        val Osymbol = R.drawable.otictactoe
        val Xsymbol = R.drawable.xtictactoe

        if(currentTurn == playerTurn.O){
            button.setImageResource(Osymbol)
            button.tag = "Osymbol"
            currentTurn = playerTurn.X
        }
        else if(currentTurn == playerTurn.X){
            button.setImageResource(Xsymbol)
            button.tag = "Xsymbol"
            button.setBackgroundColor(Color.parseColor("#2f2f2f"))
            currentTurn = playerTurn.O
        }
        setTurnLabel()
   }

   private fun checkForVictory(btnTag: String): Boolean {
        //Horizontal checking
        if(matchingTag(binding.btnRow1Col1,binding.btnRow1Col2,binding.btnRow1Col3,btnTag))
            return true
        if(matchingTag(binding.btnRow2Col1,binding.btnRow2Col2,binding.btnRow2Col3,btnTag))
            return true
        if(matchingTag(binding.btnRow3Col1,binding.btnRow3Col2,binding.btnRow3Col3,btnTag))
            return true

        //Vertical checking
        if(matchingTag(binding.btnRow1Col1,binding.btnRow2Col1,binding.btnRow3Col1,btnTag))
            return true
        if(matchingTag(binding.btnRow1Col2,binding.btnRow2Col2,binding.btnRow3Col2,btnTag))
            return true
        if(matchingTag(binding.btnRow1Col3,binding.btnRow2Col3,binding.btnRow3Col3,btnTag))
            return true

        //Diagonal checking
        if(matchingTag(binding.btnRow1Col1,binding.btnRow2Col2,binding.btnRow3Col3,btnTag))
            return true
        if(matchingTag(binding.btnRow1Col3,binding.btnRow2Col2,binding.btnRow3Col1,btnTag))
            return true

        return false
   }

   // Function to check if the Image button has same tag
   private fun matchingTag(btn1: ImageButton, btn2: ImageButton, btn3: ImageButton, btnSymbol : String): Boolean {
       if(btn1.tag == btnSymbol && btn2.tag == btnSymbol && btn3.tag == btnSymbol){
           return true
       }
       return false
   }


   private fun result(title: String) {
        val message = "\n${playerNames("player1")} - $Xscore\n\n${playerNames("player2")} - $Oscore"
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setNegativeButton(R.string.home)
            {_,_ ->
                finish()
            }
            .setPositiveButton(R.string.reset)
            {_,_ ->
                resetBoard()
            }
            .setCancelable(false)
            .show()
   }

   // Function to reset the Tic Tac Toe Board
   private fun resetBoard(){
        for(button in boardList){
            button.setImageResource(0)
            button.tag = null
            button.setBackgroundColor(Color.parseColor("#0C0C0C"))
        }
        if(firstTurn == playerTurn.O)
            firstTurn = playerTurn.X
        else if(firstTurn == playerTurn.X)
            firstTurn = playerTurn.O

        currentTurn = firstTurn
   }

   // Function to check if the board is full
   private fun fullBoard(): Boolean {
        for(button in boardList){
            if(button.tag == null)
                return false
        }
        return true
   }


    private fun hideSystemBars() {
        val windowInsetsController =
            ViewCompat.getWindowInsetsController(window.decorView) ?: return
        // Configure the behavior of the hidden system bars
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        // Hide both the status bar and the navigation bar
        windowInsetsController.hide(WindowInsetsCompat.Type.statusBars())
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this).apply {
            setTitle(R.string.confirm)
            setMessage(R.string.leaveGame)

            setPositiveButton(R.string.yes) { _, _ ->
                super.onBackPressed()
            }

            setNegativeButton(R.string.no){_, _ ->

            }
            setCancelable(true)
        }.create().show()
    }




}