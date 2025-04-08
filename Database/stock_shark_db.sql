-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 18, 2025 at 05:08 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `share_price_app_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `stock_prices`
--
-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 18, 2025 at 05:08 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `share_price_app_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `stock_prices`
--
CREATE TABLE Users (
    user_id VARCHAR(255) NOT NULL PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    FirstName VARCHAR(255),
    LastName VARCHAR(255),
    loginStatus BOOLEAN
);
CREATE TABLE TrendingStock (
    Symbol VARCHAR(10),
    Price DECIMAL(10, 2),
    priceChange DECIMAL(10, 2),
    ChangePercent DECIMAL(10, 2),
    Volume BIGINT,
    AverageVolume3M BIGINT,
    MarketCap DECIMAL(15, 2),
    PERatio DECIMAL(10, 2),
    FiftyTwoWeekChange DECIMAL(10, 2),
    PRIMARY KEY (Symbol)
);

CREATE TABLE Portfolio (
    PortfolioID INT AUTO_INCREMENT PRIMARY KEY,
    PortfolioName VARCHAR(255),
    CostBasis DECIMAL(15, 2),
    MarketValue DECIMAL(15, 2),
    DayChange DECIMAL(15, 2),
    UnrealizedGainLoss DECIMAL(15, 2),
    RealizedGainLoss DECIMAL(15, 2),
    UserID VARCHAR(255),
    FOREIGN KEY (UserID) REFERENCES Users(user_id)
);

CREATE TABLE PortfolioStocks (
    PortfolioID INT,
    StockSymbol VARCHAR(10),
    FOREIGN KEY (PortfolioID) REFERENCES Portfolio(PortfolioID),
    PRIMARY KEY (PortfolioID, StockSymbol)
);

CREATE TABLE stock_prices (
    symbol VARCHAR(10) DEFAULT NULL,
    date date DEFAULT NULL,
    open_price float DEFAULT NULL,
    high_price float DEFAULT NULL,
    low_price float DEFAULT NULL,
    close_price float DEFAULT NULL,
    volume float DEFAULT NULL,
    last_updated time DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



-- Insert sample data into Users table
INSERT INTO Users (user_id, email, password, FirstName, LastName, loginStatus) VALUES
('user001', 'john.doe@example.com', 'password123', 'John', 'Doe', TRUE),
('user002', 'jane.doe@example.com', 'password456', 'Jane', 'Doe', FALSE);

-- Insert sample data into TrendingStock table
INSERT INTO TrendingStock (Symbol, Name, Price, priceChange, ChangePercent, Volume, AverageVolume3M, MarketCap, PERatio, FiftyTwoWeekChange) VALUES
('AAPL', 150.00, 2.00, 1.35, 1000000, 500000, 2000000000, 25.50, 10.00),
('TSLA', 300.00, -1.50, -0.50, 1500000, 750000, 800000000, 30.75, -5.00);

-- Insert sample data into Portfolio table
INSERT INTO Portfolio (PortfolioName, CostBasis, MarketValue, DayChange, UnrealizedGainLoss, RealizedGainLoss, UserID) VALUES
('Tech Giants', 10000.00, 12000.00, 200.00, 2000.00, 500.00, 'user001'),
('Auto Leaders', 5000.00, 4500.00, -100.00, -500.00, 300.00, 'user002');

-- Insert sample data into PortfolioStocks table
INSERT INTO PortfolioStocks (PortfolioID, StockSymbol) VALUES
(1, 'AAPL'),
(1, 'TSLA'),
(2, 'TSLA');

-- Insert sample data into stock_prices table
INSERT INTO stock_prices (symbol, date, open_price, high_price, low_price, close_price, volume, last_updated) VALUES
('AAPL', '2023-04-01', 149.00, 151.00, 148.00, 150.00, 1000000, '17:00:00'),
('TSLA', '2023-04-01', 299.00, 305.00, 295.00, 300.00, 1500000, '17:00:00');
