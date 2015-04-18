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
    
    private int numberOfSimulations = 2500; 
    //^ the lower == computer more dumb
    
    
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
    
    public int[] pickSpot(TicTacToe game) {
        return this.runRandomSimulations(game);
    }
    
   
    public int chooseRandomSpot(TicTacToe game) {
        int[] spotAvaliable = new int[9];
        int index = 0;
        
        for (int i = 0; i < lengthOfRow; i++) {
            for (int j = 0; j < lengthOfCol; j++) {
                if (game.board[i][j] == '-') {
                    // add to spot avaliable
                    int spot = conversionToNumericalSpot(i, j);
                    spotAvaliable[index++] = spot;
                }
            }
        }
        if (index == 0) {
            throw new RuntimeException("There were no spots avaliable for the ai");
        }
        
        return spotAvaliable[rand()%index];
    }
    
    public int rand() {
        return rand.nextInt();
    }
    
    
    public int conversionToNumericalSpot(int i, int j) {
        switch(i) {
            case 0: 
                return j;
            case 1:
                return j+3;
            case 2:
                return j+6;
            default: return 10000; // SHOULD NEVER HAPPEN
        }
    }
    
    public int[] conversionToRowColSpot(int num) {
        int[] rowCol = new int[2];
        int row = 0;
        int col = 0;
        switch(num) {
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
    
    // KEY TO THE SIMULATIONS:
    
    // What will this do?
    // This will run many simulations of the game
    // It will call:
    // --chooseRandomSpot
    // -- AT THE VERY END -- Conversion to Row Col Spot
    // Will need to keep track of -> number of simulations
    
    public int[] runRandomSimulations(TicTacToe game) {
        int simulationsLeft = numberOfSimulations;
        chooseRandomSpot();
    }
    
        
      
            
            
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
