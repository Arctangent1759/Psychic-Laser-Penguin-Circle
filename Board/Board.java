/**
 *
 *	Board represents a single game state. It knows the 
 *	position of each chip, and enforces the rules of the 
 *	game, throwing an exception when a violation is reached.
 *	
 *	Invariants:
 *  	1. Will throw an exception if a move is illegal.
 *  	2. No two chips in any given place.
 *  	3. Cannot be three chips adjacent to each other.
 *  	4. Cannot place more than 10 chips of the same color.
 *  	5. No chips in wrong goals.
 *  	6. No chips in corners.
 *  	7. The board will NOT CHANGE in the event of an exception
 *	
**/

package Board;

import Constants.Constants;
import DList.*;
import java.io.*;
import player.Move;


public class Board{	


	protected Chip[][] grid;
	protected int numWhite;
	protected int numBlack;

	/**
	 *      
	 *	Constructs a new Board.
	 *
	**/
	public Board() {
		grid = new Chip[Constants.BOARDWIDTH][Constants.BOARDHEIGHT];
		numWhite = 0;
		numBlack = 0;
	}

	/**
	 *
	 *	Board copy constructor
	 *	@param b the board to be copied
	 *
	**/
	public Board(Board b) {
		grid = new Chip[Constants.BOARDWIDTH][Constants.BOARDHEIGHT];
		numWhite = b.numWhite;
		numBlack = b.numBlack;
		for (int x = 0; x < Constants.BOARDWIDTH; x++){
			for (int y = 0; y < Constants.BOARDHEIGHT; y++){
				grid[x][y]=b.grid[x][y];
			}
		}

	}

	/**
	 *
	 *	Incorporates a move into the internal game board.
	 *	@param m is the move that the board is incorporating.
	 *	@param color is the color of the player performing the move.
	 *	@throws InvalidMoveException when a move makes a board invalid.
	 *
	**/
	public void doMove(Move m, int color) throws InvalidMoveException{
		switch (m.moveKind){
			case Move.ADD:
				addChip(color,m.x1,m.y1);
				break;
			case Move.STEP:
				moveChip(m.x2,m.y2,m.x1,m.y1,color);
				break;
			case Move.QUIT:
				break;
			default:
				throw new InvalidMoveException("Move type not recognized.");
		}
	}

	/**
	 *
	 *	Undo a move in the internal game board. 
	 *	Precondition: doMove has been called on the move first
	 *
	 *	@param m is the move that the board is incorporating.
	 *	@param color is the color of the player performing the move.
	 *	@throws InvalidMoveException when a move makes a board invalid.
	 *
	**/
	public void undoMove(Move m, int color) throws InvalidMoveException{
		switch (m.moveKind){
			case Move.ADD:
				removeChip(m.x1,m.y1);
				break;
			case Move.STEP:
				moveChip(m.x1,m.y1,m.x2,m.y2,color);
				break;
			case Move.QUIT:
				break;
			default:
				throw new InvalidMoveException("Move type not recognized.");
		}
	}



