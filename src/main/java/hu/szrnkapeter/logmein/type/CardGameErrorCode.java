package hu.szrnkapeter.logmein.type;

/**
 * Enumeration to hold all kind of error codes which can be thrown during a game.
 */
public enum CardGameErrorCode {

	GENERAL_ERROR(1, "General error occured!"),
	GAME_NOT_FOUND(2, "Game not found!"),
	DECK_EMPTY(3, "Deck is empty!"),
	PLAYER_NOT_FOUND(4, "Player not found!"),
	NO_DECK_FOR_GAME(5, "Card deck not found for game!"),
	PLAYER_ALREADY_EXISTS(6, "Player already exists!");
	
	CardGameErrorCode(int c, String m) {
		code = c;
		message = m;
	}
	
	private int code;
	private String message;
	
	public int getCode() {
		return code;
	}
	
	public String getMessage() {
		return message;
	}
}