<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    // Chuyển hướng về trang welcome (controller xử lý trang chủ)
    String ctx = request.getContextPath();
    response.sendRedirect(ctx + "/welcome");
%>
