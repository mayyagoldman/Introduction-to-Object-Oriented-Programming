/**
 * Player interface represents a game player.
 * requires one method playTurn, placing a mark on a given board
 */
interface Player
{
    /**
     * This abstract method mediates a game turn of Player.
     * @param board a Board type object on which the relevant game is executed
     * @param mark the mark type to be placed on board by CleverPlayer
     */
    void playTurn(Board board , Mark mark);
}
