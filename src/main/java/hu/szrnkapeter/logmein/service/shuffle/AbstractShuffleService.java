package hu.szrnkapeter.logmein.service.shuffle;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.szrnkapeter.logmein.type.CardGameErrorCode;
import hu.szrnkapeter.logmein.type.CardGameException;

public abstract class AbstractShuffleService implements ShuffleService {
	
	protected static final Logger LOG = LoggerFactory.getLogger(AbstractShuffleService.class);

	/*
	 * (non-Javadoc)
	 * @see hu.szrnkapeter.logmein.service.shuffle.ShuffleService#shuffle(java.lang.String)
	 */
	@Override
	public String shuffle(String originalDeck) {
		if (StringUtils.isEmpty(originalDeck)) {
			throw new CardGameException(CardGameErrorCode.DECK_EMPTY);
		}

		return executeLogic(originalDeck);
	}

	/**
	 * Executes the concrete logic for the given implementation.
	 * 
	 * @param originalDeck
	 * @return
	 */
	protected abstract String executeLogic(String originalDeck);
}