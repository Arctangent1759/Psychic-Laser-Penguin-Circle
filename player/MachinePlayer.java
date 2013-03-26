/* MachinePlayer.java */

package player;

/**
 *  An implementation of an automatic Network player.  Keeps track of moves
 *  made by both players.  Can select a move for itself.
 */
public class MachinePlayer extends Player {
  
	protected int color;
	protected int searchDepth;
	protected Board board;
	
	// Creates a machine player with the given color.  Color is either 0 (black)
	// or 1 (white).  (White has the first move.)
	public MachinePlayer(int color){
		this.color = color;
		this.searchDepth = 5;
		myName = God;
  	}

	// Creates a machine player with the given color and search depth.  Color is
	// either 0 (black) or 1 (white).  (White has the first move.)
	public MachinePlayer(int color, int searchDepth) {
		this.color = color;
		this.searchDepth = searchDepth;
	}

	// Returns a new move by "this" player.  Internally records the move (updates
	// the internal game board) as a move by "this" player.
	public Move chooseMove() {  
		return chooseOptimalMove(color, START_ALPHA, START_BETA, searchDepth());
	}

	// If the Move m is legal, records the move as a move by the opponent
	// (updates the internal game board) and returns true.  If the move is
	// illegal, returns false without modifying the internal state of "this"
	// player.  This method allows your opponents to inform you of their moves.
	public boolean opponentMove(Move m) {
		return false;
	}

	// If the Move m is legal, records the move as a move by "this" player
	// (updates the internal game board) and returns true.  If the move is
	// illegal, returns false without modifying the internal state of "this"
	// player.  This method is used to help set up "Network problems" for your
	// player to solve.	
	public boolean forceMove(Move m) {
    	return false;
	}
	
	/**
	 *
	 *	Calculates how far the MachinePlayer will search through the tree.	 
	 *  @return how far it will search through the tree for the instance of the board.
	**/
	private int searchDepth() {
		return 0; //TODO
	}
	
	/**
	 *
	 * Returns the best possible move for the board.
	 * @param color the side in which the move will be determined.
	 * @param alpha maximum lower bound for game tree
	 * @param beta minimum upper bound for game tree
	 * @param searchDepth how far down the game tree it will search.
	**/
	private Move chooseOptimalMove(int color, int alpha, int beta, int searchDepth) {
		return new Move(); //TODO
	}	
}                            

