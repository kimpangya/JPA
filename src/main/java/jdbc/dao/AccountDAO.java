package jdbc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.print.MultiDocPrintService;

import jdbc.vo.AccountVO;

public class AccountDAO {
	//JDBC 관련된 변수
	private Connection conn=null;
	private PreparedStatement state=null;
	private ResultSet rs=null;

	private static String url = "jdbc:postgresql://localhost:5432/messenger";
	private static String username = "pangya";
	private static String password = "pass";

	//sql 쿼리
	private final String ACCOUNT_INSERT = "INSERT INTO account(ID, USERNAME, PASSWORD) "
		+ "VALUES((SELECT coalesce(MAX(ID), 0) + 1 FROM ACCOUNT A), ?, ?)";
	private final String ACCOUNT_SELECT = "SELECT * FROM account WHERE ID = ?";

	//crud 기ㅣ능 메소드
	public Integer insertAccount(AccountVO vo){
		var id=-1;
		try{
			String[] returnId = {"id"};
			conn= DriverManager.getConnection(url, username, password);
			state=conn.prepareStatement(ACCOUNT_INSERT, returnId);
			state.setString(1,vo.getUsername());
			state.setString(2,vo.getPassword());
			state.executeUpdate();

			//statement의 key를 이용해서 id값을 가져옴... 이해 x 그냥 그럼
			try(ResultSet rs = state.getGeneratedKeys()){
				if(rs.next()){
					id=rs.getInt(1);
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return id;
	}

	public AccountVO selectAccount(Integer id){
		AccountVO vo = null;
		try{
			conn= DriverManager.getConnection(url, username, password);
			state=conn.prepareStatement(ACCOUNT_SELECT);
			state.setInt(1,id);
			var rs = state.executeQuery();

			//rs 응답값이 여러개 올 수 있으니까 next 호출할 때마다 하나씩 꺼내오자
			if(rs.next()){
				vo = new AccountVO();
				vo.setId(rs.getInt("ID"));
				vo.setUsername(rs.getString("USERNAME"));
				vo.setPassword(rs.getString("PASSWORD"));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return vo;
	}
}
