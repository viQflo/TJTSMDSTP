package com.smhrd.crawling;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.*;
import java.time.Duration;
import java.util.List;
import java.util.Random;

public class Voucher {
    public static void main(String[] args) {
        // ✅ 1️⃣ Oracle DB 연결 정보 설정
        String url = "jdbc:oracle:thin:@project-db-cgi.smhrd.com:1524:xe";  
        String user = "mp_24K_bigdata28_p2_1";  
        String password = "smhrd1";  

        Connection conn = null;
        PreparedStatement pstmt = null;

        // ✅ 2️⃣ ChromeDriver 자동 설정 및 실행
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--disable-popup-blocking");
        options.setExperimentalOption("useAutomationExtension", false);
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");

        WebDriver driver = new ChromeDriver(options);

        try {
            // ✅ 3️⃣ DB 연결 시도
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Oracle DB 연결 성공!");

            // ✅ 4️⃣ 구글 검색 URL
            String searchKeyword = "바우처 사이트";
            String searchUrl = "https://www.google.com/search?q=" + searchKeyword;
            driver.get(searchUrl);

            // ✅ 5️⃣ 페이지별 크롤링 반복
            int maxPages = 9; // 🔥 최대 5페이지까지 크롤링
            int page = 1;

            while (page <= maxPages) {
                System.out.println("📄 현재 페이지: " + page);

                // ✅ 6️⃣ 페이지 로딩 대기
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.tF2Cxc")));

                // ✅ 7️⃣ 검색 결과에서 사이트 제목과 링크 가져오기
                List<WebElement> results = driver.findElements(By.cssSelector("div.tF2Cxc"));

                Random rand = new Random();
                String sql = "INSERT INTO TB_VOUCHER (VC_URL, VC_IDX2) VALUES (?, ?)";
                pstmt = conn.prepareStatement(sql);

                for (WebElement result : results) {
                    try {
                        WebElement titleElement = result.findElement(By.cssSelector("h3"));
                        WebElement linkElement = result.findElement(By.cssSelector("a"));

                        String title = titleElement.getText();
                        String link = linkElement.getAttribute("href");

                        if (link != null && link.startsWith("http")) {
                            pstmt.setString(1, link);
                            pstmt.setString(2, title);
                            pstmt.executeUpdate();
                            System.out.println("✅ 저장 완료: " + title + " (" + link + ")");
                        }

                        // ✅ 랜덤 딜레이 추가 (2~5초)
                        Thread.sleep(rand.nextInt(3000) + 2000);

                    } catch (Exception e) {
                        System.out.println("❌ 일부 검색 결과를 저장하지 못했습니다.");
                    }
                }

                // ✅ 8️⃣ "다음 페이지" 버튼 찾기
                try {
                    WebElement nextButton = driver.findElement(By.xpath("//a[@id='pnnext']"));
                    if (nextButton.isDisplayed()) {
                        nextButton.click();
                        page++; // 다음 페이지로 이동
                        Thread.sleep(3000); // 페이지 로딩 대기
                    } else {
                        System.out.println("❌ 다음 페이지 버튼이 없습니다. 크롤링 종료.");
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("❌ 다음 페이지 버튼을 찾을 수 없습니다. 크롤링 종료.");
                    break;
                }
            }

            System.out.println("✅ 크롤링 완료! 데이터가 Oracle DB에 저장되었습니다.");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // ✅ 9️⃣ 자원 정리
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // ✅ WebDriver 종료
            driver.quit();
        }
    }
}