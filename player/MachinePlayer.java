/* MachinePlayer.java */

package player;

import Constants.Constants;
import DList.*;
import HashTable.*;
import Board.*;

/**
 *  An implementation of an automatic Network player.  Keeps track of moves
 *  made by both players.  Can select a move for itself.
 */
public class MachinePlayer extends Player {
  
	protected int color;
	protected int searchDepth;
	protected Board board;
	protected HashTable boards;
	
	// Creates a machine player with the given color.  Color is either 0 (black)
	// or 1 (white).  (White has the first move.)
	public MachinePlayer(int color){
		this.color = color;
		this.searchDepth = 5;
		board = new Board();
		myName = "God";
		boards = new HashTable(1000); //Test number
  	}

	// Creates a machine player with the given color and search depth.  Color is
	// either 0 (black) or 1 (white).  (White has the first move.)
	public MachinePlayer(int color, int searchDepth) {
		this.color = color;
		board = new Board();
		myName = "God";
		this.searchDepth = searchDepth;
		boards = new HashTable(1000); //Test number
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
		myName = "God";
		boards = new HashTable(1000); //Test number
	}

	/** Returns a new move by "this" player.  Internally records the move (updates
         *  the internal game board) as a move by "this" player.
         * @return The new Move. 
        **/
	public Move chooseMove() {
		Move dummy_move = new Move(); //Initializer
		Board copy = new Board();
		copy = board;
		Move bestMove = chooseOptimalMove(color, Constants.START_ALPHA, 
						Constants.START_BETA, dummy_move, searchDepth(1000), board);
		board = copy; // Returns board to what it once was before going down game tree. 
		updateBoard(color, bestMove);
		return bestMove;
	}

	// If the Move m is legal, records the move as a move by the opponent
	// (updates the internal game board) and returns true.  If the move is
	// illegal, returns false without modifying the internal state of "this"
	// player.  This method allows your opponents to inform you of their moves.
	public boolean opponentMove(Move m) {
		if (color == Constants.WHITE) {
			return updateBoard(Constants.BLACK, m);
		}
		else {
			return updateBoard(Constants.WHITE, m);
		}
	}

