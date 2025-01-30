package com.smhrd.model;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.smhrd.database.SqlSessionManager;

public class CounselorDAO {
	private final SqlSessionFactory sqlSessionFactory = SqlSessionManager.getSqlSession();

	// 상담사 추가 정보 저장 메서드
	public int insertCounselorInfo(CounselorDTO counselorDTO) {
		try (SqlSession session = sqlSessionFactory.openSession(true)) {
			int result = session.insert("insertCounselor", counselorDTO);
			session.close();
			return result;
		}
	}
}
