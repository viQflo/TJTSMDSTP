package com.smhrd.model;

public class SocialLinkDTO {
    private String email;        // 기존 계정 이메일 (TB_USER.email)
    private String socialEmail;  // 소셜 로그인 이메일 (카카오, 네이버, 구글 등)
    private String provider;     // 소셜 로그인 제공자 (kakao, google, naver)

    public SocialLinkDTO() {}

    public SocialLinkDTO(String email, String socialEmail, String provider) {
        this.email = email;
        this.socialEmail = socialEmail;
        this.provider = provider;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSocialEmail() {
        return socialEmail;
    }

    public void setSocialEmail(String socialEmail) {
        this.socialEmail = socialEmail;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    @Override
    public String toString() {
        return "SocialLinkDTO [email=" + email + ", socialEmail=" + socialEmail + ", provider=" + provider + "]";
    }
}