	/**
	 *
	 *	Moves chip c to point x,y
	 *	@param x1,x2 the coordinates of the chip to be moved.
	 *	@param x1,x2 the coordinates of the chip's destination.
	 *	@param color the color of the player placing the chip
	 *	@throws InvalidMoveException when a move makes a board invalid.
	 *
	**/
	protected void moveChip(int x1, int y1, int x2, int y2, int color) throws InvalidMoveException{
		if (numWhite!=Constants.MAX_CHIPS || numBlack!=Constants.MAX_CHIPS){
			throw new InvalidMoveException("Tried to move chip before maximum number of chips have been placed.");
		}

		//Make sure there's a chip at (x1,y1)
		if (!hasChip(x1,y1)){
			throw new InvalidMoveException("No chip to move from (" + x1 + "," + y1 + ") to (" + x2 + "," + y2 + ").");
		}

		if (getChip(x1,y1).getColor()!=color){
			throw new InvalidMoveException("Tried to move chip of the wrong color.");
		}

		//Make sure you do not move from x1,y1 to x1,y1
		if (x1==x2 && y1==y2){
			throw new InvalidMoveException("Tried to move chip to its own position.");
		}

		//Get the chip at (x1,y1)'s color
		int currColor = getChip(x1,y1).getColor();


		//If addChip succeeds, the number of chips will be one greater than it really is.
		if (currColor==Constants.BLACK){
			numBlack--;
		}else if (currColor==Constants.WHITE){
			numWhite--;
		}

		//Store the chip being moved.
		Chip swapChip = grid[x1][y1];
		grid[x1][y1]=null;

		//Copy the chip to (x2,y2). If doing so throws an exception 
		//(x2,y2 is occupied, for instace), the exception is passed 
		//to caller.
		try{
			addChip(currColor,x2,y2);
		}catch (InvalidMoveException e){
			//Revert the state of the board to before the move was made if a rule violation is found.
			if (currColor==Constants.BLACK){
				numBlack++;
			}else if (currColor==Constants.WHITE){
				numWhite++;
			}
			//Replace the moved chip.
			grid[x1][y1]=swapChip;
			throw e;
		}

	}


	/**
	 *
	 *	Creates chip at x,y.
	 *	@param color the color of the chip to be
	 *		   created
	 *	@param x the destination x coordinate
	 *	@param x the destination x coordinate
	 *	@param y the destination y coorinate
	 *
	**/
	protected void addChip(int color, int x, int y) throws InvalidMoveException{
		//Update the number of chips.
		if (color == Constants.WHITE) {
			numWhite++;
		}else if (color == Constants.BLACK) {
			numBlack++;
		}

		//Make sure x,y is on the board
		if (x < 0 || x >= Constants.BOARDWIDTH || y < 0 || y >= Constants.BOARDWIDTH){
			if (color == Constants.WHITE) {
				numWhite--;
			}else if (color == Constants.BLACK) {
				numBlack--;
			}
			throw new InvalidMoveException("Attempted to place chip off board. Coordinates " + x + "," + y);
		}

		//Enforce the chip limit
		if (color == Constants.WHITE && numWhite>10) {
			numWhite--;
			throw new InvalidMoveException("Too many white chips.");
		}
		else if (color == Constants.BLACK && numBlack>10) {
			numBlack--;
			throw new InvalidMoveException("Too many black chips.");
		}
		//Enforce chip placement rule 3
		if (hasChip(x,y)){
			if (color == Constants.WHITE) {
				numWhite--;
			}else if (color == Constants.BLACK) {
				numBlack--;
			}
			throw new InvalidMoveException("There is already a chip at (" + x+","+y+").");
		}

		//Create the new chip
		grid[x][y]=new Chip(color);
		

		//Enforce chip placement rules 1, 2, and 4. These are rules dependent on board state.
		if (!isValid()){
			grid[x][y]=null; //Revert the board if the move invalidates the board.
			if (color == Constants.WHITE) {
				numWhite--;
			}else if (color == Constants.BLACK) {
				numBlack--;
			}
			throw new InvalidMoveException("Placement rule violated.");
		}
	}

	/**
	 *
	 *	Removes chip at x,y.
	 *
	 *	@param x the x coordinate
	 *	@param y the y coordinate
	 *  @return the chip.
	 *
	**/
	protected void removeChip(int x, int y){
		if (getChip(x,y).color==Constants.BLACK){
			numBlack--;
		}else if(getChip(x,y).color==Constants.WHITE){
			numWhite--;
		}
		grid[x][y]=null;
	}

        
	/**
	 *
	 *	Gets the chip at x,y.
	 *	@param x the target x coordinate
	 *	@param y the target y coordinate
	 *	@return the Chip at x,y, or null if there is no chip.
	 *
	**/
	public Chip getChip(int x, int y){
		return grid[x][y];
	}

	/**
	 * @return the the number of black chips on the board
	**/
	public int numBlack(){
		return numBlack;
	}

