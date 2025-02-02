package com.smhrd.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MemberDTO {
    private String email;
    private String pw;
    private String name;
    private String nick;
    private String birthdate;
    private String gender;
    private String job;
    private String phone;
    private String socialLinkedEmail;
    private String socialProvider;
    

    @JsonProperty("user_type") // 클라이언트 JSON의 user_type 필드와 매핑
    private String userType;

    @JsonProperty("create_dt") // 클라이언트 JSON의 create_dt 필드와 매핑
    private String createDt;

    // 기본 생성자
    public MemberDTO() {
    }

    // 모든 필드를 포함한 생성자
    public MemberDTO(String email, String pw, String name, String nick, String birthdate, String gender, String job,
                     String phone, String userType, String createDt) {
        this.email = email;
        this.pw = pw;
        this.name = name;
        this.nick = nick;
        this.birthdate = birthdate;
        this.gender = gender;
        this.job = job;
        this.phone = phone;
        this.userType = userType;
        this.createDt = createDt;
    }

    // Getter와 Setter
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getCreateDt() {
        return createDt;
    }

    public void setCreateDt(String createDt) {
        this.createDt = createDt;
    }
    
    public String getEmail2() { return email; }
    public void setEmail2(String email) { this.email = email; }

    public String getPw2() { return pw; }
    public void setPw2(String pw) { this.pw = pw; }

    public String getSocialLinkedEmail() { return socialLinkedEmail; }
    public void setSocialLinkedEmail(String socialLinkedEmail) { this.socialLinkedEmail = socialLinkedEmail; }

    public String getSocialProvider() { return socialProvider; }
    public void setSocialProvider(String socialProvider) { this.socialProvider = socialProvider; }
}
