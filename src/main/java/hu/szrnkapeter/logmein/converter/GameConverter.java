package hu.szrnkapeter.logmein.converter;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.google.common.collect.Sets;

import hu.szrnkapeter.logmein.dto.GameDataDto;
import hu.szrnkapeter.logmein.dto.GameDto;
import hu.szrnkapeter.logmein.dto.PlayerSummaryDto;
import hu.szrnkapeter.logmein.entity.DeckEntity;
import hu.szrnkapeter.logmein.entity.GameEntity;
import hu.szrnkapeter.logmein.type.CardValue;

@Component
public class GameConverter {

	public GameEntity createNewEntity(GameDto dto) {
		GameEntity newEntity = new GameEntity();
		
		if(dto.getCreationDate() != null) {
			newEntity.setCreationDate(dto.getCreationDate());
		}

		newEntity.setCreatorName(dto.getCreatorName());
		
		if(dto.getOrderStatus() != null) {
			newEntity.setOrderStatus(dto.getOrderStatus());
		}

		newEntity.setTitle(dto.getTitle());
		return newEntity;
	}

	public GameDto toDto(GameEntity entity) {
		GameDto dto = new GameDto();
		dto.setCreationDate(entity.getCreationDate());
		dto.setCreatorName(entity.getCreatorName());
		dto.setId(entity.getId());
		dto.setOrderStatus(entity.getOrderStatus());
		dto.setTitle(entity.getTitle());
		return dto;
	}

	public DeckEntity createNewDeck(GameEntity game) {
		DeckEntity newDeck = new DeckEntity();
		newDeck.setAdditionDate(LocalDate.now());
		newDeck.setGame(game);
		
		return newDeck;
	}

	public GameDataDto toGameDataDto(GameEntity game) {
		GameDataDto dto = new GameDataDto();
		dto.setCreationDate(game.getCreationDate());
		dto.setCreatorName(game.getCreatorName());
		dto.setTitle(game.getTitle());
		dto.setOrderStatus(game.getOrderStatus());
		
		Set<PlayerSummaryDto> players = Sets.newHashSet();
		game.getPlayers().forEach(player -> {
			int playerCardsValue = player.getCards().stream() //
					.map(card -> CardValue.getValueByName(card.getCard())) //
					.filter(item -> item != null) //
					.mapToInt(i -> i.intValue()) //
					.sum();

			PlayerSummaryDto playerSummaryDto = new PlayerSummaryDto();
			playerSummaryDto.setName(player.getName());
			playerSummaryDto.setCardsSumValue(playerCardsValue);
			
			players.add(playerSummaryDto);
		});

		dto.setPlayers(players);
		return dto;
	}
}