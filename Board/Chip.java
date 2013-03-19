/**
 *
 *	The chip ADT represents a single chip on the board.
 *
**/

package Board;

public class Chip{
	protected int color;

	/**
	 *
	 *	Constructs a new chip on board b.
	 *	@param b specifies the board that the chip belongs to
	 *
	**/
	public Chip(int color) {
		this.color = color;
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
