package hu.szrnkapeter.logmein.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import hu.szrnkapeter.logmein.entity.GameEntity;

@Transactional
@Repository
public interface GameRepository extends JpaRepository<GameEntity, Long> {

	Optional<GameEntity> findByTitle(String title);
}