	public class Board{
		
		protected Chip[][] board;

		/**
		 *
		 *	Constructs a new Board.
		 *
		**/
		public Board(){
			board = new Chip[8][8];

		}

		/**
		 *
		 *	Moves chip c to point x,y
		 *	@param c The chip to be moved.
		 *	@param x The x coordinate of the chip's destination
		 *	@param y The y coordinate of the chip's destination
		 *	@throws InvalidChipException if Chip c is not valid.
		 *	@throws IllegalChipMoveException if the chip is moved
		 * 			off the legal section of the board, three in a row, 
		 *			or into the wrong goal.
		 *
		**/
		public void moveChip(Chip c, int x, int y){
			board[x][y] = null;
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
		public void addChip(int x, int y) {

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
		public Chip getChip(int x, int y) throws ChipNotFoundException;

		/**
		 *
		 *	Tells whether a chip at x,y exists
		 *	@param x the target x coordinate
		 *	@param y the target y coordinate
		 *	@return whether there is a chip at x,y
		 *
		**/
		public boolean hasChip(int x, int y);

		/**
		 *
		 * Returns whether the game is over.
		 * @return the color of the winning player, or Constants.NULL_PLAYER if the game is not over.
		 *
		**/
		public int isGameOver();
	}

	/**
	 *
	 *	The chip ADT represents a single chip on the board.
	 *	Each chip knows its position on the board.
	 *
	**/