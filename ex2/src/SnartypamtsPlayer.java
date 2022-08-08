/**
 * SnartypamtsPlayer class that implements Player interface
 */
public class SnartypamtsPlayer implements Player
{
    /**
     * This method mediates a tictactoe game turn of the SnartypamtsPlayer. The method implements a strategy
     * of scanning the board col by col starting from the second  and placing the mark at the first available spot.
     * @param board a Board type object on which the relevant game is executed
     * @param mark the mark type to be placed on board by CleverPlayer
     */
    public void playTurn(Board board , Mark mark) {
        for (int col = 1 ; col < board.SIZE ; col ++)
        {
            for (int row = 0 ; row < board.SIZE ; row ++)
            {
                if (board.getMark(row , col) == Mark.BLANK)
                {
                    board.putMark(mark,row ,col);
                    return;
                }
            }
        }
    }
}
