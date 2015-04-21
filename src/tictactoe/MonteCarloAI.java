/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.util.Random;

/**
 *
 * @author kathrynhodge
 */
class MonteCarloAI {

    private int sizeOfBoard = 9;
    private int lengthOfRow = 3;
    private int lengthOfCol = 3;
    private Random rand = new Random();

    private final int numberOfSimulations = 2500;
    //^ the lower == computer more dumb
    private char NONE = '-';
    private char USER = 'X';
    private char AI = 'O';

    // Because we want the winners to stand out from the losers
    private int WIN_PTS = 100;
    private int LOSE_PTS = -1;
    private int DRAW_PTS = 0;
    private int MIN_PTS = -1000000000;
    private int NO_SPOT = -100;

    public MonteCarloAI() {

    }

    // What do we need this AI to do?
    // -we need it to know what spots are avaliable.
    // -then we need it to run simulations
    // -then we need it to have an observation about that simulation
    // -then we need it to pick a spot
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
    
    
    public int pickSpot(TicTacToe game){
        // add one because when we play turns, we want it to look to be 1-9 based
        return this.runRandomSimulations(game) + 1;
    }

    public int chooseRandomSpot(char[] board) {
        int[] spotAvaliable = new int[9];
        int index = 0;
        for (int i = 0; i < sizeOfBoard; i++) {
                if (board[i] == this.NONE) {
                    spotAvaliable[index++] = i;
                }
            }
        
        if (index == 0) {
            //System.out.println("no spot avaliable");
            return this.NO_SPOT; // no spot avaliable
        }
       // System.out.println("Spots avaliable" + index);

        return spotAvaliable[rand() % index];
    }

    public int rand() {
        return Math.abs(rand.nextInt());
    }
 
    // KEY TO THE SIMULATIONS/AI:
    public int runRandomSimulations(TicTacToe game){
        int[] winPoints = new int[9];
        int[] goodSpots = new int[9];
        char[] tempBoard = game.setSimulationBoard();
        int goodSpotIndex = 0;
        boolean isThereAFreeSpace = false;
        int firstMove;
        // Where is there a good space? Setting it up.
        for (int i = 0; i < sizeOfBoard; i++) {
                if (tempBoard[i] == '-') {
                    isThereAFreeSpace = true;
                    winPoints[i] = 0;
                } else {
                    // So we don't pick unavaliable spots
                    winPoints[i] = this.MIN_PTS;
                }
            
        }

        // JUST IN CASE
        if (!isThereAFreeSpace) {
            throw new RuntimeException("There aren't any free spaces!");
        }
        
        

        //play it out yoo...
        for (int j = 0; j < this.numberOfSimulations; j++) {
            // set to -1 so we can track what the first move is
            firstMove = -1;
            int flipper = 0;
            // Play a game...
            while (game.gameOver(tempBoard).equals("notOver")) {
               // find a spot
                int spot = this.chooseRandomSpot(tempBoard);
                
                if (spot == this.NO_SPOT /* meaning there was no spot avaliable */) {
                     game.winner = this.NONE;
                     // we are done with this game. 
                     break;
                 }
               
                // The computer is basically playing the user and the comp
                // But we just take one side.
                char marker = (flipper % 2 == 0) ? TicTacToe.aiMarker : TicTacToe.userMarker;
                // the spot
                // add one because when we play turns, we want it to look to be 1-9 based
                game.playTurn(spot + 1, marker, tempBoard);
                
                // take note if it's the first position
                if (firstMove == -1) {
                    firstMove = spot;
                }
                flipper++;
            }
            
            // Who won with this first move?
            // Dealing points appropriately
            if (game.winner != this.NONE) {
                if (game.winner == this.AI) {
                    winPoints[firstMove] += this.WIN_PTS;
                } else {
                    winPoints[firstMove] += this.LOSE_PTS;
                }
            } else {
                winPoints[firstMove] += this.DRAW_PTS;
            }
            // Reseting the game for the next simulation
            tempBoard = game.setSimulationBoard();
        }
        
        game.resetForBackToGamePlay();
        // After running through all of stimulations...
        System.out.println();
        System.out.println("Points Per Spot: ");
            int max = this.MIN_PTS;
            // Finding spot with max points = Where AI should go
            for (int k = 0; k < this.sizeOfBoard; k++) {
                if (max < winPoints[k]) {
                    max = winPoints[k];
                }
                System.out.println((k+1) + " : " + winPoints[k]);
            }

            // Could have multiple good candidates
            // Creating choices for candidates
            for (int b = 0; b < this.sizeOfBoard; b++) {
                if (winPoints[b] == max) {
                    goodSpots[goodSpotIndex++] = b;
                    System.out.println("Best Spot = " + (b+1));
                }
            }
            
            System.out.println();
            
            // Randomly choose a good one
            return goodSpots[rand() % goodSpotIndex];

        

    }

}

