package hu.szrnkapeter.logmein.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.common.collect.Sets;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "CRD_PLAYER")
public class PlayerEntity extends BaseIdEntity {

	private static final long serialVersionUID = -4503987833685345943L;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "player", orphanRemoval = true)
	private Set<PlayerCardEntity> cards = Sets.newHashSet();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_GAME_ID", nullable = false)
	private GameEntity game;

	@Column(name ="NAME")
	private String name;
}