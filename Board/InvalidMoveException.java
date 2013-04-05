
package Board;

public class InvalidMoveException extends Exception{
    	/**
	 *
	 *	An exception thrown when a player attempts to create a chip at an invalid location
	 *
	**/
	public InvalidMoveException(){
		super();
	}
        /**
	 *	An exception thrown when a player attempts to create a chip at an invalid location
         *      Prints out message msg.
         *      @param message msg to return.
	**/
	public InvalidMoveException(String msg){
		super(msg);
	}
}
