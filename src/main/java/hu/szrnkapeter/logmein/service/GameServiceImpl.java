package hu.szrnkapeter.logmein.service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.szrnkapeter.logmein.converter.GameConverter;
import hu.szrnkapeter.logmein.dto.DeckCountInfoDto;
import hu.szrnkapeter.logmein.dto.DeckSumInfoDto;
import hu.szrnkapeter.logmein.dto.GameDataDto;
import hu.szrnkapeter.logmein.dto.GameDto;
import hu.szrnkapeter.logmein.dto.GetCardCountDto;
import hu.szrnkapeter.logmein.dto.GetCardSumDto;
import hu.szrnkapeter.logmein.entity.DeckEntity;
import hu.szrnkapeter.logmein.entity.GameEntity;
import hu.szrnkapeter.logmein.entity.PlayerCardEntity;
import hu.szrnkapeter.logmein.entity.PlayerEntity;
import hu.szrnkapeter.logmein.repository.DeckRepository;
import hu.szrnkapeter.logmein.repository.GameRepository;
import hu.szrnkapeter.logmein.repository.PlayerRepository;
import hu.szrnkapeter.logmein.service.shuffle.ShuffleService;
import hu.szrnkapeter.logmein.type.CardGameErrorCode;
import hu.szrnkapeter.logmein.type.CardGameException;
import hu.szrnkapeter.logmein.type.CardSuit;
import hu.szrnkapeter.logmein.type.CardValue;
import hu.szrnkapeter.logmein.util.Constants;

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
		AtomicBoolean cardSaved = new AtomicBoolean(false);

		game.getDecks().forEach(deck -> {
			LOG.info("Deck(id={}) will be processed", deck.getId()); 
			if(cardSaved.get() || deck.getCards().isEmpty()) {
				return;
			}

			// Select a card from the deck
			List<String> cardList = getCardList(deck);
			
			SecureRandom r = new SecureRandom();
			int selectedIndex = r.nextInt(cardList.size()-1);
			String selectedCard = cardList.get(selectedIndex);
			
			PlayerCardEntity playerCard = new PlayerCardEntity();
			playerCard.setCard(selectedCard);
			playerCard.setDeck(deck);
			playerCard.setPlayer(player);
			
			player.getCards().add(playerCard);
			playerRepository.save(player);

			// Remove the card from the deck
			cardList.remove(selectedIndex);
			
			deck.setCards(StringUtils.join(cardList, Constants.COMMA));
			deckRepository.save(deck);
			
			// This is necessary to avoid dealing cards from multiple decks
			cardSaved.set(true);
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

	/*
	 * (non-Javadoc)
	 * @see hu.szrnkapeter.logmein.service.GameService#getCardCount(java.lang.Long)
	 */
	@Override
	public GetCardCountDto getCardCount(Long id) {
		GameEntity game = getEntityById(id);
		
		GetCardCountDto dto = new GetCardCountDto();
		List<DeckCountInfoDto> decks = new ArrayList<>();
		
		game.getDecks().forEach(deck -> {
			LOG.info("Deck(id={}) will be processed", deck.getId()); 
			DeckCountInfoDto deckInfo = new DeckCountInfoDto();

			final Map<String, Integer> suitMap = new TreeMap<>();
			final Map<String, Integer> valueMap = new TreeMap<>();
			
			getCardList(deck).forEach(card -> {
				CardValue value = CardValue.getValueByName(card);
				
				// Count the suit
				int suitCount = suitMap.getOrDefault(value.getColor(), 0);
				suitMap.put(value.getColor(), suitCount + 1);
				
				// Count the values
				int valueCount = valueMap.getOrDefault(value.getLabel(), 0);
				valueMap.put(value.getLabel(), valueCount + 1);
			});

			deckInfo.setId(deck.getId());
			deckInfo.setSuitCount(suitMap.entrySet().stream()
					.sorted(Map.Entry.<String, Integer>comparingByKey(suitComparator))
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1,e2)->e1,LinkedHashMap::new)));
			deckInfo.setValueCount(valueMap.entrySet().stream()
					.sorted(Map.Entry.<String, Integer>comparingByKey(valueComparator))
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1,e2)->e1,LinkedHashMap::new)));
			decks.add(deckInfo);
		});

		dto.setDecks(decks);
		return dto;
	}

	/*
	 * (non-Javadoc)
	 * @see hu.szrnkapeter.logmein.service.GameService#getCardSum(java.lang.Long)
	 */
	@Override
	public GetCardSumDto getCardSum(Long id) {
		GameEntity game = getEntityById(id);
		
		GetCardSumDto dto = new GetCardSumDto();
		List<DeckSumInfoDto> decks = new ArrayList<>();

		game.getDecks().forEach(deck -> {
			LOG.info("Deck(id={}) will be processed", deck.getId()); 

			// Calculate the number of cards per color
			DeckSumInfoDto deckInfo = new DeckSumInfoDto();
			deckInfo.setId(deck.getId());
			List<String> cardList = getCardList(deck);
			Map<String, Integer> cardSum = new HashMap<>();

			cardList.forEach(card -> {
				CardValue value = CardValue.getValueByName(card);
				int currentNumber = cardSum.getOrDefault(value.getColor(), 0);
				cardSum.put(value.getColor(), currentNumber + 1);
			});

			deckInfo.setCardSum(cardSum);
			decks.add(deckInfo);
		});

		dto.setDecks(decks);
		return dto;
	}


	@Transactional
	@Override
	public void shuffleDecks(Long id) {
		GameEntity game = getEntityById(id);
		
		if(ListUtils.emptyIfNull(game.getDecks()).isEmpty()) {
			throw new CardGameException(CardGameErrorCode.NO_DECK_FOR_GAME);
		}
		
		game.getDecks().forEach(deck -> {
			LOG.info("Before shuffle: {}", deck.getCards());
			deck.setCards(shuffleService.shuffle(deck.getCards()));
			LOG.info("After shuffle: {}", deck.getCards());
		});
		
		repository.save(game);
	}
	
	private List<String> getCardList(DeckEntity deck) {
		return new ArrayList<>(Arrays.asList(StringUtils.split(deck.getCards(), Constants.COMMA)));
	}
	
	/**
	 * Comparator class to order suits by their specified order.
	 * For further details please check {@link CardSuit}
	 */
	private static Comparator<String> suitComparator = (String o1, String o2) -> {
		CardSuit suit1 = CardSuit.getByName(o1);
		CardSuit suit2 = CardSuit.getByName(o2);
		return suit1.getOrder() - suit2.getOrder();
	};

	/**
	 * Comparator class to order cards by their value.
	 * For further details please check {@link CardValue}
	 */
	private static Comparator<String> valueComparator = (String o1, String o2) -> {
		CardValue v1 = CardValue.getValueByLabel(o1);
		CardValue v2 = CardValue.getValueByLabel(o2);
		return v2.getValue() - v1.getValue();
	};
}