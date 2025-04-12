<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Compare Stocks</title>
    <link rel="stylesheet" type="text/css" href="css/compareStocks.css" />
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
<%@ include file="header.jsp" %>
<main>
    <!-- Top row with heading and "Add To Portfolio" button on the right -->
    <div class="top-row">
        <h2>Compare Stocks</h2>
        <!-- "Add To Portfolio" form & button outside the main compare form -->
        <form action="CompareStocks" method="post" class="portfolio-form">
            <!-- Pass hidden fields to preserve user input -->
            <input type="hidden" name="symbolA" value="${symbolA}" />
            <input type="hidden" name="symbolB" value="${symbolB}" />
            <input type="hidden" name="startDate" value="${startDate}" />
            <input type="hidden" name="endDate" value="${endDate}" />
            <!-- If the user previously checked these boxes, keep them "checked" by adding hidden fields -->
            <c:if test="${showSymbolA}">
                <input type="hidden" name="showSymbolA" value="on" />
            </c:if>
            <c:if test="${showSymbolB}">
                <input type="hidden" name="showSymbolB" value="on" />
            </c:if>

            <!-- Disable the button if no chart data is present (i.e., labelsJson is empty) -->
            <button type="submit" name="addToPortfolio" value="true"
                    <c:if test="${empty labelsJson}">disabled</c:if>>
                Add To Portfolio
            </button>
        </form>
    </div>

    <section class="compare-section">
        <!-- Error or success messages -->
        <c:if test="${not empty errorMessage}">
            <div class="alert error">${errorMessage}</div>
        </c:if>
        <c:if test="${not empty portfolioMessage}">
            <div class="alert success">${portfolioMessage}</div>
        </c:if>

        <!-- Main Compare Form -->
        <form action="CompareStocks" method="post" class="compare-form">
            <div class="form-row">
                <!-- Symbol A -->
                <div class="form-group">
                    <label for="symbolA">Symbol A:</label>
                    <input type="text" id="symbolA" name="symbolA"
                           value="${symbolA}" placeholder="e.g., AAPL" required />
                </div>

                <!-- Symbol B -->
                <div class="form-group">
                    <label for="symbolB">Symbol B:</label>
                    <input type="text" id="symbolB" name="symbolB"
                           value="${symbolB}" placeholder="e.g., MSFT" required />
                </div>

                <!-- Start Date -->
                <div class="form-group">
                    <label for="startDate">Start Date:</label>
                    <input type="date" id="startDate" name="startDate"
                           value="${startDate}" required />
                </div>

                <!-- End Date -->
                <div class="form-group">
                    <label for="endDate">End Date:</label>
                    <input type="date" id="endDate" name="endDate"
                           value="${endDate}" required />
                </div>

                <!-- Checkboxes (side by side) -->
                <div class="checkbox-group">
                    <label>
                        <input type="checkbox" name="showSymbolA"
                               <c:if test="${showSymbolA}">checked</c:if> />
                        Show Symbol A
                    </label>
                    <label>
                        <input type="checkbox" name="showSymbolB"
                               <c:if test="${showSymbolB}">checked</c:if> />
                        Show Symbol B
                    </label>
                </div>

                <!-- Compare Button -->
                <div class="button-group">
                    <button type="submit">Compare</button>
                </div>
            </div>
        </form>

        <!-- Chart Container (only shown if labelsJson is not empty) -->
        <c:if test="${not empty labelsJson}">
            <div class="chart-container">
                <canvas id="compareChart"></canvas>
            </div>
        </c:if>
    </section>
</main>

<%@ include file="footer.jsp" %>

<script>
    <c:if test="${not empty labelsJson}">
    const labels = JSON.parse('${labelsJson}');
    const dataA = JSON.parse('${dataAJson}');
    const dataB = JSON.parse('${dataBJson}');
    const symbolA = '${symbolA}';
    const symbolB = '${symbolB}';
    const showSymbolA = (${showSymbolA} === true);
    const showSymbolB = (${showSymbolB} === true);

    const ctx = document.getElementById('compareChart').getContext('2d');
    const compareChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: labels,
            datasets: [
                {
                    label: symbolA,
                    data: dataA,
                    backgroundColor: 'rgba(75,192,192,0.4)',
                    borderColor: 'rgba(75,192,192,1)',
                    fill: true,
                    hidden: !showSymbolA
                },
                {
                    label: symbolB,
                    data: dataB,
                    backgroundColor: 'rgba(153,102,255,0.4)',
                    borderColor: 'rgba(153,102,255,1)',
                    fill: true,
                    hidden: !showSymbolB
                }
            ]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: 'top',
                    labels: {
                        color: '#fff'   // White legend text
                    }
                },
                title: {
                    display: true,
                    text: 'Stock Comparison Chart',
                    color: '#fff'    // White chart title
                }
            },
            scales: {
                x: {
                    title: {
                        display: true,
                        text: 'Date',
                        color: '#fff'
                    },
                    ticks: {
                        color: '#fff'
                    }
                },
                y: {
                    title: {
                        display: true,
                        text: 'Price ($)',
                        color: '#fff'
                    },
                    ticks: {
                        color: '#fff'
                    }
                }
            }
        }
    });
    </c:if>
</script>
</body>
</html>
