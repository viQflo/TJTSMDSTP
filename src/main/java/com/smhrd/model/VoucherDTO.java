package com.smhrd.model;

public class VoucherDTO {
    private String name;
    private String url;
    private String img;

    public VoucherDTO(String name, String url, String img) {
        this.name = name;
        this.url = url;
        this.img =img;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
    
    public String getImg() {
    	return img;
    }
}
