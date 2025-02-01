package com.smhrd.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VoucherDAO {
	private static final String URL = "jdbc:oracle:thin:@project-db-cgi.smhrd.com:1524:xe";
	private static final String USER = "mp_24K_bigdata28_p2_1";
	private static final String PASSWORD = "smhrd1";

	public List<VoucherDTO> getVouchers() {
	    List<VoucherDTO> vouchers = new ArrayList<>();
	    String sql = "SELECT VC_URL, VC_IDX2,  VC_IMG_URL FROM TB_VOUCHER";

	    try {
	        // ✅ JDBC 드라이버 로드 (필요할 경우)
	        Class.forName("oracle.jdbc.driver.OracleDriver");

	        System.out.println("🔌 DB 연결 시도 중...");
	        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
	        System.out.println("✅ DB 연결 성공!");

	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        ResultSet rs = pstmt.executeQuery();

	        while (rs.next()) {
	            String url = rs.getString("VC_URL");
	            String name = rs.getString("VC_IDX2");
	            String img = rs.getString("VC_IMG_URL");
	            System.out.println("📌 바우처 데이터: " + name + " | " + url);
	            vouchers.add(new VoucherDTO(name, url,img));
	        }

	    } catch (ClassNotFoundException e) {
	        System.out.println("❌ JDBC 드라이버 로드 실패!");
	        e.printStackTrace();
	    } catch (SQLException e) {
	        System.out.println("❌ DB 조회 실패: " + e.getMessage());
	        e.printStackTrace();
	    }

	    System.out.println("총 바우처 개수: " + vouchers.size());

	    return vouchers;
	}}

