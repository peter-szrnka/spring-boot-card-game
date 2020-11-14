package hu.szrnkapeter.logmein.service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.SetUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.szrnkapeter.logmein.converter.GameConverter;
import hu.szrnkapeter.logmein.dto.GameDataDto;
import hu.szrnkapeter.logmein.dto.GameDto;
import hu.szrnkapeter.logmein.entity.DeckEntity;
import hu.szrnkapeter.logmein.entity.GameEntity;
import hu.szrnkapeter.logmein.entity.PlayerCardEntity;
import hu.szrnkapeter.logmein.entity.PlayerEntity;
import hu.szrnkapeter.logmein.repository.DeckRepository;
import hu.szrnkapeter.logmein.repository.GameRepository;
import hu.szrnkapeter.logmein.repository.PlayerRepository;
import hu.szrnkapeter.logmein.type.CardGameErrorCode;
import hu.szrnkapeter.logmein.type.CardGameException;

@Service
public class GameServiceImpl extends AbstractService<GameEntity, GameRepository> implements GameService {
	
	private static final Logger LOG = LoggerFactory.getLogger(GameServiceImpl.class);

	private PlayerRepository playerRepository;
	private DeckRepository deckRepository;
	private GameConverter converter;
	private ShuffleService shuffleService;

	@Autowired
	public GameServiceImpl(PlayerRepository playerRepository,GameRepository gameRepository, DeckRepository deckRepository, GameConverter converter, ShuffleService shuffleService) {
		super(gameRepository, CardGameErrorCode.GAME_NOT_FOUND);
		this.playerRepository = playerRepository;
		this.deckRepository = deckRepository;
		this.converter = converter;
		this.shuffleService = shuffleService;
	}
	
	/*
	 * (non-Javadoc)
	 * @see hu.szrnkapeter.logmein.service.GameService#addDeck(java.lang.Long)
	 */
	@Transactional
	@Override
	public Long addDeck(Long id) {
		GameEntity game = getEntityById(id);
		DeckEntity newDeck = deckRepository.save(converter.createNewDeck(game));
		LOG.info("New deck created to game={}! Id={}", game.getId(), newDeck.getId());
		return newDeck.getId();
	}

	/*
	 * (non-Javadoc)
	 * @see hu.szrnkapeter.logmein.service.GameService#create(hu.szrnkapeter.logmein.dto.GameDto)
	 */
	@Override
	public Long create(GameDto dto) {
		GameEntity entity = repository.save(converter.createNewEntity(dto));
		LOG.info("New game created! Id={}", entity.getId());
		return entity.getId();
	}

	/*
	 * (non-Javadoc)
	 * @see hu.szrnkapeter.logmein.service.GameService#dealCard(java.lang.Long, java.lang.Long)
	 */
	@Transactional
	@Override
	public void dealCard(Long id, Long playerId) {
		GameEntity game = getEntityById(id);
		
		PlayerEntity player = getPlayerById(playerId);
		
		game.getDecks().forEach(deck -> {
			if(deck.getCards().isEmpty()) {
				return;
			}

			// Select a card from the deck
			List<String> cardList = new ArrayList<>(Arrays.asList(StringUtils.split(deck.getCards(), ",")));
			
			SecureRandom r = new SecureRandom();
			int selectedIndex = r.nextInt(cardList.size());
			String selectedCard = cardList.get(selectedIndex);
			
			PlayerCardEntity playerCard = new PlayerCardEntity();
			playerCard.setCard(selectedCard);
			playerCard.setDeck(deck);
			playerCard.setPlayer(player);
			
			player.getCards().add(playerCard);
			playerRepository.save(player);

			// Remove the card from the deck
			cardList.remove(selectedIndex);
			
			deck.setCards(StringUtils.join(cardList, ","));
			deckRepository.save(deck);
		});
	}

	private PlayerEntity getPlayerById(Long playerId) {
		Optional<PlayerEntity> result = playerRepository.findById(playerId);

		if(!result.isPresent()) {
			throw new CardGameException(CardGameErrorCode.PLAYER_NOT_FOUND);
		}
		
		return result.get();
	}

	/*
	 * (non-Javadoc)
	 * @see hu.szrnkapeter.logmein.service.GameService#delete(java.lang.Long)
	 */
	@Override
	public void delete(Long id) {
		repository.delete(getEntityById(id));
		LOG.info("Game deleted by id={}", id);
	}

	/*
	 * (non-Javadoc)
	 * @see hu.szrnkapeter.logmein.service.GameService#getAllPlayers(java.lang.Long)
	 */
	@Override
	public GameDataDto getAllPlayers(Long id) {
		return converter.toGameDataDto(getEntityById(id));
	}

	/*
	 * (non-Javadoc)
	 * @see hu.szrnkapeter.logmein.service.GameService#getById(java.lang.Long)
	 */
	@Override
	public GameDto getById(Long id) {
		return converter.toDto(getEntityById(id));
	}

	@Transactional
	@Override
	public void shuffleDecks(Long id) {
		GameEntity game = getEntityById(id);
		
		if(SetUtils.emptyIfNull(game.getDecks()).isEmpty()) {
			throw new CardGameException(CardGameErrorCode.NO_DECK_FOR_GAME);
		}
		
		game.getDecks().forEach(deck -> {
			LOG.info("Before shuffle: {}", deck.getCards());
			deck.setCards(shuffleService.shuffle(deck.getCards()));
			LOG.info("After shuffle: {}", deck.getCards());
		});
		
		repository.save(game);
	}
}