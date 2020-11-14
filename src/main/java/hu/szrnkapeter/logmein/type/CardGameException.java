package hu.szrnkapeter.logmein.type;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardGameException extends RuntimeException {

	private static final long serialVersionUID = 4406425464308277293L;
	
	private CardGameErrorCode errorCode;
	
	public CardGameException(CardGameErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}