package hu.szrnkapeter.logmein.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import hu.szrnkapeter.logmein.dto.PlayerDto;
import hu.szrnkapeter.logmein.service.PlayerService;
import hu.szrnkapeter.logmein.util.Constants;

@RestController
@RequestMapping(value = "/player")
public class PlayerController {

	private final PlayerService service;

	@Autowired
	public PlayerController(PlayerService playerService) {
		this.service = playerService;
	}
	
	@PutMapping(consumes = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> create(@RequestBody PlayerDto dto) {
		return new ResponseEntity<>(service.create(dto), HttpStatus.OK);
	}
	
	@DeleteMapping(path = Constants.ID, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		service.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(path = Constants.ID, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public @ResponseBody PlayerDto getById(@PathVariable("id") Long id) {
		return service.getById(id);
	}
}