	/**
	 * @return the the number of white chips on the board
	**/
	public int numWhite(){
		return numWhite;
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
		return grid[x][y]!=null;
	}
        
	/**
	 *
	 * Checks for valid chip location.
	 * 4 rules:
	 * 1. Cannot place chips in corners.
	 * 2. Cannot place in wrong goal.
	 * 3. Cannot place three in contact with each other.
	 * @return boolean that states if the chip location is valid.
	 *
	**/
	protected boolean isValid(){
		//Enforce chip placement rule 1. Cannot place in corners.
		if (hasChip(0,0) || hasChip(Constants.BOARDWIDTH-1,0) || hasChip(0,Constants.BOARDHEIGHT-1) || hasChip(Constants.BOARDWIDTH-1,Constants.BOARDHEIGHT-1)){
			return false;
		}
		//Enforce chip placement rule 2. Cannot place in wrong goal.
		for (int x = 0; x < Constants.BOARDWIDTH; x++){
			if (hasChip(x,0) && getChip(x,0).color==Constants.WHITE){
				return false;
			}
			if (hasChip(x,Constants.BOARDHEIGHT-1) && getChip(x,Constants.BOARDHEIGHT-1).color==Constants.WHITE){
				return false;
			}
		}
		for (int y = 0; y < Constants.BOARDHEIGHT; y++){
			if (hasChip(0,y) && getChip(0,y).color==Constants.BLACK){
				return false;
			}
			if (hasChip(Constants.BOARDWIDTH-1,y) && getChip(Constants.BOARDWIDTH-1,y).color==Constants.BLACK){
				return false;
			}
		}

		//Enforce chip placement rule 4. Cannot place three in contact.
		for (int x = 0; x < Constants.BOARDWIDTH; x++){
			for (int y = 0; y < Constants.BOARDHEIGHT; y++){
				if (getSameColorNeighbors(x,y)>=2){
					return false;
				}
			}
		}
		return true;
	}

	/**
	 *
	 *	Returns the number of same-color neighbors this chip has.
         *      @param x refers to the x-coordinate.
         *      @param y refers to the y coordinate.
	 *	@returns the number of neighbors of this chip's color.
	 *
	**/
	private int getSameColorNeighbors(int x, int y) {
		if (!hasChip(x,y)){
			return 0;
		}
		int total = 0;
		int currColor = grid[x][y].getColor();
		//Check the top left corner
		if (x!=0 && y!=0 && hasChip(x-1,y-1) && grid[x-1][y-1].getColor()==currColor){
			total+=1;
		}
		//Check left
		if (x!=0 && hasChip(x-1,y) && grid[x-1][y].getColor()==currColor){
			total+=1;
		}
		//Check up
		if (y!=0 && hasChip(x,y-1) && grid[x][y-1].getColor()==currColor){
			total+=1;
		}
		//Check the bottom right corner
		if (x!=Constants.BOARDWIDTH-1 && y!=Constants.BOARDHEIGHT-1 && hasChip(x+1,y+1) && grid[x+1][y+1].getColor()==currColor){
			total+=1;
		}
		//Check right
		if (x!=Constants.BOARDWIDTH-1 && hasChip(x+1,y) && grid[x+1][y].getColor()==currColor){
			total+=1;
		}
		//Check down
		if (y!=Constants.BOARDHEIGHT-1 && hasChip(x,y+1) && grid[x][y+1].getColor()==currColor){
			total+=1;
		}
		//Check the bottom left corner
		if (x!=0 && y!=Constants.BOARDHEIGHT-1 && hasChip(x-1,y+1) && grid[x-1][y+1].getColor()==currColor){
			total+=1;
		}
		//Check the top right corner
		if (x!=Constants.BOARDWIDTH-1 && y!=0 && hasChip(x+1,y-1) && grid[x+1][y-1].getColor()==currColor){
			total+=1;
		}
		return total;
	}

