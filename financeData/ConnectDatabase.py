'''
Note the database created on our local server. xaamp myst be running

'''

import mysql.connector
import yfinance as yf

# Connect to MySQL database
conn = mysql.connector.connect(
    host="localhost",
    user="root",
    password="",
    database="share_price_app_db"
)
cursor = conn.cursor()

def save_stock_data(symbol):
    # Fetch stock data
    stock_data = yf.download(symbol, period="1y")  # Fetch 1 year of data

    for index, row in stock_data.iterrows():
        cursor.execute("""
            INSERT INTO stock_prices (symbol, date, open_price, high_price, low_price, close_price, volume)
            VALUES (%s, %s, %s, %s, %s, %s, %s)
            ON DUPLICATE KEY UPDATE 
                open_price=VALUES(open_price),
                high_price=VALUES(high_price),
                low_price=VALUES(low_price),
                close_price=VALUES(close_price),
                volume=VALUES(volume),
                last_updated=CURRENT_TIMESTAMP;
        """, (symbol, index.date(), row['Open'].item(), row['High'].item(), row['Low'].item(), row['Close'].item(), row['Volume'].item()))

    conn.commit()

# Save data for AAPL
save_stock_data("AAPL")

# Close connection
conn.close()
print("Stock data saved successfully!")