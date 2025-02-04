package com.smhrd.frontController;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
    // execute 메서드를 정의
    String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
