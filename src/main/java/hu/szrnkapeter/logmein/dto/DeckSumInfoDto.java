package hu.szrnkapeter.logmein.dto;

import java.util.Map;

import lombok.Data;

/**
 * Detailed informations about the cards sum.
 */
@Data
public class DeckSumInfoDto {

	private Long id;
	private Map<String, Integer> cardSum; 
}