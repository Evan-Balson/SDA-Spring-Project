<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Markets</title>
    <style>
        body {
            font-family: sans-serif;
            background-color: #f4f4f4;
            color: #333;
            margin: 0;
            padding: 20px;
        }

        .container {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        h2 {
            color: #007bff;
            margin-top: 0;
            margin-bottom: 15px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }

        th {
            background-color: #f0f0f0;
            font-weight: bold;
        }

        tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        .positive {
            color: green;
        }

        .negative {
            color: red;
        }

        .button-container {
            margin-top: 20px;
        }

        button {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 10px 15px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            margin-right: 10px;
        }

        button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Trending Stocks</h2>
        <table>
            <thead>
                <tr>
                    <th>Symbol</th>
                    <th>Name</th>
                    <th>Price</th>
                    <th>Change</th>
                    <th>Change %</th>
                    <th>Volume</th>
                    <th>Avg Vol (3M)</th>
                    <th>Market Cap</th>
                    <th>P/E Ratio (TTM)</th>
                    <th>52W Change</th>
                </tr>
            </thead>
            <tbody id="trendingStocksTableBody">
                <tr>
                    <td>QBTS</td>
                    <td>D-Wave Quantum Inc.</td>
                    <td>10.15</td>
                    <td class="positive">+3.24</td>
                    <td class="positive">+46.87%</td>
                    <td>259.529M</td>
                    <td>86.209M</td>
                    <td>2.706B</td>
                    <td>-</td>
                    <td>331.8</td>
                </tr>
                <tr>
                    <td>NVDA</td>
                    <td>NVIDIA Corporation</td>
                    <td>121.67</td>
                    <td class="positive">+6.09</td>
                    <td class="positive">+5.27%</td>
                    <td>274.062M</td>
                    <td>271.663M</td>
                    <td>2.969T</td>
                    <td>41.38</td>
                    <td>37.5</td>
                </tr>
                </tbody>
        </table>

        <div class="button-container">
            <button id="refreshButton">Refresh Data</button>
            <button>Filter Stocks</button>
            <button>Sort By Price</button>
        </div>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const refreshButton = document.getElementById('refreshButton');
            const tableBody = document.getElementById('trendingStocksTableBody');

            function fetchTrendingStocks() {
                fetch('/TrendingStocks') // This is the URL you mapped in web.xml
                    .then(response => {
                        if (!response.ok) {
                            throw new Error(`HTTP error! status: ${response.status}`);
                        }
                        return response.json(); // Tell it to expect a JSON response
                    })
                    .then(data => {
                        // 'data' is the JSON array of trending stocks your servlet sent
                        // Now you need to update the table in your HTML with this data
                        tableBody.innerHTML = ''; // Clear any existing rows

                        data.forEach(stock => {
                            let row = tableBody.insertRow();
                            let symbolCell = row.insertCell();
                            let nameCell = row.insertCell();
                            let priceCell = row.insertCell();
                            let changeCell = row.insertCell();
                            let changePercentCell = row.insertCell();
                            let volumeCell = row.insertCell();
                            let avgVolumeCell = row.insertCell();
                            let marketCapCell = row.insertCell();
                            let peRatioCell = row.insertCell();
                            let fiftyTwoWeekChangeCell = row.insertCell();

                            symbolCell.textContent = stock.symbol;
                            nameCell.textContent = stock.name || 'N/A'; // Use 'N/A' if name is missing
                            priceCell.textContent = stock.price;
                            changeCell.textContent = stock.change;
                            changeCell.className = stock.change.startsWith('+') ? 'positive' : 'negative'; // Apply CSS class for color
                            changePercentCell.textContent = stock.changePercent;
                            changePercentCell.className = stock.changePercent.startsWith('+') ? 'positive' : 'negative';
                            volumeCell.textContent = stock.volume;
                            avgVolumeCell.textContent = stock.avgVolume || 'N/A';
                            marketCapCell.textContent = stock.marketCap || 'N/A';
                            peRatioCell.textContent = stock.peRatio || 'N/A';
                            fiftyTwoWeekChangeCell.textContent = stock.fiftyTwoWeekChange || 'N/A';
                        });
                    })
                    .catch(error => {
                        console.error('Error fetching trending stocks:', error);
                        tableBody.innerHTML = '<tr><td colspan="10">Failed to load trending stocks.</td></tr>';
                    });
            }

            // Call fetchTrendingStocks when the page loads
            fetchTrendingStocks();

            // Add an event listener to the refresh button to fetch data again
            refreshButton.addEventListener('click', fetchTrendingStocks);
        });
    </script>
</body>
</html>
