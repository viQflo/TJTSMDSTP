package com.smhrd.database;

import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SqlSessionManager {
   private static SqlSessionFactory sqlSessionFactory;

   static {
      try {
         String resource = "com/smhrd/database/mybatis-config.xml"; // MyBatis 설정 파일 경로
         Reader reader = Resources.getResourceAsReader(resource);
         sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public static SqlSessionFactory getSqlSession() {
      return sqlSessionFactory;
   }
}