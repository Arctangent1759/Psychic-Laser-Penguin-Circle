
package Board;

public class InvalidMoveException extends Exception{
    	/**
	 *
	 *	An exception thrown when an invalid move is attempted.
	 *
	**/
	public InvalidMoveException(){
		super();
	}
        /**
	 *	An exception thrown when an invalid move is attempted.
         *      Prints out message msg.
         *      @param message msg to return.
	**/
	public InvalidMoveException(String msg){
		super(msg);
	}
}
