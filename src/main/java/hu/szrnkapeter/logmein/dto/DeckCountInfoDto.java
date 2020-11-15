package hu.szrnkapeter.logmein.dto;

import java.util.Map;

import lombok.Data;

/**
 * Detailed informations about the counted cards.
 */
@Data
public class DeckCountInfoDto {

	private Long id;
	private Map<String, Integer> suitCount;
	private Map<String, Integer> valueCount; 
}