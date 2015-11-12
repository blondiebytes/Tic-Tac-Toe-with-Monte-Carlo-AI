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
    // FOR STORAGE:
    //  0 | 1 | 2
    // --------------------
    //  3 | 4 | 5
    // --------------------
    //  6 | 7 | 8
     // WHAT THE USER THINKS:
    //  1 | 2 | 3
    // --------------------
    //  4 | 5 | 6
    // --------------------
    //  7 | 8 | 9
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

    protected final char[] board;
    protected static final char userMarker = 'X';
    protected static final char aiMarker = 'O';
    protected IOConsole console;
    protected char winner;


    public TicTacToe(IOConsole console) {
        board = TicTacToe.setBoard();
        // board[0].length for length of row
        // board.length for length of col
        this.console = console;
        this.winner = '-';
    }
    
    public static char[] setBoard() {
        char[] board = new char[9];
        for (int i = 0; i < board.length; i++) {
            board[i] = '-';
            }
        return board;
    }
    
    // using a seperate board for simulations  
    public char[] setSimulationBoard() {
        char[] simulationBoard = new char[9];
        for (int i = 0; i < board.length; i++) {
                switch(board[i]) {
                    case '-': simulationBoard[i]= '-';
                        break;
                    case TicTacToe.userMarker: simulationBoard[i] = userMarker;
                        break;
                    case TicTacToe.aiMarker : simulationBoard[i] = aiMarker;
                        break;
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
    
    public void playTurn(int spot, char marker, char[] board) {
        if (spot >= 1 && spot < board.length + 1){
            board[spot-1] = marker;
        }
    }
    
    // check if spot is in range
    public boolean withinRange(int number) {
        return number > 0 && number < board.length + 1;
    }
   
    // check if spot isn't taken
    public boolean isSpotTaken(int spot, char[] board) {
        return board[spot-1] != '-';
    }

    public void printBoard(char[] board) {
        // Attempting to create...
        // | - | - | -
        // ------------
        // | - | - | -
        // ------------
        // | - | - | -
        
        console.println();
        for (int i = 0; i < board.length; i++) {
             if (i % 3 == 0 && i != 0) {
                console.println();
                console.println("-------------");
            }
            console.print(" | " + board[i]);
           
        }
        console.println();
        
    }
    
    public void printIndexBoard(char[] board) {
         console.println();
        for (int i = 0; i < board.length; i++) {
             if (i % 3 == 0 && i != 0) {
                console.println();
                console.println("-------------");
            }
            console.print(" | " + (i + 1));
           
        }
        console.println();
    }
    
   
    public boolean isThereAWinner(char[] board) {
        // winning conditions
        boolean diagonalsAndMiddles = (rightDi(board) || leftDi(board) || middleRow(board) || secondCol(board)) && board[4] != '-';
        boolean topAndFirst = (topRow(board) || firstCol(board)) && board[0] != '-';
        boolean bottomAndThird = (bottomRow(board) || thirdCol(board)) && board[8] != '-';
        if (diagonalsAndMiddles) {
            this.winner = board[4];
        } else if (topAndFirst){
            this.winner = board[0];
        } else if (bottomAndThird) {
            this.winner = board[8];
        }
        
        return diagonalsAndMiddles || topAndFirst || bottomAndThird;
      
    }
    
    public boolean topRow(char[] board) {
        return board[0] == board[1] && board[1] == board[2];
    }
    
    public boolean middleRow(char[] board) {
        return board[3] == board[4] && board[4] == board[5];
    }
    
    public boolean bottomRow(char[] board) {
        return board[6] == board[7] && board[7] == board[8];
    }
    
    public boolean firstCol(char[] board) {
        return board[0] == board[3] && board[3] == board[6];
    }
    
    public boolean secondCol(char[] board) {
        return board[1] == board[4] && board[4] == board[7];
    }
    
    public boolean thirdCol(char[] board) {
        return board[2] == board[5] && board[5] == board[8];
    }
    
    public boolean rightDi(char[] board){
        return board[0] == board[4] && board[4] == board[8];
    }
    
    public boolean leftDi(char[] board) {
        return board[2] == board[4] && board [4] == board[6];
    }
    
    
    public boolean isTheBoardFilled(char[] board) {
        for (int i = 0; i < board.length; i++) {
                if (board[i] == '-') {
                    return false;
                }
        }
        return true;
    }
    
   public String gameOver(char[] board) {
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
