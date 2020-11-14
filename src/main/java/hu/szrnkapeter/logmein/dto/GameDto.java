package hu.szrnkapeter.logmein.dto;

import java.time.LocalDate;

import hu.szrnkapeter.logmein.type.GameStatus;
import lombok.Data;

@Data
public class GameDto {

	private Long id;
	private String title;
	private String creatorName;
	private LocalDate creationDate = LocalDate.now();
	private GameStatus orderStatus;
}