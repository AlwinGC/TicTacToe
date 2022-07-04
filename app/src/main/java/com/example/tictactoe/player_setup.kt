package com.example.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsetsController.BEHAVIOR_DEFAULT
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.tictactoe.databinding.PlayerSetupBinding

class player_setup : AppCompatActivity() {

    private lateinit var binding: PlayerSetupBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideSystemBars()
        supportActionBar?.hide()
        binding = PlayerSetupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val buttonClick = findViewById<Button>(R.id.btnStart)
        buttonClick.setOnClickListener {
            if(valiadteEditTexts()){

                val player1 = binding.etPlayer1.text.toString().trim()
                val player2 = binding.etPlayer2.text.toString().trim()

                val intent = Intent(this, game_display::class.java)
                intent.putExtra("Player1", player1)
                intent.putExtra("Player2", player2)
                finish()
                startActivity(intent)
            }
            else{
                AlertDialog.Builder(this)
                    .setMessage(R.string.errorEnterName)
                    .setPositiveButton(R.string.close)
                    {_,_ ->
                        binding.etPlayer1.text.clear()
                        binding.etPlayer2.text.clear()
                    }
                    .show()
            }
        }

    }

    private fun valiadteEditTexts(): Boolean {
        if(binding.etPlayer1.text.toString().trim() == "") return false
        if(binding.etPlayer2.text.toString().trim() == "") return false
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


}