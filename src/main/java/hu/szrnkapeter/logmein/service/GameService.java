package hu.szrnkapeter.logmein.service;

import hu.szrnkapeter.logmein.dto.GameDataDto;
import hu.szrnkapeter.logmein.dto.GameDto;

/**
 * Game services.
 */
public interface GameService {

	/**
	 * Adds a new deck to the given game.
	 * 
	 * @param id
	 * @return
	 */
	Long addDeck(Long id);

	/**
	 * Creates a new game by the given parameters
	 * 
	 * @param dto
	 * @return
	 */
	Long create(GameDto dto);

	/**
	 * Deletes a game by id.
	 * 
	 * @param id
	 */
	void delete(Long id);
	
	/**
	 * Returns the details of a game by id.
	 * 
	 * @param id
	 * @return
	 */
	GameDto getById(Long id);

	/**
	 * Shuffles all decks of the given game.
	 * 
	 * @param id
	 */
	void shuffleDecks(Long id);

	/**
	 * Returns with all players data, and the value of their cards.
	 * 
	 * @param id
	 * @return
	 */
	GameDataDto getAllPlayers(Long id);

	/**
	 * Deals a card to a specific player.
	 * 
	 * @param id
	 * @param playerId
	 * @return
	 */
	void dealCard(Long id, Long playerId);
}