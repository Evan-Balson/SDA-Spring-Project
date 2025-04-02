import yfinance as yf

# Define stock symbol
stock_symbol = "AAPL" 
'''
This means Apple for instance
We must replace this with a variable so that
we change it based on what is entereed in our UI

'''
# Fetch stock data for the last 2 years
stock_data = yf.download(stock_symbol, start="2022-01-01", end="2024-01-01")

'''
We must make the dates variables aswell
so that we can flexibly search with our UI input.

'''

# Testing to see if it works: Display the first 5 rows
print(stock_data.head())
