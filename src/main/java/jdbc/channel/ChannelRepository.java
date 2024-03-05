package jdbc.channel;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceContexts;
import jakarta.persistence.PersistenceUnit;

import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


@Repository
public class ChannelRepository {

	@PersistenceContext
	EntityManager entityManager;


	public Channel insertChannel(Channel channel) {
		entityManager.persist(channel);
		return channel;
	}

	public Channel selectChannel(Long id) {
		return entityManager.find(Channel.class, id);
	}
}