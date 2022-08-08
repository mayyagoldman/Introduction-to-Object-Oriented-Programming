public class Game {
    private Player playerX;
    private Player playerO;
    private Renderer renderer;
    private Board board ;

    public Game(Player playerX , Player playerO , Renderer renderer) {this.playerO = playerO; this.playerX = playerX ; this.renderer = renderer ; this.board = new Board() ;}
    public Board.GameStatus run()
    {
        Player[] players = { playerX, playerO };
        Mark[] marks = { Mark.X, Mark.O };
        int counter = 0 ;
        while (board.getGameStatus() == Board.GameStatus.IN_PROGRESS)
        {
            renderer.renderBoard(this.board);
            players[counter % 2].playTurn(this.board , marks[counter % 2]);
            counter ++ ;

        }
        renderer.renderBoard(this.board);
        return board.getGameStatus() ;


    }



    public static void main (String[] args)
    {
        Player playerO = new Player() ;
        Player playerX = new Player() ;
        Renderer renderer = new Renderer() ;

        Game game = new Game(playerX , playerO , renderer) ;
        System.out.print(game.run());

//        player.playTurn(board , Mark.X);
//        renderer.renderBoard(board);
//        for (int i = 0 ; i < board.SIZE ; i ++)
//        {
//            for (int j = 0 ; j < board.SIZE ; j ++)
//            {
//                Mark x = board.getMark(i  , j );
//                System.out.print(x + " ") ;
//            }
//        }
//        Renderer renderer = new Renderer() ;
//        renderer.renderBoard(board);
    }
}
