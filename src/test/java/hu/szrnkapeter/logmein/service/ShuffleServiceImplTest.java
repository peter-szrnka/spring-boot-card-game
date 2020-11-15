package hu.szrnkapeter.logmein.service;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;

import hu.szrnkapeter.logmein.service.shuffle.CustomShuffleServiceImpl;
import hu.szrnkapeter.logmein.service.shuffle.ShuffleService;
import hu.szrnkapeter.logmein.service.shuffle.ShuffleServiceImpl;
import hu.szrnkapeter.logmein.type.CardGameErrorCode;
import hu.szrnkapeter.logmein.type.CardGameException;
import hu.szrnkapeter.logmein.util.Constants;

/**
 * Unittests of 
 * - {@link ShuffleServiceImpl}
 * - {@link CustomShuffleServiceImpl}
 */
@PowerMockIgnore("javax.management.*")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(PowerMockRunner.class)
public class ShuffleServiceImplTest {

	ShuffleServiceImpl service1 = new ShuffleServiceImpl();
	CustomShuffleServiceImpl service2 = new CustomShuffleServiceImpl();
	
	private void testWithExpectedError(ShuffleService service, String originalDeck) {
		try {
			service.shuffle(originalDeck);
		} catch(CardGameException e) {
			Assert.assertEquals("Wrong exception output!", e.getErrorCode(), CardGameErrorCode.DECK_EMPTY);
		}
	}
	
	@Test
	public void test01_inputIsNull() {
		testWithExpectedError(service1, null);
		testWithExpectedError(service2, null);
	}
	
	@Test
	public void test02_inputIsEmpty() {
		testWithExpectedError(service1, "");
		testWithExpectedError(service2, "");
	}
	
	@Test
	public void test03_success() {
		String response = service1.shuffle(Constants.LIST_ALL_CARD);
		Assert.assertNotEquals("Wrong response", Constants.LIST_ALL_CARD, response);
		
		String response2 = service2.shuffle(Constants.LIST_ALL_CARD);
		Assert.assertNotEquals("Wrong response", Constants.LIST_ALL_CARD, response2);
	}
}