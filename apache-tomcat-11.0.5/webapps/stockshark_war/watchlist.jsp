<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Watchlist</title>
    <link rel="stylesheet" type="text/css" href="css/watchlist.css" />
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>

<main>
    <section class="watchlist-section">
        <h2>My Watchlist</h2>
        <!-- Date Range Form with hidden portfolioId -->
        <form action="${pageContext.request.contextPath}/Watchlist" method="get" class="date-range-form">
            <input type="hidden" name="portfolioId" value="${param.portfolioId}" />
            <label for="startDate">Start Date:</label>
            <input type="date" id="startDate" name="startDate" value="${startDate}" required />
            <label for="endDate">End Date:</label>
            <input type="date" id="endDate" name="endDate" value="${endDate}" required />
            <button type="submit">Update Chart</button>
        </form>
        <!-- Display the fixed stock symbols retrieved from the portfolio -->
        <p>Stock Symbols: <strong>${symbolA}</strong> and <strong>${symbolB}</strong></p>
        <!-- Chart Container -->
        <div class="chart-container">
            <canvas id="watchlistChart"></canvas>
        </div>
    </section>
</main>
<%@ include file="footer.jsp" %>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        const labels = JSON.parse('${labelsJson}');
        const dataA = JSON.parse('${dataAJson}');
        const dataB = JSON.parse('${dataBJson}');
        const symbolA = '${symbolA}';
        const symbolB = '${symbolB}';
        const startDate = '${startDate}';
        const endDate = '${endDate}';

        const ctx = document.getElementById('watchlistChart').getContext('2d');
        const watchlistChart = new Chart(ctx, {
            type: 'line',
            data: {
                labels: labels,
                datasets: [
                    {
                        label: symbolA,
                        data: dataA,
                        backgroundColor: 'rgba(75, 192, 192, 0.4)',
                        borderColor: 'rgba(75, 192, 192, 1)',
                        fill: true
                    },
                    {
                        label: symbolB,
                        data: dataB,
                        backgroundColor: 'rgba(153, 102, 255, 0.4)',
                        borderColor: 'rgba(153, 102, 255, 1)',
                        fill: true
                    }
                ]
            },
            options: {
                responsive: true,
                plugins: {
                    title: {
                        display: true,
                        text: `Chart for period: ${startDate} - ${endDate}`,
                        color: '#fff',
                        font: { size: 16 }
                    },
                    legend: {
                        position: 'top',
                        labels: { color: '#fff' }
                    }
                },
                scales: {
                    x: {
                        title: { display: true, text: 'Date', color: '#fff' },
                        ticks: { color: '#fff' }
                    },
                    y: {
                        title: { display: true, text: 'Price ($)', color: '#fff' },
                        ticks: { color: '#fff' }
                    }
                }
            }
        });
    });
</script>
</body>
</html>
