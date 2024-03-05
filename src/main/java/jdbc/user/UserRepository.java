package jdbc.user;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class UserRepository {
	@PersistenceContext
	EntityManager entityManager;


	public User insertUser(User user) {
		entityManager.persist(user);
		return user;
	}

	public User selectUser(Long id) {
		return entityManager.find(User.class, id);
	}
}
