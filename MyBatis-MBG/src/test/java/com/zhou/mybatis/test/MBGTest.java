package com.zhou.mybatis.test;

import com.zhou.mybatis.mapper.EmpMapper;
import com.zhou.mybatis.pojo.Emp;
import com.zhou.mybatis.pojo.EmpExample;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Date:2021/11/30
 * Author:ybc
 * Description:
 */
public class MBGTest {

    @Test
    public void testMBG(){
        try {
            InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
            SqlSession sqlSession = sqlSessionFactory.openSession(true);
            EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
            //查询所有数据
//            List<Emp> list = mapper.selectByExample(null);
//            list.forEach(emp -> System.out.println(emp));


//            QBC风格的增删改查
            //根据条件查询
            EmpExample example = new EmpExample();
            example.createCriteria().andEmpNameEqualTo("张三").andAgeGreaterThanOrEqualTo(20);
//            example.or().andDidIsNotNull();//这个是分割上面两个语句的，用and连接( emp_name = ? and age >= ? )
            List<Emp> list = mapper.selectByExample(example);
            list.forEach(emp -> System.out.println(emp));

//            mapper.updateByPrimaryKeySelective(new Emp(1,"admin",22,null,"456@qq.com",3));//选择性修改
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
