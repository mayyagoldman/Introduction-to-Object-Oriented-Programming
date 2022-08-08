/**
 * The Tournament class is in charge of running a series of tictactoe games
 */
public class Tournament
{
    final String SCORE_MSG = "player1 scored: %d, player2 scored: %d. Draws: %d\r" ;
    private Player [] players;
    private Renderer renderer;
    private int rounds;

    /**
     * Constructor method of the Tournament class
      * @param rounds number of games to be played
     * @param renderer render that will display the games' states
     * @param players list of two players to compete in the Tournament
     */
    Tournament(int rounds ,Renderer renderer ,Player[] players) {this.players = players ; this.renderer = renderer ; this.rounds = rounds;}

    /**
     * this method is in charge of running the number of game as specified in self.rounds.
     * keeps track of scores  prints them out at the end across all games
     * @return int[] array of scores across alk games [player1 , player2, draws]
     */
    public int[] playTournament()
    {
        int [] scores = {0,0,0};
        for (int round = 0 ;  round < this.rounds ; round++)
        {
            Game game = new Game(players[round% 2] , players[(round+1) % 2]  , this.renderer);
            Board.GameStatus result = game.run();
            if (result == Board.GameStatus.X_WIN) {scores[round% 2] ++ ;}
            if (result == Board.GameStatus.O_WIN) {scores[(round+1) % 2] ++ ;}
            if (result == Board.GameStatus.DRAW) {scores[2] ++;}
            System.out.printf(SCORE_MSG,scores[0] ,scores[1] , scores[2]);
        }

        return scores;
    }

    /**
     * main method, under Tournament class.
     * @param args expected num of args is 4: num trials , renderer type , player1 type , player 2 type
     */
    public static void main (String[] args)
    {
        final String NUM_ARG_ERROR = "WRONG_NUM_OF_ARGUMENTS-CORRECT FORM: NUM_TRIALS,RENDER_TYPE,PLAYER1_TYPE,PLAYER2_TYPE";
        final String NEG_TRIALS = "NUMBER OF TRIALS SHOULD BE A POSITIVE INTEGER";
        final String REND_ERROR = "WRONG RENDER TYPE. ENTER none OR console";
        final String PLAYER_ERROR = "WRONG PLAYER TYPE. ENTER human OR clever OR snartypamts OR whatever";

        final int ARG_NUM = 4;
        final int TRIALS = 0;
        final int REND = 1;
        final int PLAYER1 = 2 ;
        final int PLAYER2 = 3 ;


        if (args.length != ARG_NUM)
        {
            System.out.print(NUM_ARG_ERROR);
            return;
        }
        int rounds = Integer.parseInt(args[TRIALS]);
        if (rounds < 0)
        {
            System.out.print(NEG_TRIALS);
            return;
        }
        PlayerFactory player_factory = new PlayerFactory() ;
        RendererFactory render_factory = new RendererFactory() ;
        Renderer renderer = render_factory.buildRenderer(args[REND]);
        if (renderer == null)
        {
            System.out.print(REND_ERROR);
            return;
        }
        Player player1 = player_factory.buildPlayer(args[PLAYER1]);
        Player player2 = player_factory.buildPlayer(args[PLAYER2]) ;
        if (player1 == null || player2 == null)
        {
            System.out.print(PLAYER_ERROR);
            return;
        }
        Player[] players = {player1 , player2} ;

        Tournament tournament = new Tournament(rounds, renderer , players) ;
        int [] results = tournament.playTournament();
    }
}
