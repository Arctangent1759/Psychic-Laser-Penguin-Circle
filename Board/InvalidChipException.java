package board;

public class InvalidChipException extends Exception{
	/* InvalidNodeException.java */

/**
 *  Implements an Exception that signals an attempt to use an invalid ListNode.
 */
  protected InvalidChipException() {
    super();
  }

  protected InvalidNodeException(String s) {
    super(s);
  }
}
