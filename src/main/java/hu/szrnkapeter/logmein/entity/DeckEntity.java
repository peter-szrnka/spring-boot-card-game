package hu.szrnkapeter.logmein.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import hu.szrnkapeter.logmein.util.Constants;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "CRD_DECK")
public class DeckEntity extends BaseIdEntity {

	private static final long serialVersionUID = -4503987833685345943L;
	
	@Column(name ="ADDITION_DATE")
	private LocalDate additionDate = LocalDate.now();
	
	@Column(name ="CARDS")
	private String cards = Constants.LIST_ALL_CARD;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_GAME_ID", nullable = false)
	private GameEntity game;
}