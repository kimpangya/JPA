package jdbc.userChannel;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import jdbc.channel.Channel;
import jdbc.channel.ChannelRepository;
import jdbc.user.User;
import jdbc.user.UserRepository;

@SpringBootTest
@Transactional
@Rollback(value = false)
class UserChannelRepositoryTest {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ChannelRepository channelRepository;

	@Autowired
	private UserChannelRepository userChannelRepository;

	@Test
	void userJoinChannelTest(){
		// given
		var newChannel = Channel.builder().name("new-channel").build();
		var newUser= User.builder().username("new_user").password("new_password").build();
		var newUserChannel = newChannel.joinUser(newUser);

		// when
		var savedChannel = channelRepository.insertChannel(newChannel);
		var savedUser = userRepository.insertUser(newUser);
		var savedUserChannel = userChannelRepository.insertUserChannel(newUserChannel);

		// then
		var foundChannel = channelRepository.selectChannel(savedChannel.getId());
		//우리가 설정한 채널의 이름과 같은 게 있는지 확인
		assert foundChannel.getUserChannels().stream()
			.map(UserChannel :: getChannel)
			.map(Channel::getName)
			.anyMatch(name -> name.equals(newChannel.getName()));
	}
}