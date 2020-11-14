package hu.szrnkapeter.logmein.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hu.szrnkapeter.logmein.entity.GameEntity;
import hu.szrnkapeter.logmein.repository.GameRepository;
import hu.szrnkapeter.logmein.type.CardGameErrorCode;

@Component
public class GameRepositoryHelper extends AbstractService<GameEntity, GameRepository> {

	@Autowired
	public GameRepositoryHelper(GameRepository gameRepository) {
		super(gameRepository, CardGameErrorCode.GAME_NOT_FOUND);
	}
	
	public GameEntity getGameEntityById(Long id) {
		return getEntityById(id);
	}
}