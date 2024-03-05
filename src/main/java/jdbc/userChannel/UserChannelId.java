package jdbc.userChannel;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserChannelId implements Serializable {

	private Long user;
	private Long channel;

	// @Column(name = "user_id")
	// private Long userId;
	//
	// @Column(name = "channel_id")
	// private Long channelId;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UserChannelId userChannelId = (UserChannelId) o;
		return Objects.equals(getUser(), userChannelId.getUser()) && Objects.equals(getChannel(), userChannelId.getChannel());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getUser(), getChannel());
	}
}