package com.stockshark.stockshark.models.Data_Handling;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

public interface iData {
    JSONObject getStockData(String symbol) throws IOException, InterruptedException;

    JSONObject getStockDataByDateRange(String symbol, String startDate, String endDate) throws IOException, InterruptedException;

    JSONObject getTrendingMarketData(String symbol) throws IOException, InterruptedException;
}
