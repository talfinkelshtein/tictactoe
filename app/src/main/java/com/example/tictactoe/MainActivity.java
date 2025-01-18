package com.example.tictactoe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private String[][] board = new String[3][3]; // Represents the game board
    private boolean isPlayerX = true; // X starts first
    private boolean gameActive = true;

    private TextView gameStatus;
    private Button playAgainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameStatus = findViewById(R.id.gameStatus);
        playAgainButton = findViewById(R.id.playAgainButton);
        GridLayout gameGrid = findViewById(R.id.gameGrid);

        // Initialize grid buttons
        for (int i = 0; i < gameGrid.getChildCount(); i++) {
            final int row = i / 3;
            final int col = i % 3;
            Button cell = (Button) gameGrid.getChildAt(i);
            cell.setOnClickListener(view -> makeMove(cell, row, col));
        }

        // Reset the game
        playAgainButton.setOnClickListener(view -> resetGame(gameGrid));
    }

    private void makeMove(Button cell, int row, int col) {
        if (!gameActive || board[row][col] != null) return; // Ignore invalid moves

        // Update the board and UI
        board[row][col] = isPlayerX ? "X" : "O";
        cell.setText(board[row][col]);

        // Check for winner
        if (checkWinner()) {
            gameStatus.setText((isPlayerX ? "X" : "O") + " wins!");
            gameActive = false;
            playAgainButton.setVisibility(View.VISIBLE);
        } else if (isBoardFull()) {
            gameStatus.setText("It's a draw!");
            gameActive = false;
            playAgainButton.setVisibility(View.VISIBLE);
        } else {
            isPlayerX = !isPlayerX; // Switch turn
            gameStatus.setText((isPlayerX ? "X" : "O") + " Play");
        }
    }

    private boolean checkWinner() {
        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != null && board[i][0].equals(board[i][1]) && board[i][1].equals(board[i][2])) return true;
            if (board[0][i] != null && board[0][i].equals(board[1][i]) && board[1][i].equals(board[2][i])) return true;
        }

        // Check diagonals
        if (board[0][0] != null && board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2])) return true;
        if (board[0][2] != null && board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0])) return true;

        return false;
    }

    private boolean isBoardFull() {
        for (String[] row : board) {
            for (String cell : row) {
                if (cell == null) return false;
            }
        }
        return true;
    }

    private void resetGame(GridLayout gameGrid) {
        // Clear the board
        for (int i = 0; i < gameGrid.getChildCount(); i++) {
            Button cell = (Button) gameGrid.getChildAt(i);
            cell.setText("");
            cell.setEnabled(true);
        }
        board = new String[3][3];
        isPlayerX = true;
        gameActive = true;
        gameStatus.setText("X Play");
        playAgainButton.setVisibility(View.GONE);
    }
}
