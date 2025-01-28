package com.smhrd.controller;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*") // 모든 경로에 대해 필터 적용
public class CORSFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// 초기화 로직이 필요하면 추가 (없으면 비워둠)
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletResponse httpResponse = (HttpServletResponse) response;

		// CORS 헤더 설정
		httpResponse.setHeader("Access-Control-Allow-Origin", "*"); // 모든 도메인 허용
		httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
		httpResponse.setHeader("Access-Control-Allow-Credentials", "true");

		// Preflight 요청 처리
		if ("OPTIONS".equalsIgnoreCase(((HttpServletRequest) request).getMethod())) {
			httpResponse.setStatus(HttpServletResponse.SC_OK);
			return;
		}

		// 다음 필터 또는 서블릿으로 요청 전달
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// 종료 로직이 필요하면 추가 (없으면 비워둠)
	}
}