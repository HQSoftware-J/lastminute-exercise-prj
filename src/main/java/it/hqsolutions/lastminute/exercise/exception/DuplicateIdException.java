package it.hqsolutions.lastminute.exercise.exception;

/**
 * Description: exception thrown when user try to insert a duplicate item
 * 
 * @author giorgio
 *
 */
public class DuplicateIdException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Parameterless Constructor
	public DuplicateIdException() {
	}

	// Constructor that accepts a message
	public DuplicateIdException(String message) {
		super(message);
	}
}
