<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Chart JS</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <style>
        .chart-controls {
            position: absolute;
            top: 20px;
            right: 20px;
            background-color: rgba(255, 255, 255, 0.8);
            padding: 10px;
            border-radius: 5px;
            z-index: 10;
        }
    </style>
</head>
<body class="bg-success">
<div class="col-8 offset-2 my-5">
    <div class="card">
        <div class="card-body">
            <h5 id="stock-name">Chart JS</h5>
            <hr>
            <input type="text" id="stock-input" class="form-control" placeholder="Enter Stock Symbol" />
            <button class="btn btn-primary mt-2" onclick="fetchStockData()">Load Chart</button>
            <canvas id="myChart" class="mt-3"></canvas>
            <div class="chart-controls">
                <select id="chart-type" class="form-select">
                    <option value="line">Line Chart</option>
                    <option value="bar">Bar Chart</option>
                    <option value="radar">Radar Chart</option>
                    <option value="pie">Pie Chart</option>
                    <option value="doughnut">Doughnut Chart</option>
                    <option value="scatter">Scatter Chart</option>
                </select>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<script>
    const ctx = document.getElementById('myChart').getContext('2d');
    const stockNameElement = document.getElementById("stock-name");
    const API_KEY = "JGCLFOUSE8PP5S1P";
    let chart;

    function fetchStockData() {
        const SYMBOL = document.getElementById("stock-input").value.toUpperCase();
        const chartType = document.getElementById("chart-type").value;

        if (!SYMBOL) {
            alert("Please enter a stock symbol");
            return;
        }

        const baseUrl = `https://www.alphavantage.co/query?function=TIME_SERIES_MONTHLY&symbol=${SYMBOL}&datatype=json&apikey=${API_KEY}`;

        fetch(baseUrl)
            .then(response => response.json())
            .then(data => {
                const timeSeries = data["Monthly Time Series"];
                if (!timeSeries) {
                    console.error("Invalid API response", data);
                    alert("Invalid stock symbol or API limit reached.");
                    return;
                }

                const allDates = Object.keys(timeSeries).reverse();
                const pastTwoYears = allDates.filter(date => {
                    const twoYearsAgo = new Date();
                    twoYearsAgo.setFullYear(twoYearsAgo.getFullYear() - 2);
                    return new Date(date) >= twoYearsAgo;
                }).slice(-24); // Get the last 24 months

                const prices = pastTwoYears.map(date => parseFloat(timeSeries[date]["4. close"]));

                stockNameElement.textContent = `Stock: ${SYMBOL}`;

                if (chart) {
                    chart.destroy();
                }

                let chartData = {
                    labels: pastTwoYears,
                    datasets: [{
                        label: 'Stock Price',
                        data: prices,
                        borderWidth: 1,
                        backgroundColor: 'rgba(255, 99, 132, 0.2)', // Default red with transparency
                        borderColor: 'rgb(255, 99, 132)',
                        fill: true
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
                    chartData.datasets[0].backgroundColor = [ // Provide multiple background colors for pie/doughnut
                        'rgba(255, 99, 132, 0.8)',
                        'rgba(54, 162, 235, 0.8)',
                        'rgba(255, 206, 86, 0.8)',
                        'rgba(75, 192, 192, 0.8)',
                        'rgba(153, 102, 255, 0.8)'
                    ];
                    chartData.datasets[0].borderColor = '#fff';
                    delete chartOptions.scales; // Remove scales for pie and doughnut
                } else if (chartType === "bar") {
                    chartData.datasets[0].backgroundColor = 'rgba(54, 162, 235, 0.8)';
                } else if (chartType === "radar") {
                    chartData.labels = pastTwoYears.slice(0, 6);
                    chartData.datasets[0].data = prices.slice(0, 6);
                    chartData.datasets[0].backgroundColor = 'rgba(153, 102, 255, 0.2)';
                    chartData.datasets[0].borderColor = 'rgb(153, 102, 255)';
                    chartOptions.scales = {
                        r: {
                            beginAtZero: true
                        }
                    };
                } else if (chartType === "scatter"){
                    chartData.datasets[0].data = pastTwoYears.map((date, index) => ({ x: date, y: prices[index] }));
                    chartData.labels = [];
                    chartData.datasets[0].showLine = false; // Don't draw lines between points
                    chartData.datasets[0].backgroundColor = 'rgba(75, 192, 192, 0.8)';
                    chartData.datasets[0].borderColor = 'rgb(75, 192, 192)';
                    chartOptions.scales = {
                        x: {
                            type: 'time',
                            time: {
                                unit: 'month'
                            }
                        },
                        y: {
                            beginAtZero: true
                        }
                    }
                }

                chart = new Chart(ctx, {
                    type: chartType,
                    data: chartData,
                    options: chartOptions,
                });
            })
            .catch(error => {
                console.error("Error fetching stock data:", error);
                alert("Error fetching stock data. Please try again later.");
            });
    }
</script>
</body>
</html>