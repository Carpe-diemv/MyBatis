import com.zhou.mybatis.mapper.EmpMapper;
import com.zhou.mybatis.pojo.Emp;
import com.zhou.mybatis.utils.SqlSessionUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class ReaultMapTest {
    @Test
    public void Test(){
        SqlSession sqlSession = SqlSessionUtils.getSqlSession();
        EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
        List<Emp> l = mapper.getAllEmp();
        l.forEach(emp -> System.out.println(emp));
    }
}
