package mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import mybatis.vo.AccountMyBatisVO;

@Mapper
public interface AccountMapper {

	AccountMyBatisVO selectAccount(@Param("id") int id);

	void insertAccount(AccountMyBatisVO vo);
}