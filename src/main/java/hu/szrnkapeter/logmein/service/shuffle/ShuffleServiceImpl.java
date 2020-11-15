package hu.szrnkapeter.logmein.service.shuffle;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import hu.szrnkapeter.logmein.util.Constants;

/**
 * Default shuffle strategy
 */
@ConditionalOnProperty(name = Constants.CONFIG_SHUFFLE_STRATEGY, havingValue = "core")
@Service
public class ShuffleServiceImpl extends AbstractShuffleService {

	/*
	 * (non-Javadoc)
	 * @see hu.szrnkapeter.logmein.service.shuffle.AbstractShuffleService#executeLogic(java.lang.String)
	 */
	protected String executeLogic(String originalDeck) {
		List<String> cardList = Arrays.asList(StringUtils.split(originalDeck, Constants.COMMA));
		Collections.shuffle(cardList, new SecureRandom());
		return StringUtils.join(cardList, Constants.COMMA);
	}
}