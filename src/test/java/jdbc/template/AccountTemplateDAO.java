package jdbc.template;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import jdbc.vo.AccountVO;

public class AccountTemplateDAO {
	private  final  JdbcTemplate jdbcTemplate;
	public AccountTemplateDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate= jdbcTemplate;
	}

	//sql 쿼리
	private final String ACCOUNT_INSERT = "INSERT INTO account(ID, USERNAME, PASSWORD) "
		+ "VALUES((SELECT coalesce(MAX(ID), 0) + 1 FROM ACCOUNT A), ?, ?)";
	private final String ACCOUNT_SELECT = "SELECT * FROM account WHERE ID = ?";

	//crud 기ㅣ능 메소드
	//일반 다오랑 방식이 다름 insert = create
	//아까처럼 커넥션 이런거 안해도 됨 더 간단쓰
	//but 설정파일로 데베 연결설정을 해줘야 함 => application.yml
	public Integer insertAccount(AccountVO vo){
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(con ->{
			var ps = con.prepareStatement(ACCOUNT_INSERT, new String[]{"id"});
			ps.setString(1, vo.getUsername());
			ps.setString(2,vo.getPassword());
			return ps;
		}, keyHolder);

		return (Integer) keyHolder.getKey();
	}

	public AccountVO selectAccount(Integer id){
		return jdbcTemplate.queryForObject(ACCOUNT_SELECT, new AccountRowMapper(), id);
	}
}
