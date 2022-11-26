package com.zhou.mybatis.test;

import com.zhou.mybatis.mapper.CacheMapper;
import com.zhou.mybatis.pojo.Emp;
import com.zhou.mybatis.utils.SqlSessionUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * Date:2021/11/30
 * Author:ybc
 * Description:
 */
public class CacheMapperTest {

    @Test
    public void testOneCache(){
        SqlSession sqlSession1 = SqlSessionUtils.getSqlSession();
        CacheMapper mapper1 = sqlSession1.getMapper(CacheMapper.class);
        Emp emp1 = mapper1.getEmpByEid(1);
        System.out.println(emp1);
//        调用这个方法第二次的时候，是从缓存中获取数据的
//        Emp emp2 = mapper1.getEmpByEid(1);
//        System.out.println(emp2);

        //1.添加也会清空缓存
//        mapper1.insertEmp(new Emp(null,"abc",23,"男","123@qq.com"));

        sqlSession1.clearCache();//2.手动清空缓存
        Emp emp2 = mapper1.getEmpByEid(1);
        System.out.println(emp2);

//        4.查询的数据不是同一个，缓存中没有。清空缓存

//        SqlSession sqlSession2 = SqlSessionUtils.getSqlSession();//3.sqlSession不是同一个，清空缓存
//        CacheMapper mapper2 = sqlSession2.getMapper(CacheMapper.class);
//        Emp emp2 = mapper2.getEmpByEid(1);
//        System.out.println(emp2);

        //在同一个session中，再次获取数据直接缓存中获取
//        CacheMapper mapper2 = sqlSession1.getMapper(CacheMapper.class);
//        Emp emp2 = mapper2.getEmpByEid(1);
//        System.out.println(emp2);
    }

    @Test
    public void testTwoCache(){
//        二级缓存开启需满足四个条件
//        增删改查会使二级缓存失效
        try {
            InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
            SqlSession sqlSession1 = sqlSessionFactory.openSession(true);
            CacheMapper mapper1 = sqlSession1.getMapper(CacheMapper.class);
            System.out.println(mapper1.getEmpByEid(1));
            sqlSession1.close();
            SqlSession sqlSession2 = sqlSessionFactory.openSession(true);
            CacheMapper mapper2 = sqlSession2.getMapper(CacheMapper.class);
            System.out.println(mapper2.getEmpByEid(1));
            sqlSession2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
