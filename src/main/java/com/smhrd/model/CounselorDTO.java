package com.smhrd.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CounselorDTO {
	@JsonProperty("cs_id") // 기존 email과 매핑
	private String csId;

	@JsonProperty("cs_charge") // 상담 분야
	private String csCharge;

	@JsonProperty("cs_certi") // 자격증
	private String csCerti;

	@JsonProperty("cs_approved") // 승인 상태 (N 고정)
	private String csApproved = "N";

	@JsonProperty("location") // 지역
	private String location;

	// 기본 생성자
	public CounselorDTO() {
	}

	public CounselorDTO(String csId, String csCharge, String csCerti, String location) {
		this.csId = csId;
		this.csCharge = csCharge;
		this.csCerti = csCerti;
		this.location = location;
		this.csApproved = "N"; // 기본값 설정
	}

	// Getter & Setter
	public String getCsId() {
		return csId;
	}

	public void setCsId(String csId) {
		this.csId = csId;
	}

	public String getCsCharge() {
		return csCharge;
	}

	public void setCsCharge(String csCharge) {
		this.csCharge = csCharge;
	}

	public String getCsCerti() {
		return csCerti;
	}

	public void setCsCerti(String csCerti) {
		this.csCerti = csCerti;
	}

	public String getCsApproved() {
		return csApproved;
	}

	public void setCsApproved(String csApproved) {
		this.csApproved = csApproved;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}
