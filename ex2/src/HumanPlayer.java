import java.util.Scanner;
/**
 * HumanPlayer class that implements Player interface
 * enables users to participate in the game
 */
public class HumanPlayer implements Player
{
    /**
     * This method mediates a tictactoe game turn of the HumanPlayer. The method gets coordinate input from the user and
     * places a mark there if the coordinate is blank and within range otherwise it waits until valid input is submitted
     * @param board a Board type object on which the relevant game is executed
     * @param mark the mark type to be placed on board by CleverPlayer
     */
    public void playTurn(Board board , Mark mark)
    {
        Scanner in = new Scanner(System.in);
        int coord = in.nextInt();
        int row = (coord / 10 ) - 1 ;
        int col = (coord % 10) - 1 ;
        while (!board.putMark(mark, row , col) )
        {
            coord = in.nextInt();
            row = (coord / 10 ) - 1 ;
            col = (coord % 10) - 1 ;
        }
    }
}