	/**
	 *
	 * Returns whether the game is over.
	 * @return the color of the winning player, or Constants.NULL_PLAYER if the game is not over.
	 *
	**/
	public int getWinner(){
		DList<Net> networks = getLongestNetworks();
		while (!networks.isEmpty()){
			Net network = networks.pop();
			if (network.isComplete()){
				return network.getPlayer();
			}
		}
		return Constants.NULL_PLAYER;
	}

                
	/**
	 *
	 * Returns a string representation of the board. 
	 * . is empty, X is BLACK, O is WHITE.
	 * @return a string.
	 *
	**/

	public String toString(){
		String out="";
		for (int y = 0; y < Constants.BOARDHEIGHT; y++){
			for (int x = 0; x < Constants.BOARDWIDTH; x++){
				if (!hasChip(x,y)){
					out+=".";
				}else if (getChip(x,y).color==Constants.BLACK){
					out+="X";
				}else if (getChip(x,y).color==Constants.WHITE){
					out+="O";
				}else{
					Constants.print("This should never happen (at Board.toString)");
				}
			}
			out+="\n";
		}
		return out;
	}

	/**
	 *
	 * Returns a list of the longest networks.
	 * @return a DList of networks
	 *
	**/

	public DList<Net> getLongestNetworks(){
		DList<Net> out = new DList<Net>();
		//Iterate down the goal rows. For each chip in the goal rows, find all networks (partial or full) touching said chip.
		for (int x = 0; x < Constants.BOARDWIDTH;x++){
			//Top goal
			if (hasChip(x,0)){
				//Append the networks involving each goal chip to the list
				out.append(expandLongestNetFromChip(x,0,0,0,x,0));
			}
			//Bottom goal
			if (hasChip(x,Constants.BOARDWIDTH-1)){
				//Append the networks involving each goal chip to the list
				out.append(expandLongestNetFromChip(x,Constants.BOARDWIDTH-1,0,0,x,Constants.BOARDWIDTH-1));
			}
		}
		//Iterate down the goal rows. For each chip in the goal rows, find all networks touching said chip.
		for (int y = 0; y < Constants.BOARDHEIGHT;y++){
			//Left goal
			if (hasChip(0,y)){
				//Append the networks involving each goal chip to the list
				out.append(expandLongestNetFromChip(0,y,0,0,0,y));
			}
			//Right goal
			if (hasChip(Constants.BOARDHEIGHT-1,y)){
				//Append the networks involving each goal chip to the list
				out.append(expandLongestNetFromChip(Constants.BOARDHEIGHT-1,y,0,0,Constants.BOARDHEIGHT-1,y));
			}
		
		}
		return out;
	}

                
	/**
	 *
	 * Finds the longest network.
	 * @param x is x value of the chip.
	 * @param y is the y value of the chip.
	 * @param last_direction is the last_direction the chip went in.
	 * @param depth is referring to how far the object goes down.
	 * @param origin_x is referring to the x-coordinate the original goal was in.
	 * @param origin_y is referring to the y-coordinate the original goal was in.
	 * @return a DList of networks
	 *
	**/

