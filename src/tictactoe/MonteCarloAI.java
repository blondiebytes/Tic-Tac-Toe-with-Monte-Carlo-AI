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

    private final int numberOfSimulations = 10000;
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
    // FOR THIS SIMULATION:
    //  0 | 1 | 2
    // --------------------
    //  3 | 4 | 5
    // --------------------
    //  6 | 7 | 8
    //    |  0  |  1  |  2  
    // --------------------
    //  0 | 0:0 | 0:1 | 0:2
    // --------------------
    //  1 | 1:0 | 1:1 | 1:2
    // --------------------
    //  2 | 2:0 | 2:1 | 2:2
    public int[] pickSpot(TicTacToe game){
        return this.conversionToRowColSpot(this.runRandomSimulations(game));
    }

    public int chooseRandomSpot(char[][] board) {
        int[] spotAvaliable = new int[9];
        int index = 0;

        for (int i = 0; i < lengthOfRow; i++) {
            for (int j = 0; j < lengthOfCol; j++) {
                if (board[i][j] == this.NONE) {
                    // add to spot avaliable
                    int spot = conversionToNumericalSpot(i, j);
                    spotAvaliable[index++] = spot;
                }
            }
        }
        if (index == 0) {
            return this.NO_SPOT; // no spot avaliable
        }

        return spotAvaliable[rand() % index];
    }

    public int rand() {
        return Math.abs(rand.nextInt());
    }

    public int conversionToNumericalSpot(int i, int j) {
        switch (i) {
            case 0:
                return j;
            case 1:
                return j + 3;
            case 2:
                return j + 6;
            default:
                return 10000; // SHOULD NEVER HAPPEN
        }
    }

    public int[] conversionToRowColSpot(int num) {
        int[] rowCol = new int[2];
        int row = 0;
        int col = 0;
        switch (num) {
            case 0:
                break;
            case 1:
                col = 1;
                break;
            case 2:
                col = 2;
                break;
            case 3:
                row = 1;
                break;
            case 4:
                row = 1;
                col = 1;
                break;
            case 5:
                row = 1;
                col = 2;
                break;
            case 6:
                row = 2;
                break;
            case 7:
                row = 2;
                col = 1;
                break;
            default:
                row = 2;
                col = 2;
                break;
        }
        rowCol[0] = row;
        rowCol[1] = col;
        return rowCol;
    }

    // KEY TO THE SIMULATIONS/AI:
    public int runRandomSimulations(TicTacToe game){
        int[] winPoints = new int[9];
        int[] goodSpots = new int[9];
        char[][] tempBoard = game.setSimulationBoard();
        int goodSpotIndex = 0;
        boolean isThereAFreeSpace = false;
        int firstMove;
        // Where is there a good space? Setting it up.
        for (int i = 0; i < lengthOfRow; i++) {
            for (int j = 0; j < lengthOfCol; j++) {
                if (game.board[i][j] == '-') {
                    isThereAFreeSpace = true;
                    winPoints[conversionToNumericalSpot(i, j)] = 0;
                } else {
                    // So we don't pick unavaliable spots
                    winPoints[conversionToNumericalSpot(i, j)] = this.MIN_PTS;
                }
            }
        }

        // JUST IN CASE
        if (!isThereAFreeSpace) {
            throw new RuntimeException("There aren't any free spaces!");
        }

        //play it out yoo
        for (int j = 0; j < this.numberOfSimulations; j++) {
            // set to -1 so we can track what the first move is
            firstMove = -1;
            
            // Play a game...
            while (game.gameOver(tempBoard).equals("notOver")) {
                // The computer is basically playing the user and the comp
                // But we just take one side.
                int spot = this.chooseRandomSpot(tempBoard);
                if (spot == this.NO_SPOT /* meaning there was no spot avaliable */) {
                    game.winner = this.NONE;
                    // we are done with this game. 
                    break;
                }
                // play the randomly choosen position
                int[] position = this.conversionToRowColSpot(spot);
                char marker = (j % 2 == 0) ? TicTacToe.aiMarker : TicTacToe.userMarker;
                
                game.playTurn(position[0], position[1], marker, tempBoard);
                // take note if it's the first position
                if (firstMove == -1) {
                    firstMove = spot;
                }
            }
            // Who won with this first move?
            // Dealing points appropriately
            if (game.winner != this.NONE) {
                if (game.winner == this.USER) {
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
                System.out.println(k + " : " + winPoints[k]);
            }

            // Could have multiple good candidates
            // Creating choices for candidates
            for (int b = 0; b < this.sizeOfBoard; b++) {
                if (winPoints[b] == max) {
                    goodSpots[goodSpotIndex++] = b;
                    System.out.println("Best Spot = " + b);
                }
            }
            
            System.out.println();
            
            // Randomly choose a good one
            return goodSpots[rand() % goodSpotIndex];

        

    }

}

