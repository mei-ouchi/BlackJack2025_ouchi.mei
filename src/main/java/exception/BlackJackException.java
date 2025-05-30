package exception;

public class BlackJackException extends Exception{
	
	public BlackJackException(String message) {
		super(message);
	}
	
	public BlackJackException(String message, Throwable cause) {
		super(message, cause);
	}
	
}