package hu.szrnkapeter.logmein.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import hu.szrnkapeter.logmein.dto.GameDataDto;
import hu.szrnkapeter.logmein.dto.GameDto;
import hu.szrnkapeter.logmein.service.GameService;
import hu.szrnkapeter.logmein.util.Constants;

@RestController
@RequestMapping(value = "/game")
public class GameController {

	private final GameService service;

	@Autowired
	public GameController(GameService gameService) {
		service = gameService;
	}

	@PutMapping(path= Constants.ID + "/addDeck", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Long> addDeck(@PathVariable("id") Long id) {
		return new ResponseEntity<>(service.addDeck(id), HttpStatus.OK);
	}

	@PutMapping(consumes = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> create(@RequestBody GameDto dto) {
		return new ResponseEntity<>(service.create(dto), HttpStatus.OK);
	}

	@PostMapping(path = Constants.ID + "/deal/{playerId}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> dealCard(@PathVariable("id") Long id, @PathVariable("playerId") Long playerId) {
		service.dealCard(id, playerId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping(path = Constants.ID, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		service.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(path = Constants.ID + "/players", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public @ResponseBody GameDataDto getAllPlayers(@PathVariable("id") Long id) {
		return service.getAllPlayers(id);
	}

	@GetMapping(path = Constants.ID, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public @ResponseBody GameDto getById(@PathVariable("id") Long id) {
		return service.getById(id);
	}
	
	@PostMapping(path= Constants.ID + "/shuffle", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Void> shuffleDecks(@PathVariable("id") Long id) {
		service.shuffleDecks(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}