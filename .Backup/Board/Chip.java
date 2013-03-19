/**
 *
 *	The chip ADT represents a single chip on the board.
 *	Each chip knows its position on the board.
 *
**/
public class Chip{
	/**
	 *
	 *	Constructs a new chip on board b.
	 *	@param b specifies the board that the chip belongs to
	**/
	private Board board;
	private int color;
	private int xPos;
	private int yPos;
	
	public Chip(Board b, color c, int x, int y) {
		board = b;
		color = c;
		xPos = x;
		yPos = y;
	}
	/**
	 *
	 *	Get the color of the chip
	 *	@return the color of the chip, defined in Constants
	 *
	 **/
	public int getColor() {
		return color;
	}
	
	public int XPos() {
		return xPos;
	}
	
	public int YPos() {
		return yPos;
	}
}
