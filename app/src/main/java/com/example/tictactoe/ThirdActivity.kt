package com.example.tictactoe
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat

class ThirdActivity : AppCompatActivity() {
    // these variables are defined there, so its possible to use them in other functions
    private lateinit var buttonsArray: Array<Button?>
    private lateinit var button5: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)
        // getting user names from EnteringNames.kt
        val player1Name = intent.getStringExtra("PLAYER1_NAME")
        val player2Name = intent.getStringExtra("PLAYER2_NAME")
        // creating a grid where buttons will be placed
        val gridLayout = findViewById<GridLayout>(R.id.gridLayout)
        val textView: TextView = findViewById(R.id.textView3)
        textView.text = "$player1Name's move"
        button5 = findViewById(R.id.button5)
        buttonsArray = Array<Button?>(9) { null }

        // restart button that becomes visible after game is ended
        button5.setOnClickListener {
            val intent = Intent(this, ThirdActivity::class.java)
            intent.putExtra("PLAYER1_NAME", player1Name)
            intent.putExtra("PLAYER2_NAME", player2Name)
            // move = false is needed because if X wins and game is restarted, move variable will have wrong value
            // so player1 will put 'O' instead of 'X' and player2 will put 'X' instead of 'O'
            move = false
            // start the new instance of the activity and finish the current one
            startActivity(intent)
            finish()
        }
        // creating 3x3 grid of buttons and adding them to the gridLayout
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                val cell = Button(this)
                cell.layoutParams = GridLayout.LayoutParams().apply {
                    width = 0
                    height = GridLayout.LayoutParams.WRAP_CONTENT
                    columnSpec = GridLayout.spec(j, 1f)
                    rowSpec = GridLayout.spec(i, 1f)
                }
                cell.textSize = 80f

                gridLayout.addView(cell)
                buttonsArray[i * 3 + j] = cell
            }
        }

        val buttonBackgroundColor = ContextCompat.getColor(this, R.color.light_gray)

        for (i in 0 until gridLayout.childCount) {
            val child = gridLayout.getChildAt(i)
            if (child is Button) {
                child.setBackgroundColor(buttonBackgroundColor)
                child.setOnClickListener {
                    handleCellClick(child, textView, player1Name, player2Name)

                    // this line is used to prevent users to replace already existing values in a cell (button)
                    // for example if 'X' is placed in a cell, second player cannot replace this cells 'X' value to 'O'
                    child.setOnClickListener(null)
                }

                child.setOnLongClickListener {
                    child.text = ""

                    // move = !move is used when the player put his value in wrong cell and wants to delete it with hold
                    // this line keeps players move after removing value from a cell
                    move = !move
                    child.setOnClickListener {
                        handleCellClick(child, textView, player1Name, player2Name)
                        child.setOnClickListener(null)
                    }
                    true
                }
            }
        }
    }
    // at the beginning I wanted to put images (X's and O's) into the buttons instead of text, but somehow it didn't work properly
    private fun handleCellClick(cell: Button, textView: TextView, player1Name: String?, player2Name: String?) {
        if (!move) {
            // player2Name is used instead of player1Name because player1 puts 'X' value on his move
            // and game has to show who needs to make NEXT move (that is player2)
            textView.text = "$player2Name's move"
            cell.text = "X"
            cell.setTextColor(ContextCompat.getColor(this, R.color.X_color))
            // move is used to define what player makes a move
            move = true
        } else {
            textView.text = "$player1Name's move"
            cell.text = "O"
            cell.setTextColor(ContextCompat.getColor(this, R.color.O_color))
            move = false
        }
        // after each move it checks if the game is ended or not
        if (checkForWin(buttonsArray, move)) {
            if (player1Name != null && player2Name != null) {
                showWinDialog(this, if (move) player1Name else player2Name)
            }
            // when the game is ended, there appears a button to restart the game
            button5.visibility = View.VISIBLE
            disableAllButtons()
        } else if (checkForTie(buttonsArray.map { it?.text.toString() }.toTypedArray())) {
            showTieDialog(this)
            button5.visibility = View.VISIBLE
            disableAllButtons()
        }
    }
    // when the game is ended (win or tie) all buttons are disabled, so user cannot continue the game
    private fun disableAllButtons() {
        for (button in buttonsArray) {
            button?.isEnabled = false
        }
    }
    // move is declared in the companion object, it can be accessed directly using the class name ThirdActivity.move from anywhere within the ThirdActivity class or its companion objects.
    companion object {
        var move = false
    }
}
