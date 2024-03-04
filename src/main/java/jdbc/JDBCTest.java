package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jdbc.dao.AccountDAO;
import jdbc.vo.AccountVO;

public class JDBCTest {
	@Test
	void jdbcTest() throws SQLException {
		// docker exec -i -t postgres_boot bash
		// su - postgres
		// psql --username kimpangya --dbname messenger
		// \list (데이터 베이스 조회)
		// \dt (테이블 조회)

		//이걸로 접속하겠다~ 5432포트
		String url = "jdbc:postgresql://localhost:5432/messenger";
		String username = "pangya";
		String password = "pass";

		//when
		try{
			Connection connection = DriverManager.getConnection(url,username, password);
			String createSql="CREATE TABLE ACCOUNT (id SERIAL PRIMARY KEY, username varchar(255), password varchar(255))";
			PreparedStatement statement = connection.prepareStatement(createSql);
			statement.execute(); //쿼리 실행

			//close마지막에 꼭 해줘야함!!
			statement.close();
			connection.close();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{

		}
	}

	@Test
	@DisplayName("JDBC 삽입/조회 실습")
	void jdbcInsertSelectTest() throws SQLException {
		// given
		String url = "jdbc:postgresql://localhost:5432/messenger";
		String username = "pangya";
		String password = "pass";

		// when
		try (Connection connection = DriverManager.getConnection(url, username, password)) {
			System.out.println("Connection created: " + connection);

			String insertSql = "INSERT INTO ACCOUNT (id, username, password) VALUES ((SELECT coalesce(MAX(ID), 0) + 1 FROM ACCOUNT A), 'user1', 'pass1')";
			try (PreparedStatement statement = connection.prepareStatement(insertSql)) {
				statement.execute();
			}

			// then
			String selectSql = "SELECT * FROM ACCOUNT";
			try (PreparedStatement statement = connection.prepareStatement(selectSql)) {
				var rs = statement.executeQuery();
				while (rs.next()) {
					System.out.printf("%d, %s, %s", rs.getInt("id"), rs.getString("username"),
						rs.getString("password"));
				}
			}
		}
	}

	@Test
	@DisplayName("JDBC DAO 삽입/조회 실습")
	void jdbcDAOInsertSelectTest() throws SQLException {
		// given
		AccountDAO accountDAO = new AccountDAO();

		// when
		var id = accountDAO.insertAccount(new AccountVO("new user", "new password"));

		// then
		var account = accountDAO.selectAccount(id);
		assert account.getUsername().equals("new user");
	}

}
