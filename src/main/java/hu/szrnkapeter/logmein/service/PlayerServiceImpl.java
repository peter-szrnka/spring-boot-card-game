package hu.szrnkapeter.logmein.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.szrnkapeter.logmein.converter.PlayerConverter;
import hu.szrnkapeter.logmein.dto.PlayerDto;
import hu.szrnkapeter.logmein.entity.GameEntity;
import hu.szrnkapeter.logmein.entity.PlayerEntity;
import hu.szrnkapeter.logmein.repository.PlayerRepository;
import hu.szrnkapeter.logmein.type.CardGameErrorCode;
import hu.szrnkapeter.logmein.type.CardGameException;

@Service
public class PlayerServiceImpl extends AbstractService<PlayerEntity, PlayerRepository> implements PlayerService {
	
	private static final Logger LOG = LoggerFactory.getLogger(PlayerServiceImpl.class);

	private final GameRepositoryHelper helper;
	private final PlayerConverter converter;

	@Autowired
	public PlayerServiceImpl(PlayerRepository repository, GameRepositoryHelper repositoryHelper, PlayerConverter playerConverter) {
		super(repository, CardGameErrorCode.PLAYER_NOT_FOUND);
		helper = repositoryHelper;
		converter = playerConverter;
	}
	
	/*
	 * (non-Javadoc)
	 * @see hu.szrnkapeter.logmein.service.PlayerService#create(hu.szrnkapeter.logmein.dto.PlayerDto)
	 */
	@Override
	public Long create(PlayerDto dto) {
		GameEntity game = helper.getGameEntityById(dto.getGameId());
		
		if(game == null) {
			throw new CardGameException(CardGameErrorCode.GAME_NOT_FOUND);
		}
		
		Optional<PlayerEntity> result = repository.findByName(dto.getName());
		
		if(result.isPresent()) {
			throw new CardGameException(CardGameErrorCode.PLAYER_ALREADY_EXISTS);
		}
		
		PlayerEntity newPlayer = repository.save(converter.createNewPlayer(game, dto));
		LOG.info("New player(Id={}) created for game={}", newPlayer.getId(), dto.getGameId());
		return newPlayer.getId();
	}

	/*
	 * (non-Javadoc)
	 * @see hu.szrnkapeter.logmein.service.PlayerService#delete(java.lang.Long)
	 */
	@Override
	public void delete(Long id) {
		repository.delete(getEntityById(id));
		LOG.info("Game deleted by id={}", id);
	}

	/*
	 * (non-Javadoc)
	 * @see hu.szrnkapeter.logmein.service.PlayerService#getById(java.lang.Long)
	 */
	@Override
	public PlayerDto getById(Long id) {
		return converter.toDto(getEntityById(id));
	}

	/*
	 * (non-Javadoc)
	 * @see hu.szrnkapeter.logmein.service.PlayerService#getByName(java.lang.String)
	 */
	@Override
	public PlayerDto getByName(String name) {
		Optional<PlayerEntity> result = repository.findByName(name);
		
		if(!result.isPresent()) {
			throw new CardGameException(CardGameErrorCode.PLAYER_NOT_FOUND);
		}
		
		return converter.toDto(result.get());
	}
}