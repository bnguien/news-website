<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<link
  href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
  rel="stylesheet"
/>
<link
  href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"
  rel="stylesheet"
/>
<link
  href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.3.1/mdb.min.css"
  rel="stylesheet"
/>
<meta charset="UTF-8">
<title>Đăng ký tài khoản</title>
</head>
<body>
<jsp:include page="header.jsp" />
<section class="h-100 gradient-form" style="background-color: #eee;">
  <div class="container py-5 h-100">
    <div class="row d-flex justify-content-center align-items-center h-100">
    
              <div class="card-body" style="margin: 0 78px;padding: 20px 62px;border-radius: 22px;border: solid 1px black;background-color: white;">
            
                <div class="text-center">
                  <h4 class="mt-1 mb-5 pb-1">Đăng kí tài khoản</h4>
                </div>

               
                <c:if test="${not empty errorCode}">
                    <div class="alert alert-danger" role="alert">
                        <c:choose>
                            <c:when test="${errorCode == 'MISSING_FIELDS'}">
                                Vui lòng nhập đầy đủ các trường.
                            </c:when>
                            <c:when test="${errorCode == 'PASSWORD_MISMATCH'}">
                                Mật khẩu nhập lại không khớp.
                            </c:when>
                            <c:when test="${errorCode == 'REGISTER_EXISTS'}">
                                Tên đăng nhập hoặc email đã tồn tại.
                            </c:when>
                            <c:when test="${errorCode == 'SERVER_ERROR'}">
                                Lỗi hệ thống, vui lòng thử lại sau.
                            </c:when>
                            <c:otherwise>
                                Lỗi không xác định.
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:if>
                
                <form action="register" method="post">
                 
                  <div class="form-outline mb-4">
                    <%-- Điền lại giá trị 'username' nếu có lỗi --%>
                    <input type="text" id="formUsername" class="form-control" 
                      name="username" value="<c:out value='${username}'/>" />
                    <label class="form-label" for="formUsername">Tên đăng nhập (username)</label>
                  </div>

                  <div class="form-outline mb-4">
                    <%-- Điền lại giá trị 'email' nếu có lỗi --%>
                    <input type="email" id="formEmail" class="form-control" 
                      name="email" value="<c:out value='${email}'/>" />
                    <label class="form-label" for="formEmail">Email</label>
                  </div>

                  <div class="form-outline mb-4">
                    <input type="password" id="formPassword" class="form-control" name="password" />
                    <label class="form-label" for="formPassword">Mật khẩu</label>
                  </div>
                  
                  <div class="form-outline mb-4">
                    <input type="password" id="formConfirmPassword" class="form-control" name="confirmPassword" />
                    <label class="form-label" for="formConfirmPassword">Nhập lại mật khẩu</label>
                  </div>
                  
                  <%-- Input cho 'role' như trong spec --%>
                  <select class="form-select mb-4" name="role">
                      <option value="reader" selected>Reader</option>
                      <option value="reporter">Reporter</option>
                      <%-- Bạn có thể ẩn/xóa role 'admin' nếu không muốn người dùng tự đăng ký --%>
                      <%-- <option value="admin">Admin</option> --%>
                  </select>

                  <div class="text-center pt-1 mb-5 pb-1">
                    <button class="btn btn-primary btn-block fa-lg gradient-custom-2 mb-3" type="submit">
                      Đăng ký
                    </button>
                  </div>

                  <div class="d-flex align-items-center justify-content-center pb-4">
                    <p class="mb-0 me-2">Already have an account?</p>
                    <a href="login.jsp" class="btn btn-outline-danger">Login</a>
                  </div>

                </form>

              </div>
            </div>
          </div>
</section>
<jsp:include page="footer.jsp" />
<script
  type="text/javascript"
  src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.3.1/mdb.umd.min.js"
></script>
</body>
</html>