package com.example.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class EnteringNames : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entering_names)

        // players need to enter their names before game
        // however if they don't type anything, they will have blank names (that doesn't affect the game process)
        // but it will be harder for players to identify what player won
        val player1EditText: EditText = findViewById(R.id.editTextText)
        val player2EditText: EditText = findViewById(R.id.editTextText2)
        val button: Button = findViewById(R.id.button4)

        button.setOnClickListener {
            val player1Name = player1EditText.text.toString()
            val player2Name = player2EditText.text.toString()

            // putExtra is used to use user entered names in ThirdActivity and to show their names later
            val intent = Intent(this, ThirdActivity::class.java)
            intent.putExtra("PLAYER1_NAME", player1Name)
            intent.putExtra("PLAYER2_NAME", player2Name)
            startActivity(intent)
        }
    }
}
