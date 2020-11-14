package hu.szrnkapeter.logmein.service;

/**
 * Shuffle services
 * 
 */
public interface ShuffleService {

	/**
	 * Shuffles a game card deck.
	 * 
	 * @param originalDeck
	 * @return
	 */
	String shuffle(String originalDeck);
}