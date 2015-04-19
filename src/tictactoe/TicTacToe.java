package tictactoe;
// What do we need to make Tic Tac Toe:
// Game Logic --> How do we win; how do we lose

import acm.io.IOConsole;
import acm.program.ConsoleProgram;
import java.util.logging.Level;
import java.util.logging.Logger;

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

public class TicTacToe extends ConsoleProgram{

    protected final char[][] board;
    protected static final char userMarker = 'X';
    protected static final char aiMarker = 'O';
    private IOConsole console;
    protected char winner;


    public TicTacToe(IOConsole console) {
        board = TicTacToe.setBoard();
        // board[0].length for length of row
        // board.length for length of col
        this.console = console;
        this.winner = '-';
    }
    
    static public char[][] setBoard() {
        char[][] board = new char[3][3];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = '-';
                //System.out.println(i + " : " + j);
            }
        }
        return board;
    }
    
    // using a seperate board for simulations  
    public char[][] setSimulationBoard() {
        char[][] simulationBoard = new char[3][3];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                switch(board[i][j]) {
                    case '-': simulationBoard[i][j] = '-';
                        break;
                    case TicTacToe.userMarker: simulationBoard[i][j] = userMarker;
                        break;
                    case TicTacToe.aiMarker : simulationBoard[i][j] = aiMarker;
                        break;
                }
            }
        }
        this.winner = '-';
        return simulationBoard;
    }
    
    public void resetForBackToGamePlay() {
        this.winner = '-';
    }
    
    // TRUE = user
    // FALSE = AI

    // Everything takes in a board now so that we can test simulation boards
    // and the true boards easier.
    
    public void playTurn(int row, int col, char marker, char[][] board) {
        if (row >= 0 && row < board.length && col >= 0 && col < board.length){
                board[row][col] = marker;
        }
    }
    
    // check if spot is in range
    public boolean withinRange(int number) {
        return number >= 0 && number < board.length;
    }
   
    // check if spot isn't taken
    public boolean isSpotTaken(int row, int col, char[][] board) {
        return board[row][col] != '-';
    }

    public void printBoard(char[][] board) {
        // Attempting to create...
        // | - | - | -
        // ------------
        // | - | - | -
        // ------------
        // | - | - | -
        String[] rows = new String[3];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (rows[i] == null) {
                    rows[i] = "";
                }
                rows[i] = rows[i] + " | " + board[i][j];
            }
        }
        console.println();
        for (String s : rows) {
            console.println(s);
            console.println("--------------");
        }
        console.println();
        
    }
    
   
    public boolean isThereAWinner(char[][] board) {
        // winning conditions
        boolean diagonalsAndMiddles = rightDi(board) || leftDi(board) || middleRow(board) || secondCol(board);
        boolean topAndFirst = topRow(board) || firstCol(board);
        boolean bottomAndThird = bottomRow(board) || thirdCol(board);
        if (diagonalsAndMiddles) {
            this.winner = board[1][1];
        } else if (topAndFirst){
            this.winner = board[0][0];
        } else if (bottomAndThird) {
            this.winner = board[2][2];
        }
        
        return diagonalsAndMiddles || topAndFirst || bottomAndThird;
      
    }
    
    public boolean topRow(char[][] board) {
        return board[0][0] == board[0][1] && board[0][1] == board[0][2] && board[0][1] != '-';
    }
    
    public boolean middleRow(char[][] board) {
        return board[1][0] == board[1][1] && board[1][1] == board[1][2] && board[1][1] != '-';
    }
    
    public boolean bottomRow(char[][] board) {
        return board[2][0] == board[2][1] && board[2][1] == board[2][2] && board[2][1] != '-';
    }
    
    public boolean firstCol(char[][] board) {
        return board[0][0] == board[1][0] && board[1][0] == board[2][0] && board[1][0] != '-';
    }
    
    public boolean secondCol(char[][] board) {
        return board[0][1] == board[1][1] && board[1][1] == board[2][1] && board[1][1] != '-';
    }
    
    public boolean thirdCol(char[][] board) {
        return board[0][2] == board[1][2] && board[1][2] == board[2][2] && board[1][2] != '-';
    }
    
    public boolean rightDi(char[][] board){
        return board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[1][1] != '-';
    }
    
    public boolean leftDi(char[][] board) {
        return board[0][2] == board[1][1] && board [1][1] == board[2][0] && board[1][1] != '-';
    }
    
    
    public boolean isTheBoardFilled(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }
    
   public String gameOver(char[][]board) {
       boolean didSomeoneWin = isThereAWinner(board);
        if (didSomeoneWin) {
            return "We have a winner! The winner is '" + this.winner +"'s";
        } else if (isTheBoardFilled(board)) {
            this.winner = '-';
            return "Draw: Game Over!";
        } else {
            return "notOver";
        }
    }

  
}
