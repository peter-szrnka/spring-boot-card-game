package hu.szrnkapeter.logmein.service.shuffle;

import java.security.SecureRandom;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import hu.szrnkapeter.logmein.util.Constants;

/**
 * Custom shuffle strategy
 */
@ConditionalOnProperty(name = Constants.CONFIG_SHUFFLE_STRATEGY, havingValue = "custom")
@Service
public class CustomShuffleServiceImpl extends AbstractShuffleService {

	/*
	 * (non-Javadoc)
	 * @see hu.szrnkapeter.logmein.service.shuffle.AbstractShuffleService#executeLogic(java.lang.String)
	 */
	@Override
	protected String executeLogic(String originalDeck) {
		String[] elements = StringUtils.split(originalDeck, Constants.COMMA);

		SecureRandom r = new SecureRandom(); 

        for (int i = elements.length-1; i > 0; i--) { 
            int j = r.nextInt(i); 

            String temp = elements[i]; 
            elements[i] = elements[j]; 
            elements[j] = temp; 
        } 
		
		return StringUtils.join(elements, Constants.COMMA);
	}
}