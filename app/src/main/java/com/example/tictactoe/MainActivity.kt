package com.example.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val board = Array(3) { arrayOfNulls<String>(3) }
    private var isPlayerX = true
    private var gameActive = true

    private lateinit var gameStatus: TextView
    private lateinit var playAgainButton: Button
    private lateinit var gameGrid: GridLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gameStatus = findViewById(R.id.gameStatus)
        playAgainButton = findViewById(R.id.playAgainButton)
        gameGrid = findViewById(R.id.gameGrid)

        for (i in 0 until gameGrid.childCount) {
            val row = i / 3
            val col = i % 3
            val cell = gameGrid.getChildAt(i) as Button
            cell.setOnClickListener {
                makeMove(cell, row, col)
            }
        }

        playAgainButton.setOnClickListener {
            resetGame()
        }
    }

    private fun makeMove(cell: Button, row: Int, col: Int) {
        if (!gameActive || board[row][col] != null) return

        board[row][col] = if (isPlayerX) "X" else "O"
        cell.text = board[row][col]

        when {
            checkWinner() -> {
                gameStatus.text = "${if (isPlayerX) "X" else "O"} wins!"
                gameActive = false
                playAgainButton.visibility = View.VISIBLE
            }
            isBoardFull() -> {
                gameStatus.text = "It's a draw!"
                gameActive = false
                playAgainButton.visibility = View.VISIBLE
            }
            else -> {
                isPlayerX = !isPlayerX // Switch turn
                gameStatus.text = "${if (isPlayerX) "X" else "O"} Play"
            }
        }
    }

    private fun checkWinner(): Boolean {
        for (i in 0..2) {
            if (board[i][0] != null && board[i][0] == board[i][1] && board[i][1] == board[i][2]) return true
            if (board[0][i] != null && board[0][i] == board[1][i] && board[1][i] == board[2][i]) return true
        }

        if (board[0][0] != null && board[0][0] == board[1][1] && board[1][1] == board[2][2]) return true
        if (board[0][2] != null && board[0][2] == board[1][1] && board[1][1] == board[2][0]) return true

        return false
    }

    private fun isBoardFull(): Boolean {
        for (row in board) {
            for (cell in row) {
                if (cell == null) return false
            }
        }
        return true
    }

    private fun resetGame() {
        for (i in 0 until gameGrid.childCount) {
            val cell = gameGrid.getChildAt(i) as Button
            cell.text = ""
        }

        for (i in board.indices) {
            for (j in board[i].indices) {
                board[i][j] = null
            }
        }

        isPlayerX = true
        gameActive = true
        gameStatus.text = "X Play"
        playAgainButton.visibility = View.GONE
    }
}
