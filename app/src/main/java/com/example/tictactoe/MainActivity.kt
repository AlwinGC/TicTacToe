package com.example.tictactoe

import android.content.Intent
import android.view.WindowInsetsController
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideSystemBars()
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        val buttonClick = findViewById<Button>(R.id.btnPlay)
        buttonClick.setOnClickListener {
            val intent = Intent(this, player_setup::class.java)
            startActivity(intent)
        }

        val buttonExit = findViewById<Button>(R.id.btnExit)
        buttonExit.setOnClickListener {
            exitProcess(-1)
        }

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