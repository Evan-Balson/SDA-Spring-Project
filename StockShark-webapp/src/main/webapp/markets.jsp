
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="header.jsp" %>
<link rel="stylesheet" type="text/css" href="css/markets.css" />

<main>

  <section class="markets-section">
    <h2>Market Overview</h2>
    <div class="market-data">
      <table>
        <thead>
        <tr>
          <th>Symbol</th>
          <th>Price</th>
          <th>Change</th>
          <th>Change Percent</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="stock" items="${trendingStocks}">
          <tr>
            <td>${stock.symbol}</td>
            <td>$<fmt:formatNumber value="${stock.price}" type="number" minFractionDigits="2"/></td>
            <!-- Using the computed negative property -->
            <td class="${stock.negative ? 'negative' : 'positive'}">
              <fmt:formatNumber value="${stock.change}" type="number" minFractionDigits="2"/>%
            </td>
            <td>${stock.changePercent}</td>
          </tr>
        </c:forEach>
        </tbody>
      </table>
      <c:if test="${empty trendingStocks}">
        <p>No trending stocks available at the moment.</p>
      </c:if>
    </div>
  </section>
</main>

<%@ include file="footer.jsp" %>
