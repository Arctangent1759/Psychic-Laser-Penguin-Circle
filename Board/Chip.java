/**
 *
 *	The chip data container represents a single chip on the board.
 *
**/

package Board;

public class Chip{
	protected int color;
	protected boolean visited;

	/**
	 *
	 *	Constructs a new chip on board b.
	 *	@param b specifies the board that the chip belongs to
	 *
	**/
	public Chip(int color) {
		this.color = color;
		this.visited=false;
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
}
