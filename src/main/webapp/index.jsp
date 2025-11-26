<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Chuyển hướng người dùng đến controller trang chủ --%>
<% response.sendRedirect(request.getContextPath() + "/welcome"); %>