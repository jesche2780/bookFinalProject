package library.exception;

// Custom runtime exception for database-related errors
@SuppressWarnings("serial")
public class DbException extends RuntimeException {

	// Creates an exception with a descriptive message
	public DbException(String message) {
		super(message);
	}

	// Creates an exception wrapping another underlying cause
	public DbException(Throwable cause) {
		super(cause);
	}

	// Creates an exception with both a message and a cause
	public DbException(String message, Throwable cause) {
		super(message, cause);
	}
}
