import java.util.Random;

/**
 * WhateverPlayer class that implements Player interface
 */
public class WhateverPlayer implements Player
{

    /**
     * This method mediates a tictactoe game turn of the WhateverPlayer. The method implements a strategy
     * randomly picking coordinates until encountering a blank spot and placing a mark there
     * @param board a Board type object on which the relevant game is executed
     * @param mark the mark type to be placed on board by CleverPlayer
     */
    public void playTurn(Board board, Mark mark)
    {

        Random rand = new Random();
        int row = rand.nextInt(Board.SIZE )  ;
        int col = rand.nextInt(Board.SIZE )  ;
        while (!board.putMark(mark, row , col) )
        {
            row = rand.nextInt(Board.SIZE )  ;
            col = rand.nextInt(Board.SIZE )  ;
        }
    }

}
