/* MachinePlayer.java */

package player;

import Constants.Constants;
import DList.*;
import HashTable.*;

/**
 *  An implementation of an automatic Network player.  Keeps track of moves
 *  made by both players.  Can select a move for itself.
 */
public class MachinePlayer extends Player {
  
	protected int color;
	protected int searchDepth;
	protected Board board;
	protected DList<Move> moves;
	protected HashTable boards;
	
	// Creates a machine player with the given color.  Color is either 0 (black)
	// or 1 (white).  (White has the first move.)
	public MachinePlayer(int color){
		this.color = color;
		this.searchDepth = 5;
		myName = God;
		moves = new DList<Move>();
  	}

	// Creates a machine player with the given color and search depth.  Color is
	// either 0 (black) or 1 (white).  (White has the first move.)
	public MachinePlayer(int color, int searchDepth) {
		this.color = color;
		this.searchDepth = searchDepth;
		moves = new DList<Move>();
	}
	
	/**
	 *
	 * Specifically used for testing code. Can use a board already created a 
	 * play from there.
	 *
	**/
	public MachinePlayer(int color, int searchDepth, Board board) {
		this.color = color;
		this.searchDepth = searchDepth;
		this.board = board;
		moves = new DList<Move>();
	}

	// Returns a new move by "this" player.  Internally records the move (updates
	// the internal game board) as a move by "this" player.
	public Move chooseMove() {  
		return chooseOptimalMove(color, Constants.START_ALPHA, 
		Constants.START_BETA, searchDepth(1000), board);
	}

	// If the Move m is legal, records the move as a move by the opponent
	// (updates the internal game board) and returns true.  If the move is
	// illegal, returns false without modifying the internal state of "this"
	// player.  This method allows your opponents to inform you of their moves.
	public boolean opponentMove(Move m) {
		if (color == Constants.WHITE) {
			updateBoard(Constants.BLACK, m);
		}
		else {
			updateBoard(Constants.WHITE, m);
		}
	}

	// If the Move m is legal, records the move as a move by "this" player
	// (updates the internal game board) and returns true.  If the move is
	// illegal, returns false without modifying the internal state of "this"
	// player.  This method is used to help set up "Network problems" for your
	// player to solve.	
	public boolean forceMove(Move m) {
    	if (color == Constants.BLACK) {
			updateBoard(Constants.BLACK, m);
		}
		else {
			updateBoard(Constants.WHITE, m);
		}
	}
	
	/**
	 *
	 *	Calculates how far the MachinePlayer will search through the tree.
	 *  @param the stack limit we want to set it.
	 *  @return how far it will search through the tree for the instance of the board.
	**/
	private int searchDepth(int stackLimit) {
		int numSpots = 0;
		int currStacks = 1;
		int searchDepth = 0;
		for (int x = 0; x < Constants.BOARDWIDTH; x++) {
			for (int y = 0; y < Constants.BOARDHEIGHT; y++) {
				if (!board.hasChip(x,y)) {
					numSpots++; 
				}
			}		
		}
		while (currStacks * numSpots < stackLimit) {
			currStacks = currStacks * numSpots;
			numSpots--;
			searchDepth++;
		}
		return searchDepth;
	}
	
	/**
	 *
	 * Returns the best possible move for the board.
	 * @param color the side in which the move will be determined.
	 * @param alpha maximum lower bound for game tree
	 * @param beta minimum upper bound for game tree
	 * @param searchDepth how far down the game tree it will search.
	**/
	private Move chooseOptimalMove(int color, int alpha, int beta, int searchDepth, Board board) {
		// ignore this for now a work in progress lulz
		for (int x = 0; x < Constants.BOARDWIDTH; x++) {
			for (int y =0; y < Constants.BOARDHEIGHT; y++) {
				if (searchDepth == 1) {
					evalBoard(color, board);
				}
				else if (color == Constants.WHITE) {
					return chooseOptimalMove(Constants.BLACK, alpha, beta, searchDepth-1 , board); 
				}
				else {
					return chooseOptimalMove(Constants.WHITE, alpha beta, searchDepth-1, board);
				}
			}
		}
		return new Move();
	}
	/**
	 * An algorithm to evaluate the current situation of the board for a player.
	 * @param color - the player whose perspective score will be evaluated.
	 * @param board - the board in which the score will be evaluated for.
	 * @return the score of the board.
	**/
	private int evalBoard(int color, Board board) {
		// The current algorithm needs serious improvement.
		DList<Net> networks = board.getLongestNetworks(); 
		int score = 0;
		while (!networks.isEmpty()) {
			Net network = networks.popFront();
			if (network.getLength() == Constants.WINNING_NETWORK) {
				if(network.getPlayer() == color) {
					return Constants.START_ALPHA;
				}
				else {
					return Constants.START_BETA;
				}
			}
			int netScore = network.getLength();
			if (network.getPlayer() == color) {
				score += netScore;
			}
			else {
				score -= netScore;
			}
		}
		return score; 
	}
	/**
	 *
	 * Updates the internal board for the player. If move is invalid, then it does not update.
	 * @param color of the piece it wants to update the internal board with.
	 * @param Move m, the move with which it wants to update the internal board.
	 * @return True if move is valid, false otherwise. 
	**/
	private boolean updateBoard(int color, Move m) {
		Board copy = new Board();
		copy = board;
		if (m.moveKind == move.ADD) {
			try {
				copy.addChip(color,move.x1,move.y1);
			}
			catch (InvalidMoveException e) {
				return false;
			}
			board = copy;
			moves.push(m);
			return true;
		}
		else if (m.moveKind == move.STEP) {
			try {
				copy.addChip(color ,move.x2, move.y2, move.x1, move.y1);
			}
			catch (InvalidMoveException e) {
				return false;
			}
			board = copy;
			moves.push(m);
			return true;
		}
		else {
			System.out.println(m);
			return true;
		}
	}
}                            

