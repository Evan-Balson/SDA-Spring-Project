<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>My Watch List</title>
    <script src="https://cdn.jsdelivr.net/npm/watch-list.js"></script>
</head>
<body>
    <h1>Check Stock Data</h1>
    <input type="text" id="stockSymbol" placeholder="Enter Stock Symbol">
    <button onclick="fetchStockData()">Get Stock Data</button>
    <div id="dataDisplay"></div>
    <canvas id="stockChart" width="400" height="200"></canvas>

    <script>
        let chart; // Declare chart variable globally
        let chartType = "line"; // Default chart type
        let ctx = document.getElementById('stockChart').getContext('2d');

        function fetchStockData() {
            var stockSymbol = document.getElementById('stockSymbol').value.trim();
            if (!stockSymbol) {
                alert('Please enter a stock symbol.');
                return;
            }

            var xhr = new XMLHttpRequest();
            var url = 'StockData?symbol=' + encodeURIComponent(stockSymbol);
            xhr.open('GET', url, true);
            xhr.onload = function () {
                if (xhr.status === 200) {
                    try {
                        var data = JSON.parse(xhr.responseText);
                        if (data.error) {
                            document.getElementById('dataDisplay').innerHTML = 'Error: ' + data.error;
                        } else if (data["Time Series (Monthly)"]) { // Check for Time Series data
                            displayChart(data["Time Series (Monthly)"], stockSymbol);
                            document.getElementById('dataDisplay').innerHTML = ''; // Clear previous message
                        } else {
                            // Assuming the response includes some data to display
                            var displayHtml = '<h3>Stock Data for ' + stockSymbol.toUpperCase() + '</h3>';
                            displayHtml += '<pre>' + JSON.stringify(data, null, 2) + '</pre>';
                            document.getElementById('dataDisplay').innerHTML = displayHtml;
                        }
                    } catch (e) {
                        document.getElementById('dataDisplay').innerHTML = 'Error parsing data.';
                    }
                } else {
                    document.getElementById('dataDisplay').innerHTML = 'Error fetching data. HTTP Status: ' + xhr.status;
                }
            };
            xhr.onerror = function() {
                document.getElementById('dataDisplay').innerHTML = 'Network error while fetching data.';
            };
            xhr.send();
        }

        function displayChart(timeSeries, SYMBOL) {
            const allDates = Object.keys(timeSeries).reverse();
            const pastTwoYears = allDates.filter(date => {
                const twoYearsAgo = new Date();
                twoYearsAgo.setFullYear(twoYearsAgo.getFullYear() - 2);
                return new Date(date) >= twoYearsAgo;
            }).slice(-24); // Get the last 24 months

            const prices = pastTwoYears.map(date => parseFloat(timeSeries[date]["4. close"]));

            let chartData = {
                labels: pastTwoYears,
                datasets: [{
                    label: 'Stock Price',
                    data: prices,
                    borderWidth: 1,
                }]
            };

            let chartOptions = {
                plugins: {
                    legend: {
                        display: true
                    }
                }
            };

            if (chartType === "pie" || chartType === "doughnut") {
                chartData.labels = pastTwoYears.slice(0, 5); // Use only first 5 for pie/doughnut
                chartData.datasets[0].data = prices.slice(0, 5);
            }

            if (chartType === "radar") {
                chartData.labels = pastTwoYears.slice(0, 6);
                chartData.datasets[0].data = prices.slice(0, 6);
                chartOptions.scales = {
                    r: {
                        beginAtZero: true
                    }
                };
            }

            if (chartType === "scatter") {
                chartData.datasets[0].data = pastTwoYears.map((date, index) => {
                    return { x: date, y: prices[index] }
                });
                chartData.labels = [];
                chartOptions.scales = {
                    x: {
                        type: 'time',
                        time: {
                            unit: 'month'
                        }
                    }
                }
            }

            if (chart) {
                chart.destroy();
            }

            chart = new Chart(ctx, {
                type: chartType,
                data: chartData,
                options: chartOptions
            });
        }
    </script>
    </body>
</html>