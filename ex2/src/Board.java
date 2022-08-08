/**
 * A tic tac toe Board class
 */
public class Board
{

    public static final int SIZE = 6;
    public static final int WIN_STREAK = 4 ;
    public enum GameStatus {O_WIN , X_WIN , DRAW , IN_PROGRESS}


    private GameStatus status;
    private Mark[][] board ;
    private int placedMarks ;
    private Mark winner;

    /**
     * Constructer Method of the Board class
     * initializes all fields to default values
     */
    public Board()
    {
        this.status = GameStatus.IN_PROGRESS;
        this.placedMarks = 0 ;
        this.winner = Mark.BLANK;
        this.board = new Mark[SIZE][SIZE] ;
        for (int i = 0 ; i < SIZE ; i ++)
        {
            for (int j = 0 ; j < SIZE ; j ++)
            {
                this.board[i][j] = Mark.BLANK;
            }
        }
    }

    /**
     * method confirming whether the mark placed at position (row,col) on board yields a winning streak in the
     * horizontal direction
     * @param row row coordinate of positioned mark
     * @param col column coordinate of positioned mark
     * @param mark type of placed mark
     * @return true if yields a streak , false otherwise
     */
    private boolean checkForStreakHorizontal( int row , int col , Mark mark )
    {
        int markCount = 1 ;
        for (int c = col + 1 ; c < col + WIN_STREAK ; c++)
        {
            if (c >= SIZE) {break;}
            if (getMark(row,c) != mark) {break;}
            markCount++ ;
            if (markCount == WIN_STREAK) {return true;}
        }

        for (int c = col - 1 ; c > col - WIN_STREAK ; c--)
        {
            if (c < 0) {break;}
            if (getMark(row,c) != mark) {break;}
            markCount++ ;
            if (markCount == WIN_STREAK) {return true ; }
        }
        
        return false;
    }
    /**
     * method confirming whether the mark placed at position (row,col) on board yields a winning streak in the
     * vertical direction
     * @param row row coordinate of positioned mark
     * @param col column coordinate of positioned mark
     * @param mark type of placed mark
     * @return true if yields a streak , false otherwise
     */
    private boolean checkForStreakVertical( int row , int col , Mark mark)
  
    {
        int markCount = 1 ;
        for (int r = row + 1 ; r < row + WIN_STREAK ; r++)
        {
            if (r >= SIZE) {break;}
            if (getMark(r,col) != mark) {break;}
            markCount++ ;
            if (markCount == WIN_STREAK) {return true;}

        }

        for (int r = row - 1 ; r > row - WIN_STREAK ; r--)
        {
            if (r < 0) {break;}
            if (getMark(r,col) != mark) {break;}
            markCount++ ;
            if (markCount == WIN_STREAK) {return true;}
        }
        return false;
    }
    /**
     * method confirming whether the mark placed at position (row,col) on board yields a winning streak in the
     * diagonal direction
     * @param row row coordinate of positioned mark
     * @param col column coordinate of positioned mark
     * @param mark type of placed mark
     * @return true if yields a streak , false otherwise
     */
    private boolean checkForStreakDiagonal(int row , int col , Mark mark)
  
    {
        int markCount = 1 ;
        for (int i = 1; true; i++)
        {
            if (!checkCoordRange(row - i , col + i)) {break;}
            if (getMark(row- i,col + i ) != mark) {break;}
            markCount++ ;
            if (markCount == WIN_STREAK)
            {return true;}
        }

        for (int i = 1; true; i++)
        {

            if (!checkCoordRange(row + i , col - i)) {break;}
            if (getMark(row+ i,col -i ) != mark) {break;}
            markCount++ ;
            if (markCount == WIN_STREAK) {return true;}
        }

        return false;
    }
    /**
     * method confirming whether the mark placed at position (row,col) on board yields a winning streak in the
     * anti-diagonal direction
     * @param row row coordinate of positioned mark
     * @param col column coordinate of positioned mark
     * @param mark type of placed mark
     * @return true if yields a streak , false otherwise
     */
    private boolean checkForStreakAntiDiagonal(int row , int col , Mark mark)
  
    {
        int markCount = 1 ;
        for (int i = 1; true; i++)
        {
            if (!checkCoordRange(row + i , col + i)) {break;}
            if (getMark(row+i,col + i ) != mark) {break;}
            markCount++ ;
            if (markCount == WIN_STREAK)
            {return true;}
        }

        for (int i = 1; i < WIN_STREAK ; i++)
        {
            if (!checkCoordRange(row - i , col - i)) {break;}
            if (getMark(row - i,col -i ) != mark) {break;}
            markCount++ ;
            if (markCount == WIN_STREAK)
            {return true;}
        }

        return false;
    }

    /**
     * method confirming whether the mark placed at position (row,col) on board yields a winning streak 
     * @param row row coordinate of positioned mark
     * @param col column coordinate of positioned mark
     * @param mark type of placed mark
     * @return true if yields a streak , false otherwise
     */
    private boolean checkStreak(int row , int col , Mark mark)
    {return checkForStreakVertical(row,col,mark) || checkForStreakHorizontal(row,col,mark) || checkForStreakDiagonal(row,col,mark) || checkForStreakAntiDiagonal(row,col,mark) ;}

    /**
     * method confirming whether the coordinate (row,col) is withing the board's range
     * @param row row coordinate of positioned mark
     * @param col column coordinate of positioned mark
     * @return true if it is , false otherwise
     */
    private boolean checkCoordRange( int row , int col)
    {
        return row < SIZE  && row >= 0 && col < SIZE && col >= 0;
    }

    /**
     * method confirming the status of the game 
     * @return true if game ended, false otherwise
     */
    public boolean gameEnded() {return !(this.getGameStatus() == GameStatus.IN_PROGRESS);}
    /**
     * Getter method for the winner field of the Board class
     * @return Mark representing the winner
     */
    public Mark getWinner() {return this.winner;}
    /**
     * Getter method for the GameStatus field of the Board class
     * @return GameStatus representing the status of the game
     */
    public GameStatus getGameStatus() {return this.status;}

    /**
     * method returning the mark type positioned at coordinate (row,col)
     * @param row row coordinate 
     * @param col col coordinate
     * @return Mark type
     */
    public Mark getMark(int row , int col)
    {
        if (!checkCoordRange(row, col)) {return Mark.BLANK;}
        return this.board[row][col];
    }

    /**
     * method that meditates placing a new mark on board at a particular coordinate if it is blank and within range.
     * the method also updates the GameStatus and winner field, by checking if the current mark placement
     * results a streak
     * @param mark mark type to be placed
     * @param row row pos coordinate
     * @param col column pos coordinate
     * @return Boolean true if mark placed sucssefuly, false otherwise
     */
    boolean putMark(Mark mark, int row, int col)
    {
        if (!checkCoordRange(row ,col)) {return false;}
        Mark currMark = getMark(row, col);
        if (currMark != Mark.BLANK) {return false;}
        this.board[row][col] = mark ;
        this.placedMarks++;
        if (checkStreak(row,col,mark))
        {
            if (mark == Mark.X) {this.status = GameStatus.X_WIN; this.winner = Mark.X ; }
            else {this.status = GameStatus.O_WIN ; this.winner = Mark.O;}

        }
        if (placedMarks == SIZE*SIZE && getGameStatus() == GameStatus.IN_PROGRESS)
        {this.status = GameStatus.DRAW ;}
        return true;
    }


}
