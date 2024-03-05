package jdbc.channel;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;

@SpringBootTest(classes = ChannelRepository.class)
@Rollback(value = false)
@Transactional
class ChannelRepositoryTest {

	@Autowired
	private ChannelRepository channelRepository;

	@Test
	void insertSelectChannelTest() {
		// given
		var newChannel = Channel.builder().name("new-channel").build();

		// when
		var savedChannel = channelRepository.insertChannel(newChannel);

		// then
		var foundChannel = channelRepository.selectChannel(savedChannel.getId());
		assert foundChannel.getId().equals(savedChannel.getId());
	}
}