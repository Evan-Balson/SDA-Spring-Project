package com.stockshark.stockshark.controllers;

import com.stockshark.config.DatabaseConfig;
import com.stockshark.stockshark.models.CompoundCoomponents.Data_Handling_Component;
import com.stockshark.stockshark.models.CompoundCoomponents.User_Management_Compound;
import com.stockshark.stockshark.models.Data_Handling.StockData;
import com.stockshark.stockshark.models.Data_Handling.Database;
import com.stockshark.stockshark.models.User_Management.Portfolio;
import com.stockshark.stockshark.models.User_Management.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.json.JSONObject;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

@WebServlet("/Watchlist")
public class WatchlistServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String portfolioIdParam = request.getParameter("portfolioId");
        if (portfolioIdParam == null) {
            request.setAttribute("errorMessage", "No portfolio was selected.");
            request.getRequestDispatcher("watchlist.jsp").forward(request, response);
            return;
        }

        int portfolioId = 0;
        try {
            portfolioId = Integer.parseInt(portfolioIdParam);
        } catch (NumberFormatException nfe) {
            request.setAttribute("errorMessage", "Invalid portfolio ID.");
            request.getRequestDispatcher("watchlist.jsp").forward(request, response);
            return;
        }

        User_Management_Compound user_management = new User_Management_Compound();
        Portfolio portfolio = user_management.getPortfolioById(portfolioId);
        if (portfolio == null) {
            request.setAttribute("errorMessage", "Portfolio not found.");
            request.getRequestDispatcher("watchlist.jsp").forward(request, response);
            return;
        }
        System.out.println("[DEBUG] Portfolio: " + portfolio);

        String symbolsStr = portfolio.getSymbols();
        String symbolA = "";
        String symbolB = "";
        if (symbolsStr != null && !symbolsStr.isEmpty()) {
            String[] syms = symbolsStr.split(",");
            if (syms.length > 0) {
                symbolA = syms[0].trim();
            }
            if (syms.length > 1) {
                symbolB = syms[1].trim();
            }
        }
        System.out.println("[DEBUG] Retrieved Symbols: symbolA = " + symbolA + ", symbolB = " + symbolB);

        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        if (startDate == null || startDate.isEmpty()) {
            startDate = "2000-01-01";
        }
        if (endDate == null || endDate.isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            endDate = sdf.format(new Date());
        }
        System.out.println("[DEBUG] Date Range: startDate = " + startDate + ", endDate = " + endDate);

        Data_Handling_Component database = user_management.getexternalDB();
        List<StockData> stockDataListA = new ArrayList<>();
        List<StockData> stockDataListB = new ArrayList<>();
        try {
            JSONObject apiResponseA = database.getStockDataByDateRange(symbolA, startDate, endDate);
            if (apiResponseA != null) {
                stockDataListA = database.Format_AlphaVantageData_ForDatabase(apiResponseA);
            }
            System.out.println("[DEBUG] Retrieved " + stockDataListA.size() + " data points for " + symbolA);
            JSONObject apiResponseB = database.getStockDataByDateRange(symbolB, startDate, endDate);
            if (apiResponseB != null) {
                stockDataListB = database.Format_AlphaVantageData_ForDatabase(apiResponseB);
            }
            System.out.println("[DEBUG] Retrieved " + stockDataListB.size() + " data points for " + symbolB);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error retrieving stock data from API.");
        }

        Map<String, List<StockData>> dataBySymbol = new HashMap<>();
        dataBySymbol.put(symbolA.toUpperCase(), stockDataListA);
        dataBySymbol.put(symbolB.toUpperCase(), stockDataListB);
        for (List<StockData> list : dataBySymbol.values()) {
            list.sort(Comparator.comparing(StockData::getDate));
        }

        SortedSet<String> unionDates = new TreeSet<>();
        for (List<StockData> list : dataBySymbol.values()) {
            for (StockData sd : list) {
                unionDates.add(sd.getDate());
            }
        }
        List<String> labelsList = new ArrayList<>(unionDates);
        System.out.println("[DEBUG] Labels: " + labelsList);

        Map<String, Double> priceMapA = new HashMap<>();
        Map<String, Double> priceMapB = new HashMap<>();
        if (dataBySymbol.containsKey(symbolA.toUpperCase())) {
            for (StockData sd : dataBySymbol.get(symbolA.toUpperCase())) {
                try {
                    priceMapA.put(sd.getDate(), Double.parseDouble(sd.getClose()));
                } catch (Exception ex) {
                    priceMapA.put(sd.getDate(), null);
                }
            }
        }
        if (dataBySymbol.containsKey(symbolB.toUpperCase())) {
            for (StockData sd : dataBySymbol.get(symbolB.toUpperCase())) {
                try {
                    priceMapB.put(sd.getDate(), Double.parseDouble(sd.getClose()));
                } catch (Exception ex) {
                    priceMapB.put(sd.getDate(), null);
                }
            }
        }
        List<Double> pricesAList = new ArrayList<>();
        List<Double> pricesBList = new ArrayList<>();
        for (String date : labelsList) {
            pricesAList.add(priceMapA.getOrDefault(date, null));
            pricesBList.add(priceMapB.getOrDefault(date, null));
        }
        System.out.println("[DEBUG] Prices for " + symbolA + ": " + pricesAList);
        System.out.println("[DEBUG] Prices for " + symbolB + ": " + pricesBList);

        String labelsJson = arrayToJson(labelsList.toArray(new String[0]));
        String dataAJson = arrayToJson(pricesAList);
        String dataBJson = arrayToJson(pricesBList);
        System.out.println("[DEBUG] labelsJson: " + labelsJson);
        System.out.println("[DEBUG] dataAJson: " + dataAJson);
        System.out.println("[DEBUG] dataBJson: " + dataBJson);

        request.setAttribute("symbolA", symbolA);
        request.setAttribute("symbolB", symbolB);
        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);
        request.setAttribute("labelsJson", labelsJson);
        request.setAttribute("dataAJson", dataAJson);
        request.setAttribute("dataBJson", dataBJson);

        request.getRequestDispatcher("watchlist.jsp").forward(request, response);
    }

    private String arrayToJson(String[] arr) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            sb.append("\"").append(arr[i]).append("\"");
            if (i < arr.length - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    private String arrayToJson(List<Double> list) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            Double value = list.get(i);
            if (value == null) {
                sb.append("null");
            } else {
                sb.append(value);
            }
            if (i < list.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
