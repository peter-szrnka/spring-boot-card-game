package hu.szrnkapeter.logmein.service;

import java.util.HashSet;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;
import hu.szrnkapeter.logmein.converter.PlayerConverter;
import hu.szrnkapeter.logmein.dto.PlayerDto;
import hu.szrnkapeter.logmein.entity.GameEntity;
import hu.szrnkapeter.logmein.entity.PlayerEntity;
import hu.szrnkapeter.logmein.repository.PlayerRepository;
import hu.szrnkapeter.logmein.type.CardGameException;

/**
 * Unittests of 
 * - {@link PlayerServiceImpl}
 */
@PowerMockIgnore("javax.management.*")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(PowerMockRunner.class)
public class PlayerServiceImplTest {
	
	private PlayerRepository repository;
	private GameRepositoryHelper helper;
	private PlayerConverter converter;
	private PlayerServiceImpl service;

	@Before
	public void setUp() {
		repository = Mockito.mock(PlayerRepository.class);
		helper = Mockito.mock(GameRepositoryHelper.class);
		converter = Mockito.mock(PlayerConverter.class);
		service = new PlayerServiceImpl(repository, helper, converter);
	}
	
	@Test(expected = CardGameException.class)
	public void test01_create_gameNotFound() {
		service.create(createInputDto());
	}
	
	@Test(expected = CardGameException.class)
	public void test02_create_playerAlreadyExists() {
		GameEntity mockGameEntity = new GameEntity();
		Mockito.when(helper.getGameEntityById(ArgumentMatchers.eq(1L))).thenReturn(mockGameEntity);
		PlayerEntity playerEntity = new PlayerEntity();
		playerEntity.setId(2L);
		Mockito.when(repository.findByName(ArgumentMatchers.anyString())).thenReturn(Optional.of(playerEntity));
		
		service.create(createInputDto());
		Mockito.verify(helper).getGameEntityById(ArgumentMatchers.eq(1L));
	}
	
	@Test
	public void test03_create() {
		GameEntity mockGameEntity = new GameEntity();
		Mockito.when(helper.getGameEntityById(ArgumentMatchers.eq(1L))).thenReturn(mockGameEntity);
		PlayerEntity playerEntity = new PlayerEntity();
		playerEntity.setId(2L);
		Mockito.when(converter.createNewPlayer(ArgumentMatchers.any(GameEntity.class), ArgumentMatchers.any(PlayerDto.class))).thenReturn(playerEntity);
		Mockito.when(repository.save(ArgumentMatchers.any(PlayerEntity.class))).thenReturn(playerEntity);
		
		Long newPlayerId = service.create(createInputDto());
		Assert.assertEquals("Wrong player id!", Long.valueOf(2l), newPlayerId);
		
		Mockito.verify(helper).getGameEntityById(ArgumentMatchers.eq(1L));
		Mockito.verify(converter).createNewPlayer(ArgumentMatchers.any(GameEntity.class), ArgumentMatchers.any(PlayerDto.class));
		Mockito.verify(repository).save(ArgumentMatchers.any(PlayerEntity.class));
	}

	private PlayerDto createInputDto() {
		PlayerDto dto = new PlayerDto();
		dto.setId(1L);
		dto.setName("James Bond");
		dto.setGameId(1L);
		dto.setCards(new HashSet<>());
		return dto;
	}
}