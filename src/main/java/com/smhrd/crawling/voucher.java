package com.smhrd.crawling;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

public class voucher {
    public static void main(String[] args) {
        try {
            // 크롤링할 웹사이트 주소
            String url = "https://example.com";

            // HTML 문서 가져오기
            Document doc = Jsoup.connect(url).get();

            // 웹페이지 제목 가져오기
            System.out.println("페이지 제목: " + doc.title());

            // 특정 태그 (예: h1) 가져오기
            Element heading = doc.selectFirst("h1");
            if (heading != null) {
                System.out.println("H1 태그: " + heading.text());
            }

            // 링크 목록 가져오기
            Elements links = doc.select("a");
            for (Element link : links) {
                System.out.println("링크: " + link.attr("href") + " | " + link.text());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
