package com.smhrd.model;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.smhrd.database.SqlSessionManager;

public class MemberDAO {
	private final SqlSessionFactory sqlSessionFactory = SqlSessionManager.getSqlSession();
	 

	// 회원 정보 삽입 메서드
	public int insertMember(MemberDTO memberDTO) {
		try (SqlSession session = sqlSessionFactory.openSession(true)) {
			int result = session.insert("join", memberDTO);
			session.close();
			return result;
		}
	}

	// 추가 정보 삽입 메서드

	// 로그인 메서드
	public MemberDTO login(MemberDTO memberDTO) {
		try (SqlSession session = sqlSessionFactory.openSession(true)) {
			return session.selectOne("login", memberDTO);
		}
	}

	

}
