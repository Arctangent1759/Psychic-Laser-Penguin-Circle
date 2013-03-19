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


	//Player Constants
	public static final int NULL_PLAYER=-1; //Black player
	public static final int BLACK=0; //Black player
	public static final int WHITE=1; //White player


	//Game Constants
	public static final int MAX_CHIPS=10; //The maximum number of chips in the game


	//Debugging and misc.
	public static final boolean DEBUG=true; //Used to disable unwanted debug output

	/**
	 *
	 *	Prints o to stdout, if DEBUG is true.
	 *	@param o The object to be printed
	 *
	**/
	public static final void print(Object o){
		if (DEBUG){
			System.err.println(o);
		}
	}
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
