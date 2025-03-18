<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Finance Data</title>
    <script>
        function fetchFinanceData() {
            var xhr = new XMLHttpRequest();
            var symbol = document.getElementById('stockSymbol').value;
            var url = 'FinanceData?symbol=' + encodeURIComponent(symbol);
            xhr.open('GET', url, true);
            xhr.onload = function () {
                if (xhr.status === 200) {
                    var data = JSON.parse(xhr.responseText);
                    if (data.error) {
                        document.getElementById('dataDisplay').innerHTML = 'Error: ' + data.error;
                    } else {
                        document.getElementById('dataDisplay').innerHTML = 'Symbol: ' + data.symbol +
                            '<br>Price: $' + data.price +
                            '<br>Previous Close: $' + data.prevClose;
                    }
                } else {
                    document.getElementById('dataDisplay').innerHTML = 'Error fetching data.';
                }
            };
            xhr.onerror = function () {
                document.getElementById('dataDisplay').innerHTML = 'Error fetching data. Please check network connection.';
            };
            xhr.send();
        }
    </script>
</head>
<body>
<h1>Stock Data Lookup</h1>
<input type="text" id="stockSymbol" placeholder="Enter Stock Symbol" value="AAPL">
<button onclick="fetchFinanceData()">Fetch Stock Data</button>
<div id="dataDisplay"></div>
</body>
</html>
