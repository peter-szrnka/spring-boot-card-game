package hu.szrnkapeter.logmein.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "CRD_PLAYER_CARD")
public class PlayerCardEntity extends BaseIdEntity {

	private static final long serialVersionUID = -4503987833685345943L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_PLAYER_ID", nullable = false)
	private PlayerEntity player;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_DECK_ID", nullable = false)
	private DeckEntity deck;

	@Column(name ="CARD")
	private String card;
}