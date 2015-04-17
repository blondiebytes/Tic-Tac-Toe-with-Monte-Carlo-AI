package tictactoe;
// What do we need to make Tic Tac Toe:
// Game Logic --> How do we win; how do we lose
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

public class TicTacToe {

    final private char[][] board;
    private char userMarker;
    private char aiMarker;
    private char currentMarker;


    public TicTacToe(char userMarker, char aiMarker) {
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
        printBoard();
    }
    
    // TRUE = user
    // FALSE = AI
    
    public boolean whoseTurn() {
        return currentMarker == userMarker;
    }

    public String playTurn(int row, int col) {
        // check if spot is in range
        if (row >= 0 && row < board.length && col >= 0 && col < board.length) {
            // check if spot isn't taken
            if (this.isSpotNotTaken(row, col)) {
                this.changeMarker();
                board[row][col] = currentMarker;
                return "played turn";
            }
        }
        return "invalid input";
    }
   
    
    public boolean isSpotNotTaken(int row, int col) {
        return board[row][col] == '-';
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
        for (String s : rows) {
            System.out.println(s);
            System.out.println("--------------");
        }
        System.out.println();
        
    }

    public boolean isGameOver() {
        // winning conditions
        // create bools for all
        boolean topRow = board[0][0] == board[0][1] && board[0][1] == board[0][2] && board[0][1] != '-';
        boolean middleRow = board[1][0] == board[1][1] && board[1][1] == board[1][2] && board[1][1] != '-';
        boolean bottomRow = board[2][0] == board[2][1] && board[2][1] == board[2][2] && board[2][1] != '-';
        boolean firstCol = board[0][0] == board[1][0] && board[1][0] == board[2][0] && board[1][0] != '-';
        boolean secondCol = board[0][1] == board[1][1] && board[1][1] == board[2][1] && board[1][1] != '-';
        boolean thirdCol = board[0][2] == board[1][2] && board[1][2] == board[2][2] && board[1][2] != '-';
        boolean rightDi = board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[1][1] != '-';
        boolean leftDi = board[0][2] == board[1][1] && board [1][1] == board[2][0] && board[1][1] != '-';
        boolean checkConditions = topRow || middleRow || bottomRow || firstCol || secondCol || thirdCol || rightDi || leftDi;
        // ALSO NEED TO CHECK IF what we match is actually a winner -> not just '-'
        return checkConditions;
      
    }

    public static void main(String[] args) {
        // The game runs here....
        TicTacToe newGame = new TicTacToe('X', 'O');
        newGame.printBoard();
    }

}
