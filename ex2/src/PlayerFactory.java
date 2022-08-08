/**
 * The PlayerFactory class bridges between Tourment and Player, generating and facilitating the correct type of player
 */
public class PlayerFactory
{
    /**
     * This method recieves a Player type and creates it
     * @param player_type name string of desired player
     * @return Player , specific player while creating an instance of the desired Player type. if invalud type returns
     * null
     */
    public Player buildPlayer(String player_type)
    {
        switch(player_type) {
            case "human":
                return new HumanPlayer() ;
            case "whatever":
                return new WhateverPlayer();
            case "clever":
                return new CleverPlayer();
            case "snartypamts":
                return new SnartypamtsPlayer();
            default:
                return null;
        }

    }
}
