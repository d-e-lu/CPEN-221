package ca.ubc.ece.cpen221.mp4.commands;

/**
 * If a {@link Command} violates a rule specified in the handout, this exception
 * is thrown.
 *
 */
public class InvalidCommandException extends Exception {
	private static final long serialVersionUID = -7015554902803115397L;

	/**
	 * Constructor specifying what rule/invariant had been violated.
	 *
	 * @param msg
	 *            the error message
	 */
	public InvalidCommandException(String msg) {
		super(msg);
	}

}
