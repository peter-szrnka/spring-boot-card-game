package hu.szrnkapeter.logmein.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@MappedSuperclass
public abstract class BaseIdEntity implements Serializable {

	private static final long serialVersionUID = -4993167731572379528L;
	
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	@Column(name ="ID")
	protected Long id;
}