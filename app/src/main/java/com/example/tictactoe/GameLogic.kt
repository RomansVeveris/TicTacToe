package com.example.tictactoe

import android.app.AlertDialog
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

fun checkForWin(buttonsArray: Array<Button?>, move: Boolean): Boolean {
    val buttonTextArray = Array<String?>(buttonsArray.size) { null }

    // fills the buttonTextArray with the text values of the buttons
    for (index in buttonsArray.indices) {
        val button = buttonsArray[index]
        buttonTextArray[index] = button?.text.toString()
    }

    // winning combinations
    val winningCombinations = listOf(
        listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8), // Rows
        listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8), // Columns
        listOf(0, 4, 8), listOf(2, 4, 6) // Diagonals
    )

    // check for a win
    for (combination in winningCombinations) {
        val values = combination.map { buttonTextArray[it] }
        if (values.all { it == "X" } && move) {
            return true // Player 1 (X) wins
        } else if (values.all { it == "O" } && !move) {
            return true // Player 2 (O) wins
        }
    }
    return false
}


// if every cell is filled with a value (X or O) but there are no winning conditions, then game state is tie
fun checkForTie(buttonTextArray: Array<String?>): Boolean {
    return !buttonTextArray.contains("")
}
// winning notification
fun showWinDialog(activity: AppCompatActivity, winner: String) {
    val builder = AlertDialog.Builder(activity)
    builder.setTitle("Game Over")
    builder.setMessage("$winner wins!")

    builder.setPositiveButton("OK") { dialog, _ ->
        dialog.dismiss()
    }
    val dialog = builder.create()
    dialog.show()
}
// tie notification
fun showTieDialog(activity: AppCompatActivity) {
    val builder = AlertDialog.Builder(activity)
    builder.setTitle("Game Over")
    builder.setMessage("Tie")

    builder.setPositiveButton("OK") { dialog, _ ->
        dialog.dismiss()
    }
    val dialog = builder.create()
    dialog.show()
}