	private DList<Net> expandLongestNetFromChip(int x, int y, int last_direction, int depth, int origin_x, int origin_y){
		//Base cases:
		//	1. x,y is in the origin goal, return empty.
		//	2. x,y is in the target goal, return path.
		//Recursive cases:
		//	1. x,y is not any of the base cases.
		//	2. x,y is a dead end. append path.
		
		Chip c = getChip(x,y);
		DList<Net> out = new DList<Net>();
		boolean isDeadEnd=true;

		//Check Base Cases
		if (!(x==origin_x && y==origin_y)) //Add exception for first node, since it must be in a goal row.
		{
			if (c.getColor() == Constants.WHITE && (x==0 || x==Constants.BOARDWIDTH-1)){
				if (x==origin_x){
					out.push(new Net(c.getColor(),depth,false));
					return out;
				}else{
					out.push(new Net(c.getColor(),depth+1,depth+1>=6));
					return out;
				}
			}
			if (c.getColor() == Constants.BLACK && (y==0 || y==Constants.BOARDHEIGHT-1)){
				if (y==origin_y){
					out.push(new Net(c.getColor(),depth,false));
					return out;
				}else{
					out.push(new Net(c.getColor(),depth+1,depth+1>=6));
					return out;
				}
			}

		}

		c.visited=true;
		//Left-right, direction code 1
		if (last_direction!=1){ //Make sure the network turns
			for (int s = 1; s <= Constants.BOARDWIDTH-1-x; s++){
				if (hasChip(x+s,y)){
					if (getChip(x+s,y).color==c.color && !getChip(x+s,y).visited){
						out.append(expandLongestNetFromChip(x+s,y,1,depth+1,origin_x,origin_y));
						isDeadEnd=false;
						break;
					}else{
						break;
					}
				}
			}
			for (int s = -1; s >= -x; s--){
				if (hasChip(x+s,y)){
					if (getChip(x+s,y).color==c.color && !getChip(x+s,y).visited){
						out.append(expandLongestNetFromChip(x+s,y,1,depth+1,origin_x,origin_y));
						isDeadEnd=false;
						break;
					}else{
						break;
					}
				}
			}
		}

		//Up-down, direction code 2
		if (last_direction!=2){ //Make sure the network turns
			for (int s = 1; s <= Constants.BOARDHEIGHT-1-y; s++){
				if (hasChip(x,y+s)){
					if (getChip(x,y+s).color==c.color && !getChip(x,y+s).visited){
						out.append(expandLongestNetFromChip(x,y+s,2,depth+1,origin_x,origin_y));
						isDeadEnd=false;
						break;
					}else{
						break;
					}
				}
			}
			for (int s = -1; s >= -y; s--){
				if (hasChip(x,y+s)){
					if (getChip(x,y+s).color==c.color && !getChip(x,y+s).visited){
						out.append(expandLongestNetFromChip(x,y+s,2,depth+1,origin_x,origin_y));
						isDeadEnd=false;
						break;
					}else{
						break;
					}
				}
			}
		}

		//Ascending diagonal, direction code 3
		if (last_direction!=3){ //Make sure the network turns
			for (int s = 1; s <= Math.min(Constants.BOARDHEIGHT-1-y,Constants.BOARDWIDTH-1-x); s++){
				if (hasChip(x+s,y+s)){
					if (getChip(x+s,y+s).color==c.color && !getChip(x+s,y+s).visited){
						out.append(expandLongestNetFromChip(x+s,y+s,3,depth+1,origin_x,origin_y));
						isDeadEnd=false;
						break;
					}else{
						break;
					}
				}
			}
			for (int s = -1; s >= Math.max(-x,-y); s--){
				if (hasChip(x+s,y+s)){
					if (getChip(x+s,y+s).color==c.color && !getChip(x+s,y+s).visited){
						out.append(expandLongestNetFromChip(x+s,y+s,3,depth+1,origin_x,origin_y));
						isDeadEnd=false;
						break;
					}else{
						break;
					}
				}
			}
		}

		//Descending diagonal, direction code 4
		if (last_direction!=4){ //Make sure the network turns
			for (int s = 1; s <= Math.min(y,Constants.BOARDWIDTH-1-x); s++){
				if (hasChip(x+s,y-s)){
					if (getChip(x+s,y-s).color==c.color && !getChip(x+s,y-s).visited){
						out.append(expandLongestNetFromChip(x+s,y-s,4,depth+1,origin_x,origin_y));
						isDeadEnd=false;
						break;
					}else{
						break;
					}
				}
			}
			for (int s = -1; s >= Math.min(x,Constants.BOARDHEIGHT-1-y); s--){
				if (hasChip(x+s,y-s)){
					if (getChip(x+s,y-s).color==c.color && !getChip(x+s,y-s).visited){
						out.append(expandLongestNetFromChip(x+s,y-s,4,depth+1,origin_x,origin_y));
						isDeadEnd=false;
						break;
					}else{
						break;
					}
				}
			}
		}
		c.visited=false;
		if (isDeadEnd){
			out.push(new Net(c.getColor(),depth+1,false));
		}
		return out;
	}
}
