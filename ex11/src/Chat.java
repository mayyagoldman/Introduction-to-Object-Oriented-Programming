import java.util.Scanner ;


class Chat {
    public static void main (String [] args)
    {
        String[] illegalReplies1 = { "NO say I should say: " + ChatterBot.ILLEGAL_REQUEST_PLACEHOLDER, "what? " + ChatterBot.ILLEGAL_REQUEST_PLACEHOLDER + "?" };
        String[] illegalReplies2 = { "NOOOOO say : say " + ChatterBot.ILLEGAL_REQUEST_PLACEHOLDER , "whaaat?? " + ChatterBot.ILLEGAL_REQUEST_PLACEHOLDER , "what? "};
        String[] legalReplies1 = {"would you like me to say " + ChatterBot.REQUESTED_PHRASE_PLACEHOLDER +"? okay then: " + ChatterBot.REQUESTED_PHRASE_PLACEHOLDER};
        String[] legalReplies2 = {"I will reply: " + ChatterBot.REQUESTED_PHRASE_PLACEHOLDER };
        ChatterBot[] bots = new ChatterBot[2];
        bots[0] = new ChatterBot("Maya" , legalReplies1, illegalReplies1);
        bots[1] = new ChatterBot("Maia", legalReplies2 , illegalReplies2);

        String statement = "oh na na what's my name " ;
        Scanner scanner = new Scanner(System.in) ;
        while (true)
        {
            for (ChatterBot bot : bots )
            {
                statement = bot.replyTo(statement) ;
                System.out.print(bot.getName() +
                        ": "+ statement) ;
                scanner.nextLine();
            }
        }




    }

}