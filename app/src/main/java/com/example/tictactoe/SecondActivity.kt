package com.example.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val button: Button = findViewById(R.id.button2)
        // only 2 player mode is implemented (PvC is not, so PvC button has no function)
        button.setOnClickListener {
            val intent = Intent(this, EnteringNames::class.java)
            startActivity(intent)
        }
    }
}