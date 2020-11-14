package hu.szrnkapeter.logmein.dto;

import java.util.Set;

import com.google.common.collect.Sets;

import lombok.Data;

@Data
public class PlayerDto {

	private Long id;
	private Long gameId;
	private String name;
	private Set<String> cards = Sets.newHashSet();
}