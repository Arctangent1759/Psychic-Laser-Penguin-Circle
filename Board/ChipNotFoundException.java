public class ChipNotFoundException extends Exception{
	/* ChipNotFoundException.java */


/**
 *  Implements an Exception that says a chip cannot be found.
 */
  protected ChipNotFoundException() {
    super();
  }

  protected ChipNotFoundException(String msg) {
    super(msg);
  }
}
