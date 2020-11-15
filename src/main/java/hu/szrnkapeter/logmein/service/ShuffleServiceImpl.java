package hu.szrnkapeter.logmein.service;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import hu.szrnkapeter.logmein.type.CardGameErrorCode;
import hu.szrnkapeter.logmein.type.CardGameException;
import hu.szrnkapeter.logmein.util.Constants;

@Service
public class ShuffleServiceImpl implements ShuffleService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see hu.szrnkapeter.logmein.service.ShuffleService#shuffle(java.lang.String)
	 */
	@Override
	public String shuffle(String originalDeck) {
		if (originalDeck == null || originalDeck.isEmpty()) {
			throw new CardGameException(CardGameErrorCode.DECK_EMPTY);
		}

		List<String> cardList = Arrays.asList(StringUtils.split(originalDeck, Constants.COMMA));
		Collections.shuffle(cardList, new SecureRandom());
		return StringUtils.join(cardList, Constants.COMMA);
	}
}