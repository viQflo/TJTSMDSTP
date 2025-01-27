package com.smhrd.model;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.smhrd.database.SqlSessionManager;

public class MemberDAO {

	public int join(MemberDTO dto) {
		SqlSessionFactory sqlSessionFactory = SqlSessionManager.getSqlSession();
		SqlSession sqlsession = sqlSessionFactory.openSession(true); // auto commit
		int cnt = sqlsession.insert("join", dto);
		sqlsession.close();
		return cnt;

	}

}
