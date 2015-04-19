/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import acm.program.ConsoleProgram;

/**
 *
 * @author kathrynhodge
 */
public class TicTacToeApplication extends ConsoleProgram {
 
    private TicTacToe newGame = new TicTacToe(getConsole());
    private MonteCarloAI ai = new MonteCarloAI();
    
    public void run(){
        // The game runs here....
        println("Welcome to Tic Tac Toe! You are about to go against"
                + " the master of Tic Tac Toe. Are you ready? I hope"
                + " so! To play, enter a row number and column number,"
                + " and your token shall be put in its place. The rows go from "
                + "0 - 2 and the columns go from 0 - 2. You are"
                + " the X's and I am the O's. We shall see who will win"
                + " this round.");
        newGame.printBoard(newGame.board);
        while (newGame.gameOver(newGame.board).equals("notOver")) {
            // We want to cycle through the turns of user and AI
         
            // User Input!
            // Getting User's Row
            Integer userRow;
            Integer userCol;
            Integer r = 0;
            do {
                println();
                if (r != 0) {
                    println("That spot is already taken! Try again!");
                } else {
                    println("It's your turn!");
                }
                println();
                // Getting User Row
                println("Enter Row Number:");
                userRow = stringToIntCheck(newGame);

                // Getting User's Col
                println("Enter Column Number: ");
                userCol = stringToIntCheck(newGame);
                r++;
            } while /* Get new stuff if the spot is taken */ (newGame.isSpotTaken(userRow, userCol, newGame.board));

            // Play User's Turn
            newGame.playTurn(userRow, userCol, TicTacToe.userMarker, newGame.board);

            // Print board
            
            newGame.printBoard(newGame.board);

            // Check for Winning Condition
            if (!newGame.gameOver(newGame.board).equals("notOver")) {
                newGame.printBoard(newGame.board);
                println(newGame.gameOver(newGame.board));
                return;
            }
            println("It's my turn!");
            
            // AI Monte Carlo Simulation that gives back array of row and col
            
            // AI picks where it want to go
            int[] rowCol = ai.pickSpot(newGame);
                    
            // AI play turn
            newGame.playTurn(rowCol[0], rowCol[1], TicTacToe.aiMarker, newGame.board);
            
            newGame.printBoard(newGame.board);
            println("I picked " + rowCol[0] + " : " + rowCol[1]);
        }
        println(newGame.gameOver(newGame.board));

    }

    public int stringToIntCheck(TicTacToe newGame) {
        String userInput = readLine().trim();
        while (!newGame.withinRange(Integer.valueOf(userInput))) {
            println("Sorry, that's not a good row number. Try again");
            userInput = readLine().trim();
        }
        return Integer.valueOf(userInput);
    }

    public static void main(String[] args) {
        new TicTacToeApplication().start();

    }
}
