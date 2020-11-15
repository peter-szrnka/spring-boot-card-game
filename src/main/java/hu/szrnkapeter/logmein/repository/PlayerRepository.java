package hu.szrnkapeter.logmein.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import hu.szrnkapeter.logmein.entity.PlayerEntity;

@Transactional
@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {
	
	Optional<PlayerEntity> findByName(String name);
}