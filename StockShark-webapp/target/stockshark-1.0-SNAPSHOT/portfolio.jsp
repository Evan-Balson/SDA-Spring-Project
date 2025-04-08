<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Portfolio</title>
    <style>
        body {
            font-family: sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }

        .container {
            width: 90%;
            max-width: 1200px;
            margin: 20px auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }

        .logo {
            font-size: 1.5em;
            font-weight: bold;
        }

        .nav {
            display: flex;
        }

        .nav a {
            text-decoration: none;
            color: #333;
            margin-left: 15px;
        }

        .profile {
            display: flex;
            align-items: center;
            margin-top: 20px; 
            padding: 10px; 
            background-color: #f9f9f9;
            border-radius: 5px; 
        }

        .profile img {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            margin-left: 10px;
        }

        .table-container {
            overflow-x: auto;
            margin-top: 20px; 
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
        }

        tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        .expand-button {
            background: none;
            border: none;
            cursor: pointer;
            padding: 0;
        }
        a {
            color: #007bff;
            text-decoration: none;
        }

        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <div class="logo">LOGO</div>
        <nav class="nav">
            <a href="#">Markets</a>
            <a href="#">My Portfolio</a>
            <a href="#">Compare Stocks</a>
            <button>Menu</button>
        </nav>
    </div>
    <div class="profile">
        <img src="profile.jpg" alt="Profile">
        <button>...</button>
    </div>

    <h2>My Portfolio</h2>

    <div class="table-container">
        <table id="portfolioTable">
            <thead>
            <tr>
                <th>Stock Name</th>
                <th>Symbol</th>
                <th>Cost Basis (includes cash)</th>
                <th>Market Value (includes cash)</th>
                <th>Day Change</th>
                <th>Unrealized Gain/Loss</th>
                <th>Realized Gain/Loss</th>
                <th></th>
            </tr>
            </thead>
            <tbody id="portfolioBody">
            </tbody>
        </table>
    </div>
</div>
<script>
    const portfolioBody = document.getElementById('portfolioBody');

    function populatePortfolio() {
        portfolioBody.innerHTML = '';
        const storedPortfolio = localStorage.getItem('myPortfolio');
        let portfolioData = [];

        if (storedPortfolio) {
            try {
                portfolioData = JSON.parse(storedPortfolio);
            } catch (error) {
                console.error('Error parsing stored portfolio data:', error);
                localStorage.removeItem('myPortfolio');
                portfolioData = [];
            }
        }

        if (portfolioData.length === 0) {
            portfolioBody.innerHTML = `
                <tr>
                    <td colspan="8">No stocks in your portfolio yet.</td>
                </tr>
            `;
        }
        else{
            portfolioData.forEach(stock => {
                const row = document.createElement('tr');
                row.innerHTML = `
                <td>${stock.name}</td>
                <td><a href="watch-list-item.jsp?symbol=${stock.symbol}">${stock.symbol}</a></td>
                <td>${stock.costBasis.toFixed(2)}</td>
                <td>${stock.marketValue.toFixed(2)}</td>
                <td>${stock.dayChange.toFixed(2)}</td>
                <td>${stock.unrealizedGainLoss.toFixed(2)}</td>
                <td>${stock.realizedGainLoss.toFixed(2)}</td>
                <td><button class="expand-button">...</button></td>
            `;
                portfolioBody.appendChild(row);
            });
        }
    }

    function addStockToPortfolio() {
        const newStock = {
            name: "Apple Inc.",
            symbol: "AAPL",
            costBasis: 150.00,
            marketValue: 160.00,
            dayChange: 1.50,
            unrealizedGainLoss: 10.00,
            realizedGainLoss: 20.00
        };

        let portfolioData = [];
        const storedPortfolio = localStorage.getItem('myPortfolio');

        if (storedPortfolio) {
            try {
                portfolioData = JSON.parse(storedPortfolio);
            } catch (error) {
                console.error('Error parsing stored portfolio data:', error);
                localStorage.removeItem('myPortfolio');
            }
        }

        portfolioData.push(newStock);
        localStorage.setItem('myPortfolio', JSON.stringify(portfolioData));
        populatePortfolio();
    }

    document.addEventListener('DOMContentLoaded', function() {
        populatePortfolio();
        addStockToPortfolio();
    });
</script>
</body>
</html>