<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Stock Data</title>
    <script>
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
                        } else {
                            // Display stock price (modify as needed based on Alpha Vantage response)
                            document.getElementById('dataDisplay').innerHTML =
                                '<h3>Stock Data for ' + stockSymbol.toUpperCase() + '</h3>' +
                                '<pre>' + JSON.stringify(data, null, 2) + '</pre>';
                        }
                    } catch (e) {
                        document.getElementById('dataDisplay').innerHTML = 'Error parsing data.';
                    }
                } else {
                    document.getElementById('dataDisplay').innerHTML = 'Error fetching data. HTTP Status: ' + xhr.status;
                }
            };
            xhr.onerror = function () {
                document.getElementById('dataDisplay').innerHTML = 'Network error occurred.';
            };
            xhr.send();
        }
    </script>
</head>
<body>
<h1>Check Stock Data</h1>
<input type="text" id="stockSymbol" placeholder="Enter Stock Symbol">
<button onclick="fetchStockData()">Get Data</button>
<div id="dataDisplay"></div>
</body>
</html>
