import requests
import json

# Define API Key and stock symbol
api_key = "LmBtai8mcvAtNDR6LqQbDR4D"
stock_symbol = "AAPL"

# Google Finance API URL
url = f"https://www.searchapi.io/api/v1/google/finance?ticker={stock_symbol}&api_key={api_key}"

# Fetch stock data
response = requests.get(url)
data = response.json()

# Save data to JSON
with open(f"{stock_symbol}_google_finance.json", "w") as file:
    json.dump(data, file, indent=4)

print(f"Google Finance data for {stock_symbol} saved successfully.")
