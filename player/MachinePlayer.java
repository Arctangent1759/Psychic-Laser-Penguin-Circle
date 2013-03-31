/* MachinePlayer.java */

package player;
import Board.*;
import HashTable.*;
import DList.*;
import Constants.Constants;

/**
 *	An implementation of an automatic Network player.	Keeps track of moves
 *	made by both players.	Can select a move for itself.
 */
public class MachinePlayer extends Player {
	//Constants
	protected static int DEFAULTSEARCHDEPTH=5;
	protected static int STEPSEARCHDEPTH=1;
	protected static int HASHSIZE=1000;				//TODO: Determine appropriate search depth.
	protected static String NAME="Alan Turing";
	

	//Instance Vars
	protected int searchDepth;
	protected int color;
	protected Board board;
	protected HashTable boardCache;

	// Creates a machine player with the given color.	Color is either 0 (black)
	// or 1 (white).	(White has the first move.)
	public MachinePlayer(int color) {
	  this(color,DEFAULTSEARCHDEPTH);
	}

	// Creates a machine player with the given color and search depth.	Color is
	// either 0 (black) or 1 (white).	(White has the first move.)
	public MachinePlayer(int color, int searchDepth) {
		this.color=color;
		this.searchDepth=searchDepth;
		this.myName=NAME;
		this.board=new Board();
		this.boardCache = new HashTable(HASHSIZE);
	}

	// Returns a new move by "this" player.	Internally records the move (updates
	// the internal game board) as a move by "this" player.
	public Move chooseMove() {
		Move m = chooseBestMove(color,-2,2,searchDepth).move;
		try{
			board.doMove(m,color);
		}catch(InvalidMoveException e){
			Constants.print("This should never happen.");
		}
		Constants.print(board);
		return m;
	} 

	// If the Move m is legal, records the move as a move by the opponent
	// (updates the internal game board) and returns true.	If the move is
	// illegal, returns false without modifying the internal state of "this"
	// player.	This method allows your opponents to inform you of their moves.
	public boolean opponentMove(Move m) {
		try{
			board.doMove(m,getOppColor(this.color));
			return true;
		}catch (InvalidMoveException e){
			return false;
		}
	}

	// If the Move m is legal, records the move as a move by "this" player
	// (updates the internal game board) and returns true.	If the move is
	// illegal, returns false without modifying the internal state of "this"
	// player.	This method is used to help set up "Network problems" for your
	// player to solve.
	public boolean forceMove(Move m) {
		try{
			board.doMove(m,this.color);
			return true;
		}catch (InvalidMoveException e){
			return false;
		}
	}


	//Helper functions
	

	private static int START_ALPHA=-2;
	private static int START_BETA=2;

	//Use Minimax to recursively finds the optimal move for the player
	private Best chooseBestMove(int currColor, double alpha, double beta, int depth){
		if (board.numBlack()==10 && board.numWhite()==10){
			depth=Math.min(depth,STEPSEARCHDEPTH);
		}


		//Init my best move and opponent's best moves
		Best myBest = new Best(new Move(), 0);
		Best reply;

		//Base Case: If the board already has a winning network or the recursive depth is hit, return the board's score
		if (board.getWinner()!=Constants.NULL_PLAYER || depth==0){
			myBest.score=scoreBoard(depth);
			return myBest;
		}

		//Otherwise, commence alpha-beta pruning and game tree search
		if (currColor==this.color){	//Curr Player is Machine
			myBest.score=alpha;
		}else{	//Curr Player is human
			myBest.score=beta;
		}

		//Get all moves
		DList<Move> moves = getAllMoves(currColor);
	
		Move m;
		while (!moves.isEmpty()){//For each move
			m=moves.pop();

			//Do the move
			try{
				board.doMove(m,currColor);
			}catch (InvalidMoveException e){
				//Illegal move caught. Board is unchanged, so resume next.
				continue;	
			}

			//Minimax recursive step
			reply = chooseBestMove(getOppColor(currColor),alpha,beta,depth-1);

			//Undo the move.
			try{
				board.undoMove(m,currColor);
			}catch(InvalidMoveException e){
				Constants.print("This should never happen.");
				System.exit(1);
			}

			if ((currColor==this.color) && (reply.score >= myBest.score)){ //Curr Player is machine
				myBest.move=m;
				myBest.score=reply.score;
				alpha=reply.score;
			}else if(currColor==getOppColor(this.color)){ //Curr Player is human
				myBest.move=m;
				myBest.score=reply.score;
				beta=reply.score;
			}
			if (alpha>=beta){
				return myBest;
			}
		}
		return myBest;
	}

	//Gets all moves for myColor
	private DList<Move> getAllMoves(int myColor){
		DList<Move> moves = new DList<Move>();
		for (int x = 0; x < Constants.BOARDWIDTH; x++){
			for (int y = 0; y < Constants.BOARDWIDTH; y++){

				if (board.numWhite()==10 && board.numBlack()==10){	//Consider step moves only

					if (board.hasChip(x,y) && board.getChip(x,y).getColor()==myColor){
						for (int dx = -Constants.BOARDWIDTH; dx < Constants.BOARDWIDTH; dx++){
							for (int dy = -Constants.BOARDHEIGHT; dy < Constants.BOARDHEIGHT; dy++){
								moves.push(new Move(x+dx,y+dy,x,y));
							}
						}
					}

				}else{												//Consider add moves only
					//If x,y is empty, can add a chip to x,y
					if (!board.hasChip(x,y)){
						moves.push(new Move(x,y));
					}
				}
			}
		}
		return moves;
	}

	//Returns the score of the current board
	//Scores go from -2 to 2. All scores above 1 are winning moves, and all scores below -1 are losing moves.
	private double scoreBoard(int depth){
		if (board.getWinner()==this.color){
			return 1.0+(((double)depth)/searchDepth);
		}else if (board.getWinner()==getOppColor(this.color)){
			return -1.0-(((double)(searchDepth-depth))/searchDepth);
		}else{
			return 0;
		}
	}

	//Returns opponent color
	private int getOppColor(int c){
		if (c==Constants.BLACK){
			return Constants.WHITE;
		}else if (c==Constants.WHITE){
			return Constants.BLACK;
		}else{
			return Constants.NULL_PLAYER;
		}
	}

}
class Best{
	protected Move move;
	protected double score;
	public Best(Move m, double s){
		this.move=m;
		this.score=s;
	}
}
