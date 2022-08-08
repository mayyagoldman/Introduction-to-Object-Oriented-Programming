import  java.util.Scanner;

public class Player
{
    public Player() {} ;
    public void playTurn(Board board , Mark mark)
    {
        Scanner in = new Scanner(System.in);
        int num = in.nextInt();
        int row = (num / 10 ) - 1 ;
        int col = (num % 10) - 1 ;
        while (!board.putMark(mark, row , col) )
        {
            num = in.nextInt();
            row = (num / 10 ) - 1 ;
            col = (num % 10) - 1 ;
        }


    }
}
