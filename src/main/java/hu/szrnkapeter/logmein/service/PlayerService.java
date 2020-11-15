package hu.szrnkapeter.logmein.service;

import hu.szrnkapeter.logmein.dto.PlayerDto;

/**
 * Player services.
 */
public interface PlayerService {
	
	/**
	 * Adds a player to a given game.
	 * 
	 * @param dto
	 * @return
	 */
	Long create(PlayerDto dto);

	/**
	 * Deletes a player by id.
	 * 
	 * @param id
	 */
	void delete(Long id);

	/**
	 * Returns a player by the given id.
	 * 
	 * @param id
	 * @return
	 */
	PlayerDto getById(Long id);

	/**
	 * Returns a player by name.
	 * 
	 * @param name
	 * @return
	 */
	PlayerDto getByName(String name);
}