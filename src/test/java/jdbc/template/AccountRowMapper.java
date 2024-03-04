package jdbc.template;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import jdbc.vo.AccountVO;

public class AccountRowMapper implements RowMapper<AccountVO> {

	@Override
	public AccountVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		var vo= new AccountVO();
		vo = new AccountVO();
		vo.setId(rs.getInt("ID"));
		vo.setUsername(rs.getString("USERNAME"));
		vo.setPassword(rs.getString("PASSWORD"));
		return vo;
	}
}
