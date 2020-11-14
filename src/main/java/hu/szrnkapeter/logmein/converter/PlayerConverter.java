package hu.szrnkapeter.logmein.converter;

import java.util.stream.Collectors;

import org.apache.commons.collections4.SetUtils;
import org.springframework.stereotype.Component;

import com.google.common.collect.Sets;

import hu.szrnkapeter.logmein.dto.PlayerDto;
import hu.szrnkapeter.logmein.entity.GameEntity;
import hu.szrnkapeter.logmein.entity.PlayerEntity;

@Component
public class PlayerConverter {

	public PlayerDto toDto(PlayerEntity player) {
		PlayerDto dto = new PlayerDto();
		dto.setId(player.getId());
		dto.setName(player.getName());
		dto.setCards(SetUtils.emptyIfNull(player.getCards()).stream() //
				.map(playerCard -> playerCard.getCard()) //
				.collect(Collectors.toSet()));
		dto.setGameId(player.getGame() == null ? null : player.getGame().getId());
		return dto;
	}

	public PlayerEntity createNewPlayer(GameEntity game, PlayerDto dto) {
		PlayerEntity entity = new PlayerEntity();
		entity.setCards(Sets.newHashSet());
		entity.setGame(game);
		entity.setName(dto.getName());
		return entity;
	}
}