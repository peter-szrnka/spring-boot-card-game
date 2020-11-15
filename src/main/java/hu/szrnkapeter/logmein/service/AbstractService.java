package hu.szrnkapeter.logmein.service;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.szrnkapeter.logmein.type.CardGameErrorCode;
import hu.szrnkapeter.logmein.type.CardGameException;

public abstract class AbstractService<T, R extends JpaRepository<T, Long>> {

	protected R repository;
	protected CardGameErrorCode errorCode;

	AbstractService(R repo, CardGameErrorCode cardGameErrorCode) {
		repository = repo;
		errorCode = cardGameErrorCode;
	}

	/**
	 * Returns an entity by ID.
	 * 
	 * @param id
	 * @return T
	 */
	public T getEntityById(Long id) {
		Optional<T> result = repository.findById(id);

		if (!result.isPresent()) {
			throw new CardGameException(errorCode);
		}

		return result.get();
	}
}