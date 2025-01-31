package com.smhrd.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CounselorDTO {

    @JsonProperty("name")
    private String name; // 상담사 이름 (TB_USER.NAME)

    @JsonProperty("cs_id") 
    private String csId; // 상담사 ID (TB_COUNSELOR.CS_ID, TB_USER.EMAIL)

    @JsonProperty("cs_charge")
    private String csCharge; // 상담 분야 (TB_COUNSELOR.CS_CHARGE)

    @JsonProperty("cs_certi")
    private String csCerti; // 자격증 (TB_COUNSELOR.CS_CERTI)

    @JsonProperty("cs_approved")
    private String csApproved = "N"; // 기본값 (회원가입 시 사용됨)

    @JsonProperty("location")
    private String location; // 지역 (TB_COUNSELOR.LOCATION)

    // 🔹 기본 생성자 (필수)
    public CounselorDTO() {}

    // 🔹 상담사 리스트 조회용 생성자 (MyBatis `getFilteredCounselors` 용)
    public CounselorDTO(String name, String csCharge, String location) {
        this.name = name;
        this.csCharge = csCharge;
        this.location = location;
    }

    // 🔹 회원가입 및 등록용 생성자 (기존 `insertCounselor`용)
    public CounselorDTO(String csId, String name, String csCharge, String csCerti, String location, String csApproved) {
        this.csId = csId;
        this.name = name;
        this.csCharge = csCharge;
        this.csCerti = csCerti;
        this.location = location;
        this.csApproved = csApproved;
    }

    // 🔹 Getter & Setter (필수)
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCsId() { return csId; }
    public void setCsId(String csId) { this.csId = csId; }

    public String getCsCharge() { return csCharge; }
    public void setCsCharge(String csCharge) { this.csCharge = csCharge; }

    public String getCsCerti() { return csCerti; }
    public void setCsCerti(String csCerti) { this.csCerti = csCerti; }

    public String getCsApproved() { return csApproved; }
    public void setCsApproved(String csApproved) { this.csApproved = csApproved; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}
