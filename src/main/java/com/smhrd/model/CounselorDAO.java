package com.smhrd.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	
	
	public List<CounselorDTO> getFilteredCounselors(String location, String csCharge) {
	    SqlSession session = sqlSessionFactory.openSession();
	    List<CounselorDTO> counselorList = null;

	    try {
	        CounselorDTO params = new CounselorDTO();
	        params.setLocation(location);
	        params.setCsCharge(csCharge);

	        System.out.println("DAO");
	        System.out.println("DEBUG: DAO에서 받은 location = " + location);
	        System.out.println("DEBUG: DAO에서 받은 csCharge = " + csCharge);

	        // 🚨 `namespace`를 `CounselorMapper`로 변경
	        counselorList = session.selectList("com.smhrd.model.CounselorDAO.getFilteredCounselors", params);

	        if (counselorList == null || counselorList.isEmpty()) {
	            System.out.println("DEBUG: DAO에서 반환된 상담사 목록이 없음.");
	        } else {
	            System.out.println("DEBUG: DAO에서 가져온 상담사 개수 = " + counselorList.size());
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        session.close();
	    }
	    return counselorList;
	}

	}
