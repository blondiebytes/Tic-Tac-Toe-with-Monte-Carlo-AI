package tictactoe;
// What do we need to make Tic Tac Toe:
// Game Logic --> How do we win; how do we lose

import acm.io.IOConsole;
import acm.program.ConsoleProgram;

// VIA gameOver method that checks things

    // Controls --> How do we play? How do we enter X's and O's
// VIA terminal/entering in the console
    // Monte Carlo AI -> How does the AI know what to do next?
// VIA probabilities + monte carlo method of running many and many 
// scenarios, observing the results, and then choosing where to go
    // What will we need?
// - A class managing the AI components -> how we run all the 
//   simulations, observe results, etc. (USING Heuristics & Probabilities)
// - A class that contains the logic of the game 
//   (onTick/update, react, display)
// - A custom class (or some type of data structure) that stores the current
//   state of the game

// Picture of Game with Index:
//    |  0  |  1  |  2  
// --------------------
//  0 | 0:0 | 0:1 | 0:2
// --------------------
//  1 | 1:0 | 1:1 | 1:2
// --------------------
//  2 | 2:0 | 2:1 | 2:2
//
//
// UI Picture of Game:
// INIT:
// | - | - | -
// ------------
// | - | - | -
// ------------
// | - | - | -
// GAMEPLAY:
// | O | - | O
// ------------
// | - | X | -
// ------------
// | - | - | X

public class TicTacToe extends ConsoleProgram {

    final private char[][] board;
    private char userMarker;
    private char aiMarker;
    private char currentMarker;
    private IOConsole console;
    private char winner;


    public TicTacToe(IOConsole console, char userMarker, char aiMarker) {
        board = new char[3][3];
        // board[0].length for length of row
        // board.length for length of col
        for (int i = 0; i < board[0].length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = '-';
            }
        }
        this.userMarker = userMarker;
        this.aiMarker = aiMarker;
        this.currentMarker = userMarker;
        this.console = console;
        this.winner = '-';
    }
    
    // TRUE = user
    // FALSE = AI
    
    public boolean whoseTurn() {
        return currentMarker == userMarker;
    }

    public void playTurn(int row, int col) {
        if (row >= 0 && row < board.length && col >= 0 && col < board.length) {
                this.changeMarker();
                board[row][col] = currentMarker;
        }
    }
    
    // check if spot is in range
    public boolean withinRange(int number) {
        return number >= 0 && number < board.length;
    }
   
    // check if spot isn't taken
    public boolean isSpotTaken(int row, int col) {
       return board[row][col] != '-';
    }

    public void changeMarker() {
        if (currentMarker == userMarker) {
            currentMarker = aiMarker;
        } else {
            currentMarker = userMarker;
        }
    }

    public void printBoard() {
        // Attempting to create...
        // | - | - | -
        // ------------
        // | - | - | -
        // ------------
        // | - | - | -
        String[] rows = new String[3];
        for (int i = 0; i < board[0].length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (rows[i] == null) {
                    rows[i] = "";
                }
                rows[i] = rows[i] + " | " + board[j][i];
            }
        }
        console.println();
        for (String s : rows) {
            console.println(s);
            console.println("--------------");
        }
        console.println();
        
    }

    public boolean isThereAWinner() {
        // winning conditions
        boolean diagonalsAndMiddles = rightDi() || leftDi() || middleRow() || secondCol();
        boolean topAndFirst = topRow() || firstCol();
        boolean bottomAndThird = bottomRow() || thirdCol();
        if (diagonalsAndMiddles) {
            this.winner = board[1][1];
        } else if (topAndFirst){
            this.winner = board[0][0];
        } else if (bottomAndThird) {
            this.winner = board[2][2];
        }
        
        return diagonalsAndMiddles || topAndFirst || bottomAndThird;
      
    }
    
    public boolean topRow() {
        return board[0][0] == board[0][1] && board[0][1] == board[0][2] && board[0][1] != '-';
    }
    
    public boolean middleRow() {
        return board[1][0] == board[1][1] && board[1][1] == board[1][2] && board[1][1] != '-';
    }
    
    public boolean bottomRow() {
        return board[2][0] == board[2][1] && board[2][1] == board[2][2] && board[2][1] != '-';
    }
    
    public boolean firstCol() {
        return board[0][0] == board[1][0] && board[1][0] == board[2][0] && board[1][0] != '-';
    }
    
    public boolean secondCol() {
        return board[0][1] == board[1][1] && board[1][1] == board[2][1] && board[1][1] != '-';
    }
    
    public boolean thirdCol() {
        return board[0][2] == board[1][2] && board[1][2] == board[2][2] && board[1][2] != '-';
    }
    
    public boolean rightDi(){
        return board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[1][1] != '-';
    }
    
    public boolean leftDi() {
        return board[0][2] == board[1][1] && board [1][1] == board[2][0] && board[1][1] != '-';
    }
    
    
    
    public boolean isTheBoardFilled() {
        for (int i = 0; i < board[0].length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }
    
   public String gameOver() {
        if (isTheBoardFilled()) {
            return "Draw: Game Over!";
        } else if (isThereAWinner()) {
            return "We have a winner! The winner is '" + this.winner +"'s";
        } else {
            return "notOver";
        }
    }

}
