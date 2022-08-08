public class Board
{
    public static final int SIZE = 4 ;
    public static final int WIN_STREAK = 3 ;
    public enum GameStatus {O_WIN , X_WIN , DRAW , IN_PROGRESS}


    private GameStatus status;
    private Mark[][] board ;
    private int marks ;
    private Mark winner;

    private boolean check_for_streak_horizontal( int row , int col , Mark mark )
    {
        int count = 1 ;
        for (int i = col + 1 ; i < col + WIN_STREAK ; i++)
        {
            if (i >= SIZE) {break;}
            if (getMark(row,i) != mark) {break;}
            count++ ;
            if (count == WIN_STREAK)
            {return true;}

        }

        for (int i = col - 1 ; i > col - WIN_STREAK ; i--)
        {
            if (i < 0) {break;}
            if (getMark(row,i) != mark) {break;}
            count++ ;
            if (count == WIN_STREAK)
            {return true ; }
        }
        return false;
    }

    private boolean check_for_streak_vertical( int row , int col , Mark mark )
    {
        int count = 1 ;
        for (int i = row + 1 ; i < row + WIN_STREAK ; i++)
        {
            if (i >= SIZE) {break;}
            if (getMark(i,col) != mark) {break;}
            count++ ;
            if (count == WIN_STREAK)
            {return true;}

        }

        for (int i = row - 1 ; i > row - WIN_STREAK ; i--)
        {
            if (i < 0) {break;}
            if (getMark(i,col) != mark) {break;}
            count++ ;
            if (count == WIN_STREAK)
            {return true ; }
        }
        return false;
    }

    private boolean check_for_streak_diagonal(int row , int col , Mark mark)
    {
        int count = 1 ;
        for (int i = 1; i < WIN_STREAK ; i++)
        {

            if (!checkInputRange(row - i , col + i)) {break;}
            if (getMark(row- i,col + i ) != mark) {break;}
            count++ ;
            if (count == WIN_STREAK)
            {return true;}
        }

        for (int i = 1; i < WIN_STREAK ; i++)
        {

            if (!checkInputRange(row + i , col - i)) {break;}
            if (getMark(row+ i,col -i ) != mark) {break;}
            count++ ;
            if (count == WIN_STREAK)
            {return true;}
        }

        return false;
    }

    private boolean check_for_streak_anti_diagonal(int row , int col , Mark mark)
    {
        int count = 1 ;
        for (int i = 1; i < WIN_STREAK ; i++)
        {

            if (!checkInputRange(row + i , col + i)) {break;}
            if (getMark(row+i,col + i ) != mark) {break;}
            count++ ;
            if (count == WIN_STREAK)
            {return true;}
        }

        for (int i = 1; i < WIN_STREAK ; i++)
        {

            if (!checkInputRange(row - i , col - i)) {break;}
            if (getMark(row - i,col -i ) != mark) {break;}
            count++ ;
            if (count == WIN_STREAK)
            {return true;}
        }

        return false;
    }


    private boolean check_streak(int row , int col , Mark mark)
    {
        return check_for_streak_vertical(row,col,mark) || check_for_streak_horizontal(row,col,mark) || check_for_streak_diagonal(row,col,mark) || check_for_streak_anti_diagonal(row,col,mark) ;
    }
    private boolean checkInputRange( int row , int col)
    {
        return row < SIZE  && row >= 0 && col < SIZE && col >= 0;
    }
    public boolean gameEnded() {return !(this.getGameStatus() == GameStatus.IN_PROGRESS);}
    public Mark getWinner() {return this.winner;}
    public GameStatus getGameStatus() {return this.status;}
    public Mark getMark(int row , int col)
    {
        if (!checkInputRange(row, col)) {return Mark.BLANK;}
        return this.board[row][col];
    }
    boolean putMark(Mark mark, int row, int col)
    {
        if (!checkInputRange(row ,col)) {return false;}
        Mark curr = getMark(row, col);
        if (curr!= Mark.BLANK) {return false;}
        this.board[row][col] = mark ;
        if (check_streak(row,col,mark))
        {
            if (mark == Mark.X) {this.status = GameStatus.X_WIN; this.winner = Mark.X ; }
            else {this.status = GameStatus.O_WIN ; this.winner = Mark.O;}

        }
        if (marks == SIZE*SIZE && getGameStatus() == GameStatus.IN_PROGRESS)
        {this.status = GameStatus.DRAW ;}
        return true;
    }
    public Board() {
        this.status = GameStatus.IN_PROGRESS ;
        this.marks = 0 ;
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
}
