package jdbc.channel;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jdbc.thread.Thread;
import jdbc.user.Address;
import jdbc.user.User;
import jdbc.userChannel.UserChannel;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//lombok
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

//jpa
@Entity
public class Channel {
	/**
	 * 컬럼 - 연관관계 컬럼을 제외한 컬럼을 정의합니다.
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	private String name;

	//ORDINAL = enum Type에 public, private가 //0,1로 데이터베이스에 들어가게 됨
	//그럼 문제점이 뭐냐~ 저기에 하나가 더 추가된다거나 순서가 바뀌면 다른 걸로 됨...
	//그래서 String 으로 쓸 거임
	//@Enumerated(EnumType.ORDINAL)
	@Enumerated(EnumType.STRING)
	private Type type;


	public enum Type{
		PUBLIC, PRIVATE
	}

	// @Embedded
	// @AttributeOverrides({
	// 	@AttributeOverride(name = "street", column = @Column(name = "home_street"))
	// })
	// private Address address;



	/**
	 * 생성자 - 약속된 형태로만 생성가능하도록 합니다.
	 */
	@Builder
	public Channel(String name, Type type){
		this.name=name;
		this.type=type;
	}


	/**
	 * 연관관계 - Foreign Key 값을 따로 컬럼으로 정의하지 않고 연관 관계로 정의합니다.
	 */
	@OneToMany(mappedBy = "channel")
	private Set<Thread> threads = new LinkedHashSet<>();

	@OneToMany(mappedBy = "channel")
	private Set<UserChannel> userChannels= new LinkedHashSet<>();

	/**
	 * 연관관계 편의 메소드 - 반대쪽에는 연관관계 편의 메소드가 없도록 주의합니다.
	 */
	public void addThread(Thread thread){
		this.threads.add(thread);
	}

	public UserChannel joinUser(User user){
		var userChannel=UserChannel.builder().user(user).channel(this).build();
		this.userChannels.add(userChannel);
		user.getUserChannels().add(userChannel);
		return userChannel;
	}

	/**
	 * 서비스 메소드 - 외부에서 엔티티를 수정할 메소드를 정의합니다. (단일 책임을 가지도록 주의합니다.)
	 */
}
