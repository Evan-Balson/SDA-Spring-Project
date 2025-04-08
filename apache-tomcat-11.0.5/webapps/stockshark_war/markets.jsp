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
        // This is where your JavaScript to fetch and update the stock data will go
        // For now, the table is filled with placeholder data.

        document.addEventListener('DOMContentLoaded', function() {
            const refreshButton = document.getElementById('refreshButton');
            const tableBody = document.getElementById('trendingStocksTableBody');

            // In a real application, this button would trigger a JavaScript function
            // to call your TrendingStocksServlet and update the table with fresh data.
            refreshButton.addEventListener('click', function() {
                alert('Refreshing data...');
                // In a real scenario, you would make an AJAX call here
                // to fetch the latest trending stock data and update the tableBody.
            });

            // You would also add event listeners to your other buttons
            // to handle filtering and sorting actions.
        });
    </script>
</body>
</html>