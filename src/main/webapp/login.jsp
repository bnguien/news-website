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
<title>Đăng nhập</title>
</head>
<body>
<jsp:include page="header.jsp" />
<section class="h-100 gradient-form" style="background-color: #eee;">
  <div class="container py-5 h-100">
    <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col-lg-12">
              <div class="card-body" style="margin: 0 78px;padding: 20px 62px;border-radius: 22px;border: solid 1px black;background-color: white;">

                <div class="text-center">
                  <h4 class="mt-1 mb-5 pb-1">Đăng nhập</h4>
                </div>

               
                <c:if test="${not empty errorCode}">
                    <div class="alert alert-danger" role="alert">
                        <c:choose>
                            <c:when test="${errorCode == 'MISSING_FIELDS'}">
                                Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu.
                            </c:when>
                            <c:when test="${errorCode == 'ACCOUNT_NOT_EXISTS'}">
                                Tài khoản không tồn tại.
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
                
                <c:if test="${param.msg == 'register_success'}">
                    <div class="alert alert-success" role="alert">
                        Đăng ký tài khoản thành công! Vui lòng đăng nhập.
                    </div>
                </c:if>
                
                <form action="login" method="post">
                  <div class="form-outline mb-4">
                    <input type="text" id="formLoginId" class="form-control"
                      placeholder="Tên đăng nhập hoặc email" name="loginIdentifier" 
                      value="<c:out value='${loginIdentifier}'/>" />
                    <label class="form-label" for="formLoginId">Username or Email</label>
                  </div>

                  <div class="form-outline mb-4">
                    <%-- Tên input là "password" như trong spec --%>
                    <input type="password" id="formPassword" class="form-control" name="password" />
                    <label class="form-label" for="formPassword">Password</label>
                  </div>

                  <div class="text-center pt-1 mb-5 pb-1">
                    <button class="btn btn-primary btn-block fa-lg gradient-custom-2 mb-3" type="submit">Log
                      in</button>
                    <a class="text-muted" href="#!">Forgot password?</a>
                  </div>

                  <div class="d-flex align-items-center justify-content-center pb-4">
                    <p class="mb-0 me-2">Don't have an account?</p>
                    <a href="register.jsp" class="btn btn-outline-danger">Create new</a>
                  </div>

                </form>

              </div>
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