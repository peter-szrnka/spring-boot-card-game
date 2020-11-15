package hu.szrnkapeter.logmein;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import hu.szrnkapeter.logmein.dto.ErrorResponseDto;
import hu.szrnkapeter.logmein.type.CardGameErrorCode;
import hu.szrnkapeter.logmein.type.CardGameException;

@ControllerAdvice
public class CardGameResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger LOG = LoggerFactory.getLogger(CardGameResponseEntityExceptionHandler.class);
	
	/**
	 * Handles business exceptions (CardGameException)
	 * 
	 * @param e Any kind of exception occured during game
	 * @param handlerMethod
	 * @return {@link ResponseEntity}
	 */
	@ExceptionHandler({ CardGameException.class })
	public ResponseEntity<ErrorResponseDto> handleCardGameException(final CardGameException e, final HandlerMethod handlerMethod) {
		return createResponseEntity(e.getErrorCode().getMessage(), e.getErrorCode().getCode());
	}

	/**
	 * Handles generic exceptions
	 * 
	 * @param e Any kind of exception occured during game
	 * @param handlerMethod
	 * @return {@link ResponseEntity}
	 */
	@ExceptionHandler({ Exception.class })
	public ResponseEntity<ErrorResponseDto> handleSomeException(final Exception e, final HandlerMethod handlerMethod) {
		LOG.error("Exception handled: {}", e.getMessage(), e);
		return createResponseEntity(e.getMessage(), CardGameErrorCode.GENERAL_ERROR.getCode());
	}
	
	private ResponseEntity<ErrorResponseDto> createResponseEntity(String message, int code) {
		ErrorResponseDto response = new ErrorResponseDto();
		response.setSuccess(false);
		response.setMessage(message);
		response.setErrorCode(code);

		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}