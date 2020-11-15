package hu.szrnkapeter.logmein.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.common.collect.Lists;

import hu.szrnkapeter.logmein.type.GameStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "CRD_GAME")
public class GameEntity extends BaseIdEntity {

	private static final long serialVersionUID = 1805034075812246830L;

	@Column(name ="TITLE")
	private String title;

	@Column(name ="CREATOR_NAME")
	private String creatorName;

	@Column(name ="CREATION_DATE")
	private LocalDate creationDate;
	
	@Column(name ="STATUS")
	@Enumerated(EnumType.STRING)
	private GameStatus orderStatus = GameStatus.STARTED;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "game", orphanRemoval = true)
	private List<PlayerEntity> players = Lists.newArrayList();
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "game", orphanRemoval = true)
	private List<DeckEntity> decks = Lists.newArrayList();
}