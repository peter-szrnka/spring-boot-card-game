package hu.szrnkapeter.logmein.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Summary about a player.
 */
@Getter
@Setter
public class PlayerSummaryDto {

	private String name;
	private int cardsSumValue;
}