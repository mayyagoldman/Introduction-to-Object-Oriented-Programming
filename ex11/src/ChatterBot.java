import java.util.*;

/**
 * The ChatterBot exercise.
 */

class ChatterBot {
	static final String REQUEST_PREFIX = "say ";
	static final String REQUESTED_PHRASE_PLACEHOLDER = "<phrase>" ;
	static final String ILLEGAL_REQUEST_PLACEHOLDER = "<request>";

	Random rand = new Random();

	String _name;
	String [] repliesToLegalRequest ;
	String[] repliesToIllegalRequest;

	ChatterBot(String name , String [] repliesToLegalRequest ,  String[] repliesToIllegalRequest)
	/**
	 * Constructer Method of the ChatterBot class
	 * @param name string representing name of ChatterBot
	 * @param repliesToLegalRequest array of strings - reply templates for legal requests
	 * @param repliesToIllegalRequest array of strings - reply templates for illegal requests
	 * @return none
	 */

	{
		_name = name ;
		this.repliesToIllegalRequest = new String[repliesToIllegalRequest.length];
		System.arraycopy(repliesToIllegalRequest, 0, this.repliesToIllegalRequest, 0,
				repliesToIllegalRequest.length);
		this.repliesToLegalRequest = new String[repliesToLegalRequest.length] ;
		System.arraycopy(repliesToLegalRequest, 0, this.repliesToLegalRequest,
				0, repliesToLegalRequest.length);
	}

	String getName()
	/**
	 * Getter method for the name field of the ChatterBot
	 * @param none
	 * @return string name
	 */
	{
		return _name ;
	}

	String replyTo(String statement) {
		/**
		 * Method facilitating the appropriate reply of the ChatterBot to given response
		 * @param statement given string Chatterbot needs to respond to
		 * @return String custemized randomized response of the ChatterBot
		 */
		if(statement.startsWith(REQUEST_PREFIX)) // check if the response is legal
		{
			String phrase = statement.replaceFirst(REQUEST_PREFIX, ""); // cleans phrase from its prefix
			return replacePlaceholderInARandomPattern(repliesToLegalRequest , REQUESTED_PHRASE_PLACEHOLDER , phrase) ;
		}
		return replacePlaceholderInARandomPattern(repliesToIllegalRequest ,ILLEGAL_REQUEST_PLACEHOLDER , statement );
	}

	String replacePlaceholderInARandomPattern(String [] replies , String place_holder , String replacement)
	/**
	 * A method custemizing and choosing out of a predefined pool of options ChatterBot's response to a given statement
	 * Generalization of the initial respondTolegalRequest and respondToIllegalRequest methods to avoid
	 * code duplication
	 * @param replies array of strings - reply templates for legal / illegal requests
	 * @param place_holder String to be replaced by replacement
	 * @param replacement String statment to be inserted instead of place_holder
	 * @return String Chatterbot's processed response
 	 */
	{
		int randomIndex = rand.nextInt(replies.length); // choose random reply template in the range of reply options
		String reply = replies[randomIndex];
		return reply.replaceAll(place_holder, replacement); // replaces all place_holder with replacement

	}

//	String respondTolegalRequest(String statement) {
//		int randomIndex = rand.nextInt(repliesToLegalRequest.length);
//		String reply = repliesToLegalRequest[randomIndex];
//		return reply.replaceAll(REQUESTED_PHRASE_PLACEHOLDER, statement);
//
//	}
//
//	String respondToIllegalRequest(String statement) {
//		int randomIndex = rand.nextInt(repliesToIllegalRequest.length);
//		String reply = repliesToIllegalRequest[randomIndex];
//		return reply.replaceAll(ILLEGAL_REQUEST_PLACEHOLDER, statement);
//	}
}
