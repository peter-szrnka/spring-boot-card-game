package hu.szrnkapeter.logmein.dto;

import java.time.LocalDate;
import java.util.Set;

import hu.szrnkapeter.logmein.type.GameStatus;
import lombok.Data;

@Data
public class GameDataDto {

	private String title;
	private String creatorName;
	private LocalDate creationDate;
	private GameStatus orderStatus;
	private Set<PlayerSummaryDto> players;
}