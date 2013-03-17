                                                                     
                                             
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
	 *	Returns whether this Chip a is line of sight with Chip b
	 *	@param a is the chip of reference.
	 *	@param b is the chip for comparison
	 *	@return whether the chip is line of sight with b
	 *
	**/
	public boolean isLOS(Chip a, Chip b) {
		if (isTopBottom(a,b) || isLeftRight(a,b) || isDiagonal(a,b)) {
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
		if (b.xPos() - a.xPos() == 0) {
			return false;
		}
		else {
			double slope = (b.yPos()-a.yPos()) / (b.xPos() - a.xPos()); 
			if (slope == -1) {
				return true;
			}
			return false;
		}
	}
	
	private boolean isRightDiagonal(Chip a, Chip b) {
		if (b.xPos() - a.xPos() == 0) {
				return false;
		}
		else {
			double slope = (b.yPos()-a.yPos()) / (b.xPos() - a.xPos()); 
			if (slope == 1) {
				return true;
			}
			return false;
		}
	}
	
	/**
	 *
	 *	Returns whether chip a is between chips b and c.
	 *	@param a the reference chip
	 *	@param b the second chip
	 *	@param c the third chip
	 *	@return whether this chip is between a and b.
	 *
	**/
	public boolean isBetween(Chip a, Chip b, Chip c) {
		if (isTopBottom(a,b) && isTopBottom(b,c)) {
			if ( (b.yPos()-a.yPos() < 0 && c.yPos()-a.yPos() > 0) 
				|| (b.yPos()-a.yPos() > 0 && c.yPos()-a.yPos() < 0)) {
				return true;
			}
			return false;
		}
		else if (isLeftRight(a,b) && isLeftRight(b,c)) {
			if ( (b.xPos()-a.xPos() < 0 && c.xPos()-a.xPos() > 0) 
				|| (b.xPos()-a.xPos() > 0 && c.xPos()-a.xPos() < 0)) {
				return true;
			}
			return false;
		}
		
		else if (isLeftDiagonal(a,b) && isLeftDiagonal(b,c)) {
			if ( (b.xPos()-a.xPos() < 0 && c.xPos()-a.xPos() > 0) 
				|| (b.xPos()-a.xPos() > 0 && c.xPos()-a.xPos() < 0)) {
				return true;
			}
			return false;
		}
		
		else if (isRightDiagonal(a,b) && isRightDiagonal(b,c)) {
			if ( (b.xPos()-a.xPos() < 0 && c.xPos()-a.xPos() > 0) 
				|| (b.xPos()-a.xPos() > 0 && c.xPos()-a.xPos() < 0)) {
				return true;
			}
			return false;
		}
		return false;
	}
	/**
	 *
	 *	Returns the number of same-color neighbors this chip has.
	 *	@returns the number of neighbors of this chip's color.
	 *
	**/
	public int getSameColorNeighbors() {
		return 0;
	}
	/**
	 *
	 * Returns whether the game is over.
	 * @return the color of the winning player, or Constants.NULL_PLAYER if the game is not over.
	 *
	**/
	public int isGameOver();
