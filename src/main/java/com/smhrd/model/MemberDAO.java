package com.smhrd.model;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.smhrd.database.SqlSessionManager;

public class MemberDAO {
    private final SqlSessionFactory sqlSessionFactory = SqlSessionManager.getSqlSession();

    // 회원 정보 삽입 메서드
    public int insertMember(MemberDTO memberDTO) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            return session.insert("join", memberDTO);
        }
    }

    // 로그인 메서드
    public MemberDTO login(MemberDTO memberDTO) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            return session.selectOne("com.smhrd.model.MemberDAO.login", memberDTO);
        }
    }

    // 이메일을 기반으로 사용자 정보 가져오기 (마이페이지에서 사용)
    public MemberDTO getMemberByEmail(String email) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.selectOne("com.smhrd.model.MemberDAO.getMemberByEmail", email);
        }
    }

    // 전화번호 업데이트
    public int updatePhone(String email, String phone) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            MemberDTO member = new MemberDTO();
            member.setEmail(email);
            member.setPhone(phone);
            return session.update("com.smhrd.model.MemberDAO.updatePhone", member);
        }
    }

    // 비밀번호 변경
    public int updatePassword(String email, String newPassword) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            MemberDTO member = new MemberDTO();
            member.setEmail(email);
            member.setPw(newPassword);
            return session.update("updatePassword", member);
        }
    }

    // 사용자 전화번호 조회 (EMAIL 기반)
    public String getUserPhone(String email) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            return session.selectOne("com.smhrd.model.MemberDAO.getUserPhone", email);
        }
    }

    // 소셜 이메일을 기반으로 사용자 찾기
    public MemberDTO findBySocialEmail(String socialEmail) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.selectOne("com.smhrd.model.MemberDAO.findBySocialEmail", socialEmail);
        }
    }

    // 기존 계정과 소셜 계정 연동
    public boolean linkSocialAccount(String email, String socialEmail, String provider) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            MemberDTO existingUser = session.selectOne("com.smhrd.model.MemberDAO.findByEmail", email);
            if (existingUser == null) return false;

            MemberDTO param = new MemberDTO();
            param.setEmail(email);
            param.setSocialLinkedEmail(socialEmail);
            param.setSocialProvider(provider);
            session.update("com.smhrd.model.MemberDAO.linkSocialAccount", param);
            session.commit();
            return true;
        }
    }

    // 소셜 회원 가입
    public int insertSocialMember(MemberDTO memberDTO) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            return session.insert("com.smhrd.model.MemberDAO.insertSocialMember", memberDTO);
        }
    }
    
    public MemberDTO findByEmail(String email) {
    	SqlSession session = sqlSessionFactory.openSession(true);
        return session.selectOne("com.smhrd.model.MemberDAO.findByEmail", email);
    }

    public void insertSocialMember2(MemberDTO member) {
    	SqlSession session = sqlSessionFactory.openSession(true);
        session.insert("com.smhrd.model.MemberDAO.insertSocialMember", member);
    }

    public int updateSocialLink(MemberDTO member) {
    	SqlSession session = sqlSessionFactory.openSession(true);
        return session.update("com.smhrd.model.MemberDAO.updateSocialLink", member);
    }
    
    public String findEmailByNameAndPhone(String name, String phone) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            MemberDTO param = new MemberDTO();
            param.setName(name);
            param.setPhone(phone);
            List<String> emails = session.selectList("com.smhrd.model.MemberDAO.findEmailByNameAndPhone", param);

            if (emails != null && !emails.isEmpty()) {
                return emails.get(0); // 첫 번째 결과만 반환
            }
            return null; // 없으면 null 반환
        }
    }
 // 이메일로 비밀번호 찾기
    public String findPasswordByEmail(String email) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.selectOne("com.smhrd.model.MemberDAO.findPasswordByEmail", email);
        }
    }
}
