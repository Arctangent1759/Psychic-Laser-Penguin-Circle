                                                                     
                                             
/**
 *	Board represents a single game state. It knows the 
 *	position of each chip, and enforces the rules of the 
 *	game, throwing an exception whenever a rule violation
 *	is encountered.
 *	
 *	Invariants:
 *  	1. Will throw an exception if a move is illegal.
 *  	2. No two chips in any given place.
 *  	3. Cannot be three chips adjacent to each other.
 *  	4. Cannot place more than 10 chips of the same color.
 *  	5. No chips in wrong goals.
 *  	6. No chips in corners.
 *	
**/

public class Board{	
	/**
	 *
	 *	Constructs a new Board.
	 *
	**/
	public int[][] board;
	
	public Board() {
		board = new int[Constants.BOARDWIDTH][Constants.BOARDHEIGHT];
	}

	/**
	 *
	 *	Moves chip c to point x,y
	 *	@param c The chip to be moved.
	 *	@param x The x coordinate of the chip's destination.
	 *	@param y The y coordinate of the chip's destination.
	**/
	public void moveChip(Chip c, int x, int y) throws InvalidChipException{
		board[c.xPos][c.yPos] = null;
		if(hasChip(board[x][y]) == false){
			board[x][y] = new Chip(this,c.color,x,y);
		}
		else{
			throw InvalidChipException;
		}
	}
	/**
	 *
	 *	Creates chip at x,y.
	 *	@param x the destination x coordinate
	 *	@param y the destination y coorinate
	 *	@throws InvalidChipException if the chip is added
	 *			in an illegal location (on top of another
	 *			chip, in the wrong goal, or three in a row).
	 *
	**/
	public void addChip(int color, int x, int y) throws InvalidChipException{
		if(hasChip(board[x][y])){
			board[x][y] = new Chip(this,c.color,x,y);
		}
		else{
			throw InvalidChipException
		}
	}
	/**
	 *
	 *	removes the chip at x,y.
	 *	@param x the target x coordinate
	 *	@param y the target y coordinate
	 *	@return nothing.
	 *
	**/
	public void removeChip(Chip c){
		if(hasChip())
		board[c.xPos()][c.yPos()] = null;
	}
	/**
	 *
	 *	Gets the chip at x,y.
	 *	@param x the target x coordinate
	 *	@param y the target y coordinate
	 *	@throws ChipNotFoundException if there is no chip at x,y
	 *	@return the Chip at x,y
	 *
	**/
	public Chip getChip(int x, int y) throws ChipNotFoundException{
		if(!hasChip(x,y)){
			throw ChipNotFoundException;
		}
		return board[x][y];
	}
	/**
	 *
	 *	Tells whether a chip at x,y exists
	 *	@param x the target x coordinate
	 *	@param y the target y coordinate
	 *	@return whether there is a chip at x,y
	 *
	**/
	public boolean hasChip(int x, int y){
		if (board[x][y] instanceof Chip){
			return true;
		}
		return false;
	}
	/**
	 *
	 *	Returns whether this chip is line of sight with Chip c
	 *	@param c is the chip for comparison
	 *	@return whether the chip is line of sight with c
	 *
	**/
	public boolean isLOS(Chip a, Chip b) {
		if (isTopBottom(a,b) || isLeftRight(a,b) || isLeftDiagonal(a,b) || isRightDiagonal(a,b)) {
			return true;
		}
		return false;
	}
	
	private boolean isTopBottom(Chip a, Chip b) {
		if (a.xPos() == b.xPos()) {
			return true;
		}
		return false;
	}
	
	private boolean isLeftRight(Chip a, Chip b) {
		if (a.yPos() == b.yPos()) {
			return true;
		}
		return false;
	}
	
	private boolean isLeftDiagonal(Chip a, Chip b) {
		int index = 0;
		while (index + xPos() < Constants.WIDTH) {
		
		}
	}
	
	private boolean isRightDiagonal(Chip c) {
	
	}
	/**
	 *
	 *	Returns whether this chip is between chips c and d.
	 *	@param c the first chip
	 *	@param c the second chip
	 *	@return whether this chip is between c and d.
	 *
	**/
	public boolean isBetween(Chip c, Chip d) {
	
	}
	/**
	 *
	 *	Returns the number of same-color neighbors this chip has.
	 *	@returns the number of neighbors of this chip's color.
	 *
	**/
	public int getSameColorNeighbors() {
	
	}
	/**
	 *
	 * Returns whether the game is over.
	 * @return the color of the winning player, or Constants.NULL_PLAYER if the game is not over.
	 *
	**/
	public int isGameOver();
