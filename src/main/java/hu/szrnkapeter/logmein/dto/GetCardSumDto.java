package hu.szrnkapeter.logmein.dto;

import java.util.List;

import com.google.common.collect.Lists;

import hu.szrnkapeter.logmein.service.GameService;
import lombok.Data;

/**
 * Main dto of {@link GameService#getCardSum(Long)}
 */
@Data
public class GetCardSumDto {

	private List<DeckSumInfoDto> decks = Lists.newArrayList();
}