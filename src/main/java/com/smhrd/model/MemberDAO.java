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
	
	// ✅ 이메일을 기반으로 사용자 정보 가져오기 (마이페이지에서 사용)
    public MemberDTO getMemberByEmail(String email) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.selectOne("getMemberByEmail", email);
        }
    }

    // ✅ 전화번호 업데이트
    public int updatePhone(String email, String phone) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            MemberDTO member = new MemberDTO();
            member.setEmail(email);
            member.setPhone(phone);
            return session.update("updatePhone", member);
        }
    }

    // ✅ 비밀번호 변경
    public int updatePassword(String email, String newPassword) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            MemberDTO member = new MemberDTO();
            member.setEmail(email);
            member.setPw(newPassword);
            return session.update("updatePassword", member);
        }
    }

	

}