	/** If the Move m is legal, records the move as a move by "this" player
         * (updates the internal game board) and returns true.  If the move is
	 * illegal, returns false without modifying the internal state of "this"
	 * player.  This method is used to help set up "Network problems" for your
	 * player to solve.
         * @param The move being tested
         * @return Whether the move is legal or not.
        **/
	public boolean forceMove(Move m) {
    	if (color == Constants.BLACK) {
			return updateBoard(Constants.BLACK, m);
		}
		else {
			return updateBoard(Constants.WHITE, m);
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
	 * @param bestMove the best Move that has been currently made. 
	 * @param searchDepth how far down the game tree it will search.
         * @return The optimal move.
	**/
	private Move chooseOptimalMove(int color, int alpha, int beta, 
		Move bestMove, int searchDepth, Board board) {
		// I had an extremely hard time with this. If you guys can fix it that would be great :D
		
		Board copy = new Board(); //Saving board before anything is called.
		copy = board;
		if (searchDepth == 0 || evalBoard(color,board) == Constants.START_BETA) {
			return bestMove;
		}
		
		else if (color == this.color) {
			if (board.isFull()) {
				//Step implementation	
				for (int x1 = 0; x1 < Constants.BOARDWIDTH; x1++) {
					for (int y1 = 0; y1 < Constants.BOARDHEIGHT; y1++) {
						if (!board.hasChip(x1,y1) || board.getChip(x1,y1).getColor() != color) {
							continue; //No chip, nothing to see here move on.
						}
						else {
							for (int x2 = 0; x2 < Constants.BOARDWIDTH; x2++) {
								for (int y2 = 0; y2 < Constants.BOARDHEIGHT; y2++) {
									board = copy; // reverts every sibling.
									Move step = new Move(x2,y2,x1,y1);
									updateBoard(color,step);									
									if (!board.hasChip(x2,y2) && board.hasChip(x1,y1)) {
										continue; //Invalid move, nothing to see here move along.
									}
								
									int score = evalBoard(color, board);
									// Changing beta during opponent's turn's turn.  
									if (score < beta) {
										beta = score;
										bestMove = step;
									}
									// Pruning
									if (beta <= alpha) {
										break;
									}
						
									// Moving down the game tree
									else if (color == Constants.BLACK) {
										return chooseOptimalMove(Constants.WHITE, alpha, beta, bestMove, searchDepth-1, board);
									}
									else {
										return chooseOptimalMove(Constants.BLACK, alpha, beta, bestMove, searchDepth-1, board);
									}
								}
							}
						}
					}
				}
				return bestMove;
			}
						
			else {
				//Move implementation
				
				// Going through all possible child nodes.
				for (int x = 0; x < Constants.BOARDWIDTH; x++) {
					for (int y = 0; y < Constants.BOARDHEIGHT; y++) {
						board = copy; // reverts every sibling.
						Move move = new Move(x,y);
						updateBoard(color,move);
						if (!board.hasChip(x,y)) {
							continue; //Invalid chip, nothing to see here move on.
						}
						
						int score = evalBoard(color, board);
						// Changing alpha during player's turn.  
						if (score >= alpha) {
							alpha = score;
							bestMove = move;
						}
						// Pruning
						if (beta <= alpha) {
							break;
						}
						
						// Moving down the game tree
						else if (color == Constants.BLACK) {
							return chooseOptimalMove(Constants.WHITE, alpha, beta, bestMove, searchDepth-1, board);
						}
						else {
							return chooseOptimalMove(Constants.BLACK, alpha, beta, bestMove, searchDepth-1, board);
						}
					}
				}
			return bestMove;	
			}
		}
		//Opponent's turn
		else {
			if (board.isFull()) {
				//Step implementation	
				for (int x1 = 0; x1 < Constants.BOARDWIDTH; x1++) {
					for (int y1 = 0; y1 < Constants.BOARDHEIGHT; y1++) {
						if (!board.hasChip(x1,y1) || board.getChip(x1,y1).getColor() != color) {
							continue; //No chip, nothing to see here move on.
						}
						else {
							for (int x2 = 0; x2 < Constants.BOARDWIDTH; x2++) {
								for (int y2 = 0; y2 < Constants.BOARDHEIGHT; y2++) {
									board = copy; // reverts every sibling.
									Move step = new Move(x2,y2,x1,y1);
									updateBoard(color,step);									
									if (!board.hasChip(x2,y2) && board.hasChip(x1,y1)) {
										continue; //Invalid move, nothing to see here move along.
									}
								
									int score = evalBoard(color, board);
									// Changing beta during opponent's turn's turn.  
									if (score < beta) {
										beta = score;
										bestMove = step;
									}
									// Pruning
									if (beta <= alpha) {
										break;
									}
						
									// Moving down the game tree
									else if (color == Constants.BLACK) {
										return chooseOptimalMove(Constants.WHITE, alpha, beta, bestMove, searchDepth-1, board);
									}
									else {
										return chooseOptimalMove(Constants.BLACK, alpha, beta, bestMove, searchDepth-1, board);
									}
								}
							}
						}
					}
				}
				return bestMove;
			}
			else {
				for (int x = 0; x < Constants.BOARDWIDTH; x++) {
					for (int y = 0; y < Constants.BOARDHEIGHT; y++) {
						board = copy; // reverts every sibling.
						Move move = new Move(x,y);
						updateBoard(color,move);
						if (!board.hasChip(x,y)) {
							continue; //Invalid chip, nothing to see here move on.
						}
						
						int score = evalBoard(color , board);
						// Changing beta during opponent's turn.  
						if (score < beta) {
							beta = score;
							bestMove = move;
						}
						
						// Pruning
						if (beta <= alpha) {
							break;
						}
						
						// Moving down the game tree
						else if (color == Constants.BLACK) {
							return chooseOptimalMove(Constants.WHITE, alpha, beta, bestMove, searchDepth-1, board);
						}
						else {
							return chooseOptimalMove(Constants.BLACK, alpha, beta, bestMove, searchDepth-1, board);
						}
					}
				}
				return bestMove;
			}		
		}
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
	private boolean updateBoard(int color, Move move) {
		if (move.moveKind == Move.ADD) {
			try {
				board.addChip(color,move.x1,move.y1);
			}
			catch (InvalidMoveException e) {
				return false;
			}
			return true;
		}
		else if (move.moveKind == Move.STEP) {
			try {
				board.moveChip(move.x1,move.y1,move.x2,move.y2);
			}
			catch (InvalidMoveException e){
				return false;
			}
			return true;
		}
		else {
			return true; //Only Quit, quitting is always a valid move, although it might not be the best.
		}
	}
}                            

