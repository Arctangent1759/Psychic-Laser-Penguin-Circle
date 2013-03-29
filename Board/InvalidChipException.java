package Board;

public class InvalidChipException extends Exception{
	/* InvalidNodeException.java */

/**
 *  Implements an Exception that signals an attempt to use an invalid chip
 */
  protected InvalidChipException() {
    super();
  }

  protected InvalidChipException(String s){
    super(s);
  }
}
