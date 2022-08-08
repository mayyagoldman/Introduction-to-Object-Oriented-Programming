/**
 * The game class, mediates a game of tictactoe
 */
public class Game {
    private Player playerX;
    private Player playerO;
    private Renderer renderer;

    /**
     * constructor method for the Game class
     * @param playerX Player of some type whos mark is X in this game
     * @param playerO Player of some type whos mark is O in this game
     * @param renderer Renderer of some type that will display the game
     */
    public Game(Player playerX , Player playerO , Renderer renderer) {this.playerO = playerO; this.playerX = playerX ; this.renderer = renderer ; }

    /**
     * The run method is in charge of running a single game of tictactoe
     * It initialzes a board, mediates the turns of the player , and renderers it
     * @return GameStatus reflecting the final result of the game
     */
    public Board.GameStatus run()
    {
        Board board = new Board();
        Player[] players = { playerX, playerO };
        Mark[] marks = { Mark.X, Mark.O };
        int rounds = 0 ;
        while (board.getGameStatus() == Board.GameStatus.IN_PROGRESS)
        {
            renderer.renderBoard(board);
            players[rounds % 2].playTurn(board , marks[rounds % 2]);
            rounds++ ;

        }
        renderer.renderBoard(board);
        return board.getGameStatus() ;
    }
}
