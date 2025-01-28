package com.smhrd.database;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class SqlSessionManager {

	private static SqlSessionFactory sqlSessionFactory;

	static {
		try {
			String resource = "mybatis-config.xml"; // MyBatis 설정 파일 경로
			InputStream inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static SqlSessionFactory getSqlSession() {
		return sqlSessionFactory;
	}
}
