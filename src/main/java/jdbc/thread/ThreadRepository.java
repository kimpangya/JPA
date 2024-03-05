package jdbc.thread;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class ThreadRepository {

	@PersistenceContext
	EntityManager entityManager;


	public Thread insertChannel(Thread thread) {
		entityManager.persist(thread);
		return thread;
	}

	public Thread selectChannel(Long id) {
		return entityManager.find(Thread.class, id);
	}
}