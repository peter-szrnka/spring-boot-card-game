package hu.szrnkapeter.logmein.dto;

import lombok.Data;

/**
 * Data transfer object for holding error related informations.
 */
@Data
public class ErrorResponseDto {

	private boolean success;
	private String message;
	private int errorCode;
}