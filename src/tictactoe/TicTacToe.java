
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


// Picture of Game:

public class TicTacToe {
    final private char[][] board;
    private char marker;
    
    public TicTacToe(char userMarker) {
        board = new char[3][3];
        marker = userMarker;
    }
    
    public void playTurn(int row, int col) {
        // check if spot isn't taken
        if (!(this.isSpotTaken(row, col))) {
            board[row][col] = marker;
        }
    }
    
    public boolean isSpotTaken(int row, int col) {
        return (board[row][col] == 'X' || board[row][col] ==  'O');
    }
    
    public void changeMarker() {
        if (marker == 'X') {
            marker = 'O';
        } else 
            marker = 'X';
    }
    
    public void printBoard() {
        
    }
    
    public boolean isGameOver() {
        // winning conditions
        return false;
    }
    
    
    
    
    
    
    
    
    
    
    public static void main(String[] args) {
        // The game runs here....
    }
    
}
