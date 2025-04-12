<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>StockShark</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css" />
</head>
<body>
<header>
  <div class="header-container">
    <div class="logo">
      <h1>StockShark Web-app</h1>
    </div>
    <nav class="main-nav">
      <ul>
        <c:choose>
          <c:when test="${not empty sessionScope.user}">
            <li><a href="${pageContext.request.contextPath}/">Home</a></li>
            <li><a href="${pageContext.request.contextPath}/Markets">Markets</a></li>
            <li><a href="${pageContext.request.contextPath}/CompareStocks">Compare Stocks</a></li>
            <li><a href="${pageContext.request.contextPath}/Portfolio">Watchlist</a></li>
            <li><a href="${pageContext.request.contextPath}/Profile">Profile</a></li>
            <li><a href="${pageContext.request.contextPath}/Logout">Log Out</a></li>
          </c:when>

          <c:otherwise>
            <li><a href="${pageContext.request.contextPath}/CompareStocks">Compare Stocks</a></li>
            <li><a href="${pageContext.request.contextPath}/Login">Log In</a></li>
          </c:otherwise>
        </c:choose>
      </ul>
    </nav>
  </div>
</header>
