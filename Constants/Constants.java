/**
 *
 *	Global class contining constants that will be
 *	used throughout the project.
 *
**/

package Constants;

public final class Constants{


	//Board Constants
	public static final int BOARDHEIGHT=8; //The height of a board
	public static final int BOARDWIDTH = 8; //The width of a board
	public static final int WINNING_NETWORK = 6; //Winning network length


	//Player Constants
	public static final int NULL_PLAYER=-1; //Black player
	public static final int BLACK=0; //Black player
	public static final int WHITE=1; //White player


	//Game Constants
	public static final int MAX_CHIPS=10; //The maximum number of chips in the game
	
	// Machine Player Constants
	public static final int START_ALPHA = -2; //Minimum alpha value
	public static final int START_BETA = 2; //Maximum beta value


	//Debugging and misc.
	public static final boolean DEBUG=false; //Used to disable unwanted debug output

	/**
	 *
	 *	Prints o to stdout, if DEBUG is true.
	 *	Used for debugging purposes.
	 *	@param o The object to be printed
	 *
	**/
	public static final void print(Object o){
		if (DEBUG){
			System.err.println(o);
		}
	}

	/**
	 *
	 *	Tests string value of o against the string value of expected
	 *	and stops the program on fail with an error message.
	 *	Used exclusively for debugging purposes.
	 *	@param expected The expected value
	 *	@param o The object to be tested
	 *
	**/
	public static final void printTest(Object expected, Object o){
		print("---------------------");
		print("\tExpected: " + expected.toString() + "\n\tObserved: " + o.toString());
		print("---------------------");
		if (!expected.equals(o)){
			print("Test failed.");
			System.exit(1);
		}
		print("");
	}
}
