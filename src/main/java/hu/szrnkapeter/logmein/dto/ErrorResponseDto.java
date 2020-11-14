package hu.szrnkapeter.logmein.dto;

import lombok.Data;

@Data
public class ErrorResponseDto {

	private boolean success;
	private String message;
	private int errorCode;
}