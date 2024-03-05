package jdbc.thread;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import jdbc.channel.Channel;
import jdbc.channel.ChannelRepository;

@SpringBootTest
@Transactional
@Rollback(value = false)
class ThreadRepositoryTest {
	@Autowired
	private  ThreadRepository threadRepository;

	@Autowired
	private ChannelRepository channelRepository;

	@Test
	void insertSelectThreadTest(){
		// given
		var newChannel = Channel.builder().name("new-channel").build();
		var newThread=Thread.builder().message("new-message").build();
		var newThread2=Thread.builder().message("new-message2").build();
		newThread.setChannel(newChannel);

		// when
		var savedThread = threadRepository.insertThread(newThread);
		var savedThread2 = threadRepository.insertThread(newThread2);
		var savedChannel=channelRepository.insertChannel(newChannel);

		// then
		var foundChannel = channelRepository.selectChannel(savedChannel.getId());
		// var foundThread = threadRepository.selectThread(savedThread.getId());
		// assert foundThread.getChannel().getName().equals(newChannel.getName());
		assert foundChannel.getThreads().containsAll(Set.of(savedThread, savedThread2));
	}
}