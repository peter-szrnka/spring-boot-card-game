package hu.szrnkapeter.logmein.service.shuffle;

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