<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Portfolio</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/portfolio.css" />
    <!-- Optional: include Font Awesome if you want icons -->
    <!-- <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" /> -->
</head>
<body>
<%@ include file="header.jsp" %>
<main>
    <section class="portfolio-section">
        <h2>My Portfolio</h2>
        <c:if test="${not empty portfolioList}">
            <table class="portfolio-table">
                <thead>
                <tr>
                    <th>Watchlist Name</th>
                    <th>ID</th>
                    <th>Stock Symbols</th>
                    <th>Date Saved</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="portfolio" items="${portfolioList}">
                    <!-- Set a data attribute for the portfolio ID -->
                    <tr class="portfolio-row" data-portfolio-id="${portfolio.portfolioId}">
                        <td>
                                ${portfolio.portfolioName}
                        </td>
                        <td>${portfolio.portfolioId}</td>
                        <td>${portfolio.symbols}</td>
                        <td>${portfolio.savedDate}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
        <c:if test="${empty portfolioList}">
            <p>No portfolio details available.</p>
        </c:if>
    </section>
</main>
<%@ include file="footer.jsp" %>

<script>
    // When the page loads, attach a click listener to each portfolio row.
    document.addEventListener('DOMContentLoaded', function() {
        var rows = document.querySelectorAll('.portfolio-row');
        rows.forEach(function(row) {
            row.addEventListener('click', function() {
                // Get the portfolio ID from the data attribute.
                var portfolioId = row.getAttribute('data-portfolio-id');
                // Redirect to the watchlist page and pass the portfolioId as a query parameter.
                window.location.href = "${pageContext.request.contextPath}/Watchlist?portfolioId=" + portfolioId;
            });
        });
    });
</script>
</body>
</html>
